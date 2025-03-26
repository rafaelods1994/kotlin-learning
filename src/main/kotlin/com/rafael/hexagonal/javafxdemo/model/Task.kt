package com.rafael.hexagonal.javafxdemo.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val priority: String
)
