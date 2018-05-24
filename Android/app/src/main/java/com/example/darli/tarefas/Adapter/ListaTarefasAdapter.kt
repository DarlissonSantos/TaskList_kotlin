package com.example.darli.tarefas.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.darli.tarefas.Entidades.TarefaEntity
import com.example.darli.tarefas.R
import com.example.darli.tarefas.ViewSuporte.TarefaViewSuporte

class ListaTarefasAdapter( val listaTarefa: List<TarefaEntity>): RecyclerView.Adapter<TarefaViewSuporte>(){

    private var nCountCreate = 0
    private var nCountBind = 0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewSuporte {
        val context = parent?.context
        val inflater =  LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.rowtarefa_list,parent,false)

        nCountCreate++
        return TarefaViewSuporte(view)

    }

    override fun getItemCount(): Int {
        //retorno o numero de view da minha holder
        return listaTarefa.count()
    }

    override fun onBindViewHolder(holder: TarefaViewSuporte, position: Int) {
        //atribui valores para a linha
        val tarefa = listaTarefa[position]
        holder.bindData(tarefa)

        nCountBind++
    }
}