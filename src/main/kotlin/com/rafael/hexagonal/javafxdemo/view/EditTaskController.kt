package com.rafael.hexagonal.javafxdemo.view

import com.rafael.hexagonal.javafxdemo.api.ApiClient
import com.rafael.hexagonal.javafxdemo.model.Task
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import kotlinx.coroutines.runBlocking

class EditTaskController(private val task: Task) { // Pass selected task to this controller

    @FXML
    private lateinit var titleField: TextField

    @FXML
    private lateinit var descriptionField: TextField

    @FXML
    private lateinit var statusField: ComboBox<String>

    @FXML
    private lateinit var priorityField: ComboBox<String>

    fun initialize() {
        // Pre-fill fields with the task's existing details
        titleField.text = task.title
        descriptionField.text = task.description
        statusField.value = task.status
        priorityField.value = task.priority
    }

    @FXML
    private fun onSaveTask() {
        val updatedTask = task.copy(
            title = titleField.text,
            description = descriptionField.text,
            status = statusField.value,
            priority = priorityField.value
        )

        runBlocking {
            try {
                ApiClient.updateTask(updatedTask)
                Alert(Alert.AlertType.INFORMATION, "Task updated successfully!").showAndWait()

                TaskListController.reloadTasks()
                val stage = titleField.scene.window as Stage
                stage.close()
            } catch (e: Exception) {
                Alert(Alert.AlertType.ERROR, "Failed to update task: ${e.message}").showAndWait()
            }
        }
    }
}
