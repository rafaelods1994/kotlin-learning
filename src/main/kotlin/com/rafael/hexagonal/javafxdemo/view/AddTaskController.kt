package com.rafael.hexagonal.javafxdemo.view

import com.rafael.hexagonal.javafxdemo.api.ApiClient
import com.rafael.hexagonal.javafxdemo.model.Task
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import kotlinx.coroutines.runBlocking

class AddTaskController {

    @FXML
    private lateinit var titleField: TextField

    @FXML
    private lateinit var descriptionField: TextField

    @FXML
    private lateinit var statusField: ComboBox<String>

    @FXML
    private lateinit var priorityField: ComboBox<String>

    @FXML
    private fun onSaveTask() {
        val title = titleField.text
        val description = descriptionField.text
        val status = statusField.value
        val priority = priorityField.value

        // Validate form fields
        if (title.isNullOrEmpty() || description.isNullOrEmpty() || status.isNullOrEmpty() || priority.isNullOrEmpty()) {
            Alert(Alert.AlertType.ERROR, "All fields are required!").showAndWait()
            return
        }

        // Create a new Task object
        val newTask = Task(
            id = 0, // Assume the backend generates the ID
            title = title,
            description = description,
            status = status,
            priority = priority
        )

        // Call API to save the task
        runBlocking {
            try {
                ApiClient.createTask(newTask)
                Alert(Alert.AlertType.INFORMATION, "Task saved successfully!").showAndWait()

                // Close the form after saving
                val stage = titleField.scene.window as Stage
                stage.close()
            } catch (e: Exception) {
                Alert(Alert.AlertType.ERROR, "Failed to save task: ${e.message}").showAndWait()
            }
        }
        // Notify TaskListController to reload tasks after saving
        TaskListController.reloadTasks()
    }
}
