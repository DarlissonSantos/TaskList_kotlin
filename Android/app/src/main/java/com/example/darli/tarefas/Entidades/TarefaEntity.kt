package com.example.darli.tarefas.Entidades

data class TarefaEntity(
    val id: Int,
    val userId: Int,
    var priorityId: Int,
    var description: String,
    var dueDate: String,
    var complete: Boolean )