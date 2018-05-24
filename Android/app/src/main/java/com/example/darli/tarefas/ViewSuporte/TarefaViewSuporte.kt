package com.example.darli.tarefas.ViewSuporte

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.darli.tarefas.Entidades.TarefaEntity
import com.example.darli.tarefas.R


class TarefaViewSuporte(itemView:View) : RecyclerView.ViewHolder(itemView) {
    private val nTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val nTextDate: TextView = itemView.findViewById(R.id.textDate)
    private val nTextPriority: TextView = itemView.findViewById(R.id.textPrioridade)
    private val nImageTarefa: ImageView = itemView.findViewById(R.id.imageTarefa)


    fun bindData(tarefa: TarefaEntity){
        nTextDescription.text = tarefa.description
        nTextPriority.text = ""
        nTextDate.text = tarefa.dueDate

        if (tarefa.complete){
            nImageTarefa.setImageResource(R.drawable.ic_realizada)
        }
    }
}