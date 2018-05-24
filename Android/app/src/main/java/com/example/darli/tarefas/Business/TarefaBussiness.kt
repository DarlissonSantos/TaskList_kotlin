package com.example.darli.tarefas.Business

import android.content.Context
import com.example.darli.tarefas.Entidades.TarefaEntity
import com.example.darli.tarefas.R
import com.example.darli.tarefas.Repository.TarefaRepository
import com.example.darli.tarefas.Util.ValidationException


class TarefaBussiness (val context: Context) {

    private val nTarefaRepository: TarefaRepository = TarefaRepository.getInstance(context)
    fun getList(userId: Int) : MutableList<TarefaEntity> = nTarefaRepository.getList(userId)


    //fun insert( tarefaEntity: TarefaEntity) = nTarefaRepository.insert(tarefaEntity)
    fun insert(tarefaEntity: TarefaEntity) {

        try {
            // Faz a validação dos campos
            if (tarefaEntity.description == "" || tarefaEntity.dueDate == "" || tarefaEntity.priorityId == 0) {
                throw ValidationException(context.getString(R.string.preencha_campos))
            }

            // Faz a inserção da tarefa
            nTarefaRepository.insert(tarefaEntity)
        } catch (e: Exception) {
            throw e
        }
    }
}