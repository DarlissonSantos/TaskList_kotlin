package com.example.darli.tarefas.Repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.darli.tarefas.Constants.DataBaseConstants
import com.example.darli.tarefas.Entidades.PriorityEntity
import com.example.darli.tarefas.Entidades.TarefaEntity

class TarefaRepository private constructor(context: Context) {
    // Singleton limita o acesso ao bando de dados 1 por vez, evitando erros

    private var nTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object { // tornar acessível, minha classe privada
        fun getInstance(context: Context): TarefaRepository {
            if (INSTANCE == null) {
                INSTANCE = TarefaRepository(context)
            }
            return INSTANCE as TarefaRepository
        }

        private var INSTANCE: TarefaRepository? = null
    }

    fun get(tarefaID: Int): TarefaEntity? {

        var tarefaEntity: TarefaEntity? = null

        try {
            val cursor: Cursor
            val db = nTaskDataBaseHelper.readableDatabase

            // Colunas que serão retornadas
            val projection = arrayOf(DataBaseConstants.TAREFA.COLUMS.ID
                    , DataBaseConstants.TAREFA.COLUMS.USERID
                    , DataBaseConstants.TAREFA.COLUMS.PRIORITYID
                    , DataBaseConstants.TAREFA.COLUMS.DESCRIPTION
                    , DataBaseConstants.TAREFA.COLUMS.DUEDATE
                    , DataBaseConstants.TAREFA.COLUMS.COMPLETE)

            // Filtro
            val selection = "${DataBaseConstants.TAREFA.COLUMS.ID} = ?"
            val selectionArgs = arrayOf(tarefaID.toString())

            // Carrega usuário
            cursor = db.query(DataBaseConstants.TAREFA.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            // Verifica se existe retorno
            if (cursor.count > 0) {
                cursor.moveToFirst()

                val tarefaID = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.USERID))
                val userId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.USERID))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.DESCRIPTION))
                val dueDate = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.DUEDATE))
                val priorityId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.PRIORITYID))
                val complete = (cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.COMPLETE)) == 1)

                // Atribui valor a variável do usuário
                tarefaEntity = TarefaEntity(tarefaID, userId, priorityId, description, dueDate, complete)
            }

            // Fecha cursor
            cursor.close()

        } catch (e: Exception) {
            return tarefaEntity
        }

        // Retorno objeto com dados
        return tarefaEntity
    }


    /**
     * Carrega todos os prioridades
     */
    fun getList(userId: Int): MutableList<TarefaEntity> {
        val list = mutableListOf<TarefaEntity>()

        try {
            val cursor: Cursor
            val db = nTaskDataBaseHelper.readableDatabase // instancio bando de dados p/ leitura

            // Lista de prioridades
            cursor = db.rawQuery("select * from ${DataBaseConstants.TAREFA.TABLE_NAME}" +
                    "WHERE ${DataBaseConstants.TAREFA.COLUMS.USERID} = $userId", null)  // filtro da tarefa diacordo com o usuário
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.ID))
                    val userId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.USERID))
                    val description = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.DESCRIPTION))
                    val dueDate = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.DUEDATE))
                    val priorityId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.PRIORITYID))
                    val complete = (cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.TAREFA.COLUMS.COMPLETE)) == 1)


                    // Adiciona item a lista
                    list.add(TarefaEntity(id, userId, priorityId, description, dueDate, complete))
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

    /**
     * Faz a inserção da tarefa
     * */
    fun insert(tarefa: TarefaEntity) {
        try {

            // Para fazer escrita de dados
            val db = nTaskDataBaseHelper.writableDatabase
            // Faz a conversão do boolean para inteiro
            val complete: Int = if (tarefa.complete) 1 else 0

            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.TAREFA.COLUMS.USERID, tarefa.userId)
            insertValues.put(DataBaseConstants.TAREFA.COLUMS.PRIORITYID, tarefa.priorityId)
            insertValues.put(DataBaseConstants.TAREFA.COLUMS.DESCRIPTION, tarefa.description)
            insertValues.put(DataBaseConstants.TAREFA.COLUMS.DUEDATE, tarefa.dueDate)
            insertValues.put(DataBaseConstants.TAREFA.COLUMS.COMPLETE, complete)


            // Faz a inserção
            db.insert(DataBaseConstants.TAREFA.TABLE_NAME, null, insertValues)

        } catch (e: Exception) {
            throw e
        }
    }


    /**
     * Faz a atualização da tarefa
     * */
    fun update(tarefa: TarefaEntity) {
        try {

            // Para fazer escrita de dados
            val db = nTaskDataBaseHelper.writableDatabase

            // Faz a conversão do boolean para inteiro
            val complete: Int = if (tarefa.complete) 1 else 0

            val updateValues = ContentValues()
            updateValues.put(DataBaseConstants.TAREFA.COLUMS.USERID, tarefa.userId)
            updateValues.put(DataBaseConstants.TAREFA.COLUMS.DESCRIPTION, tarefa.description)
            updateValues.put(DataBaseConstants.TAREFA.COLUMS.DUEDATE, tarefa.dueDate)
            updateValues.put(DataBaseConstants.TAREFA.COLUMS.PRIORITYID, tarefa.priorityId)
            updateValues.put(DataBaseConstants.TAREFA.COLUMS.COMPLETE, complete)

            // Critério de seleção
            val selection = DataBaseConstants.TAREFA.COLUMS.ID + " = ?"
            val selectionArgs = arrayOf(tarefa.id.toString())

            // Executa
            db.update(DataBaseConstants.TAREFA.TABLE_NAME, updateValues, selection, selectionArgs)

        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Faz a remoção da tarefa
     * */
    fun delete(id: Int) {
        try {
            // Para fazer escrita de dados
            val db = nTaskDataBaseHelper.writableDatabase

            val whereClause = "${DataBaseConstants.TAREFA.COLUMS.ID} = ?"
            val whereArgs = arrayOf(id.toString())
            db.delete(DataBaseConstants.TAREFA.TABLE_NAME, whereClause, whereArgs)


        } catch (e: Exception) {
            throw e
        }
    }


}