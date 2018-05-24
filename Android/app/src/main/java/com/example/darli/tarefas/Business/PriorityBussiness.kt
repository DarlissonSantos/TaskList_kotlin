package com.example.darli.tarefas.Business

import android.content.Context
import com.example.darli.tarefas.Entidades.PriorityEntity
import com.example.darli.tarefas.Repository.PriorityRepository


class PriorityBussiness(context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)
    fun getList():MutableList<PriorityEntity> = mPriorityRepository.getList() //// alterado

}