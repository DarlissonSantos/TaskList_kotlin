package com.example.darli.tarefas.Constants

class DataBaseConstants {

    object USER{
        val TABLE_NAME = "user"

        object COLUMS{
            val ID = "id"
            val NAME = "name"
            val EMAIL = "email"
            val PASSWORD = "password"
        }
    }

    object PRIORITY{
        val TABLE_NAME = "priority"

        object COLUMS{
            val ID = "id"
            val DESCRIPTION = "description"
                    }
    }

    object TAREFA {
        val TABLE_NAME = "tarefa"

        object COLUMS {
            val ID = "id"
            val USERID = "userid"
            val DESCRIPTION = "description"
            val DUEDATE = "duedate"
            val PRIORITYID = "priorityid"
            val COMPLETE = "complete"
        }

    }
}