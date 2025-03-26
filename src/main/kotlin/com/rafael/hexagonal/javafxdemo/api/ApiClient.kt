package com.rafael.hexagonal.javafxdemo.api

import com.rafael.hexagonal.javafxdemo.model.Task
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

object ApiClient {
    private val client = HttpClient()

    // Fetch all tasks from the backend
    suspend fun getTasks(): List<Task> {
        val response: String = client.get("http://localhost:8080/tasks").bodyAsText()
        return Json.decodeFromString(response)
    }

    // Add the createTask function to save a new task
    suspend fun createTask(task: Task) {
        client.post("http://localhost:8080/tasks") {
            // Specify the content type of the request
            contentType(ContentType.Application.Json)
            // Serialize the Task object to JSON and set it as the request body
            setBody(Json.encodeToString(task))
        }
    }
    suspend fun deleteTask(taskId: Int) {
        client.delete("http://localhost:8080/tasks/$taskId")
    }

    // Update an existing task
    suspend fun updateTask(task: Task) {
        client.put("http://localhost:8080/tasks/${task.id}") {
            contentType(ContentType.Application.Json) // Specify JSON content type
            setBody(Json.encodeToString(task)) // Serialize Task object to JSON
        }
    }


}

