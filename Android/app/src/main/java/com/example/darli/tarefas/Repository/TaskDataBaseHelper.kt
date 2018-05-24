package com.example.darli.tarefas.Repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.darli.tarefas.Constants.DataBaseConstants


class TaskDataBaseHelper (context: Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ){
    //SQLLite não pussui tipo booleano
    companion object {
        private val DATABASE_VERSION: Int = 1
        private val DATABASE_NAME: String = "tarefas.db"
    }

    private val createTableUser = """ CREATE TABLE ${DataBaseConstants.USER.TABLE_NAME} (
         ${DataBaseConstants.USER.COLUMS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
         ${DataBaseConstants.USER.COLUMS.NAME} TEXT,
         ${DataBaseConstants.USER.COLUMS.EMAIL} TEXT,
         ${DataBaseConstants.USER.COLUMS.PASSWORD} TEXT
        );"""

    private val createTablePriority = """ CREATE TABLE ${DataBaseConstants.PRIORITY.TABLE_NAME} (
         ${DataBaseConstants.PRIORITY.COLUMS.ID} INTEGER PRIMARY KEY,
         ${DataBaseConstants.PRIORITY.COLUMS.DESCRIPTION} TEXT
        );""" //apagar


    private val createTableTarefa = """ CREATE TABLE ${DataBaseConstants.TAREFA.TABLE_NAME} (
         ${DataBaseConstants.TAREFA.COLUMS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
         ${DataBaseConstants.TAREFA.COLUMS.USERID} TEXT,
          ${DataBaseConstants.TAREFA.COLUMS.PRIORITYID} TEXT,
         ${DataBaseConstants.TAREFA.COLUMS.DESCRIPTION} TEXT,
         ${DataBaseConstants.TAREFA.COLUMS.COMPLETE} INTEGER,
         ${DataBaseConstants.TAREFA.COLUMS.DUEDATE} TEXT
        );"""

    // Populando dados de prioridades /// apgar
    private val inserPriorities = ("INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME}"
            + " values (1, 'Baixa'), (2, 'Média'), (3, 'Alta'), (4, 'Crítica')")


    private val deleteTableUser = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"
    private val deleteTableTarefa = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"
    private val deleteTablePriority = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}" //apagar

    override fun onCreate(sqlLite: SQLiteDatabase) {
            //criação do bando de dados se ainda não existir
        sqlLite.execSQL(createTableUser)
        sqlLite.execSQL(createTableTarefa)
        sqlLite.execSQL(createTablePriority)//apagar
       sqlLite.execSQL(inserPriorities)//apagar

    }

    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    //Remover tabela se existir
        sqlLite.execSQL(deleteTableUser)
        sqlLite.execSQL(deleteTableTarefa)
        sqlLite.execSQL(deleteTablePriority) //apagar
    // Crio a tabela
        sqlLite.execSQL(createTableUser)

    }
}