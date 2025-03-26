package com.rafael.hexagonal.javafxdemo

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class AppLauncher : Application() {
    override fun start(primaryStage: Stage) {
        try {
            // Load the FXML file for Task List
            val loader = FXMLLoader(javaClass.getResource("/fxml/TaskList.fxml"))
            val root = loader.load<javafx.scene.Parent>()

            // Create the scene and link CSS for styling
            val scene = Scene(root)
            scene.stylesheets.add(javaClass.getResource("/css/style.css").toExternalForm())

            // Configure the primary stage
            primaryStage.title = "Task Manager"
            primaryStage.scene = scene
            primaryStage.show()
        } catch (e: Exception) {
            e.printStackTrace() // Log errors to debug issues
        }
    }
}

fun main() {
    Application.launch(AppLauncher::class.java)
}
