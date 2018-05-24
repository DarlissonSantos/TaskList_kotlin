package com.example.darli.tarefas.Repository

import android.content.Context
import android.database.Cursor
import com.example.darli.tarefas.Constants.DataBaseConstants
import com.example.darli.tarefas.Entidades.PriorityEntity

class PriorityRepository private constructor(context: Context){
    // Singleton limita o acesso ao bando de dados 1 por vez, evitando erros

    private var nTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object { // tornar acessível, minha classe privada
        fun getInstance(context: Context): PriorityRepository {
            if (INSTANCE == null) {
                INSTANCE = PriorityRepository(context)
            }
            return INSTANCE as PriorityRepository
        }

        private var INSTANCE: PriorityRepository? = null
    }

    /**
     * Carrega todos os prioridades
     */
    fun getList(): MutableList<PriorityEntity> {
        val list = mutableListOf<PriorityEntity>()

        try {
            val cursor: Cursor
            val db = nTaskDataBaseHelper.readableDatabase

            // Lista de prioridades
            cursor = db.rawQuery("select * from ${DataBaseConstants.PRIORITY.TABLE_NAME}", null)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.PRIORITY.COLUMS.ID))
                    val description = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.PRIORITY.COLUMS.DESCRIPTION))

                    // Instancia classe de prioridade
                    val guestEntity = PriorityEntity(id, description)

                    // Adiciona item a lista
                    list.add(guestEntity)
                }
            }

            // Fecha cursor
            cursor.close()

        } catch (e: Exception) {
            return list
        }

        // Retorno objeto com dados
        return list
    }

}