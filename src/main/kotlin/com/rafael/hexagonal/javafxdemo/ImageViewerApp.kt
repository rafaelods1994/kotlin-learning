package com.rafael.hexagonal.javafxdemo

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class ImageViewerApp : Application() {
    override fun start(primaryStage: Stage) {
        // Load an image
        val imageUrl = "https://plus.unsplash.com/premium_photo-1664474619075-644dd191935f?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aW1hZ2V8ZW58MHx8MHx8fDA%3D" // Replace with your image URL
        val image = Image(imageUrl)
        val imageView = ImageView(image)
        imageView.isPreserveRatio = true
        imageView.fitWidth = 400.0 // Adjust width if needed

        // Add the image to the layout
        val root = StackPane(imageView)
        val scene = Scene(root, 400.0, 400.0)

        // Configure and display the window
        primaryStage.title = "JavaFX Image Viewer"
        primaryStage.scene = scene
        primaryStage.show()
    }
}
