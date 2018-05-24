package com.example.darli.tarefas.Business

import android.content.Context
import com.example.darli.tarefas.Constants.TarefasConstantes
import com.example.darli.tarefas.Entidades.UserEntidades
import com.example.darli.tarefas.R
import com.example.darli.tarefas.Repository.UserRepository
import com.example.darli.tarefas.Util.PreferenciaSeguranca
import com.example.darli.tarefas.Util.ValidationException

class UserBusiness (val context: Context){
    private val nUserRepository : UserRepository = UserRepository.getInstance(context)
    private val nSecurityPreferences : PreferenciaSeguranca = PreferenciaSeguranca(context)

    // Método do login
    fun login (email: String, password: String): Boolean{
        val user: UserEntidades? = nUserRepository.get(email,password)
        return if (user != null){
            nSecurityPreferences.storeString(TarefasConstantes.KEY.USER_ID, user.id.toString())
            nSecurityPreferences.storeString(TarefasConstantes.KEY.USER_NAME,user.name)
            nSecurityPreferences.storeString(TarefasConstantes.KEY.USER_EMAIL, user.email)
            true
        }else {
            false
        }
    }

    fun insert (name: String, email: String, password: String){
       try{
           if (name == "" || email == "" || password == ""){
               throw ValidationException(context.getString(R.string.Informe_ALL_Campos))
           }
           if (nUserRepository.isEmailExistent(email)){
               throw ValidationException(context.getString(R.string.Email_Existente))

           }
           val userId = nUserRepository.insert(name, email, password)

           //Salvar Dados do usuário no shared
           nSecurityPreferences.storeString(TarefasConstantes.KEY.USER_ID, userId.toString())
           nSecurityPreferences.storeString(TarefasConstantes.KEY.USER_NAME, name)
           nSecurityPreferences.storeString(TarefasConstantes.KEY.USER_EMAIL, email)

       } catch (e: Exception){
           throw e
       }

    }
}