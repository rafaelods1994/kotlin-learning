package com.rafael.hexagonal.javafxdemo.view

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class TaskListView(stage: Stage) {
    init {
        val root: Parent = FXMLLoader.load(javaClass.getResource("/fxml/TaskList.fxml"))
        stage.scene = Scene(root)
        stage.show()
    }
}
