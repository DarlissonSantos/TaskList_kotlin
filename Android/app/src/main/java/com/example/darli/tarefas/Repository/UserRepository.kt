package com.example.darli.tarefas.Repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.example.darli.tarefas.Constants.DataBaseConstants
import com.example.darli.tarefas.Entidades.UserEntidades

class UserRepository private constructor(context: Context) {
    // Singleton limita o acesso ao bando de dados 1 por vez, evitando erros

    private var nTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object { // tornar acessível, minha classe privada
        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }

        private var INSTANCE: UserRepository? = null
    }

    //obter dados do usuário atraves do email e senha
    fun get(email: String, password: String): UserEntidades?{

        var userEntidades : UserEntidades? = null
        try {
            val cursor: Cursor
            val db = nTaskDataBaseHelper.readableDatabase

            // Colunas que serão retornadas
            val projection = arrayOf(DataBaseConstants.USER.COLUMS.ID
                    , DataBaseConstants.USER.COLUMS.NAME
                    , DataBaseConstants.USER.COLUMS.EMAIL)

            // Filtro
            val selection = " ${DataBaseConstants.USER.COLUMS.EMAIL} = ? AND ${DataBaseConstants.USER.COLUMS.PASSWORD} = ?"
            val selectionArgs = arrayOf(email, password)

            // Carrega usuário
            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if (cursor.count > 0){
                cursor.moveToFirst()
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMS.ID))
                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMS.NAME))
                val mail = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMS.EMAIL))

                //preencho a entidade do usuário
                userEntidades = UserEntidades(userId,name,mail)
            }


        }catch (e: Exception){
            return userEntidades
        }

        return userEntidades
    }

    //Validação do email.
    fun isEmailExistent(email: String): Boolean {

        var ret: Boolean = false
        try {
            var cursor: Cursor
            val db = nTaskDataBaseHelper.readableDatabase

            // Colunas que serão retornadas
            val projection = arrayOf(DataBaseConstants.USER.COLUMS.ID)

            // Filtro
            val selection = DataBaseConstants.USER.COLUMS.EMAIL + " = ?"
            val selectionArgs = arrayOf(email)

            // Carrega usuário - Linha única
            // cursor = db.rawQuery("select * from user where email = '$email'", null)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            ret = cursor.count > 0 // se retormar maior que zero exixte emial no banco de dados

            // Fecha cursor
            cursor.close()

        } catch (e: Exception) {
            throw e
        }

        // Retorno objeto com dados
        return ret
    }

    fun insert(name: String, email: String, password: String): Int {
        try {

            val db = nTaskDataBaseHelper.writableDatabase       //inserir no banco

            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.USER.COLUMS.NAME, name)
            insertValues.put(DataBaseConstants.USER.COLUMS.EMAIL, email)
            insertValues.put(DataBaseConstants.USER.COLUMS.PASSWORD, password)

            return db.insert(DataBaseConstants.USER.TABLE_NAME, null, insertValues).toInt()
        }catch (e: Exception){
            throw e
        }

    }
}