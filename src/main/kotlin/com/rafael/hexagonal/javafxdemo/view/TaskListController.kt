package com.rafael.hexagonal.javafxdemo.view

import com.rafael.hexagonal.javafxdemo.api.ApiClient
import com.rafael.hexagonal.javafxdemo.model.Task
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.collections.FXCollections
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.stage.Stage
import kotlinx.coroutines.runBlocking

class TaskListController {

    @FXML
    private lateinit var taskListView: ListView<String>

    init {
        instance = this
    }

    companion object {
        // Holds the current instance of the controller
        private lateinit var instance: TaskListController

        // Reload tasks via the instance
        fun reloadTasks() {
            runBlocking {
                instance.loadTasks()
            }
        }
    }

    fun initialize() {
        // Add listeners for the ComboBoxes to trigger filtering logic
        statusFilter.valueProperty().addListener { _, _, _ -> onFilterChange() }
        priorityFilter.valueProperty().addListener { _, _, _ -> onFilterChange() }

        // Initially load all tasks
        runBlocking {
            loadTasks()
        }
    }


    private suspend fun loadTasks() {
        try {
            val tasks: List<Task> = ApiClient.getTasks() // Fetch tasks from backend
            val taskTitles = tasks.map { it.title } // Extract titles
            taskListView.items = FXCollections.observableArrayList(taskTitles)
        } catch (e: Exception) {
            showError("Failed to load tasks: ${e.message}")
        }
    }

    private fun showError(message: String) {
        Alert(AlertType.ERROR, message).showAndWait()
    }

    @FXML
    private fun onEditTask() {

        val selectedTitle = taskListView.selectionModel.selectedItem
        if (selectedTitle == null) {
            showError("Please select a task to edit.")
            return
        }

        runBlocking {
            val tasks = ApiClient.getTasks()
            val selectedTask = tasks.find { it.title == selectedTitle } ?: return@runBlocking
            try {
                val loader = FXMLLoader(javaClass.getResource("/fxml/EditTask.fxml"))
// Dynamically assign the controller
                val controller = EditTaskController(selectedTask)
                loader.setController(controller) // Ensure this line is not missing
                val root = loader.load<Parent>()
                val stage = Stage()
                stage.title = "Edit Task"
                stage.scene = Scene(root)
                stage.show()
            } catch (e: Exception) {
                showError("Failed to open Edit Task form: ${e.message}")
            }
        }
    }


    @FXML
    private fun onDeleteTask() {
        val selectedTitle = taskListView.selectionModel.selectedItem
        if (selectedTitle == null) {
            showError("Please select a task to delete.")
            return
        }

        runBlocking {
            val tasks = ApiClient.getTasks()
            val selectedTask = tasks.find { it.title == selectedTitle } ?: return@runBlocking

            try {
                ApiClient.deleteTask(selectedTask.id)
                Alert(Alert.AlertType.INFORMATION, "Task deleted successfully!").showAndWait()

                // Reload the task list
                reloadTasks()
            } catch (e: Exception) {
                showError("Failed to delete task: ${e.message}")
            }
        }
    }

    @FXML
    private lateinit var statusFilter: ComboBox<String>

    @FXML
    private lateinit var priorityFilter: ComboBox<String>

    @FXML
    private fun onFilterChange() {
        val status = statusFilter.value ?: "ALL"
        val priority = priorityFilter.value ?: "ALL"

        runBlocking {
            val tasks = ApiClient.getTasks()
            val filteredTasks = tasks.filter { task ->
                (status == "ALL" || task.status == status) &&
                        (priority == "ALL" || task.priority == priority)
            }
            val taskTitles = filteredTasks.map { it.title }
            taskListView.items = FXCollections.observableArrayList(taskTitles)
        }
    }

    @FXML
    fun onAddTask() {
        try {
            val loader = FXMLLoader(javaClass.getResource("/fxml/AddTask.fxml"))
            val root = loader.load<Parent>()

            val stage = Stage()
            stage.title = "Add New Task"
            stage.scene = Scene(root)
            stage.show()
        } catch (e: Exception) {
            Alert(Alert.AlertType.ERROR, "Failed to open Add Task form: ${e.message}").showAndWait()
        }
    }


}

