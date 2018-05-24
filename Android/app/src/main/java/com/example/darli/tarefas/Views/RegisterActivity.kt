package com.example.darli.tarefas.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.darli.tarefas.Business.UserBusiness
import com.example.darli.tarefas.R
import com.example.darli.tarefas.Util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener { //implementa a interface view

    private lateinit var nUserBusiness: UserBusiness   // Lateinit = iniciar tardiamente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //eventos do meu botão
        setListeners()

        //instanciar varáveis da classe
        nUserBusiness = UserBusiness(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSave -> {
                handleSave()
            }
        }


    }

    private fun setListeners() {

        buttonSave.setOnClickListener(this)
    }

    private fun handleSave() { // função para salvar o usuário no banco
        //tratamento de Erros
        try {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            //faz a inserção do usuário
            nUserBusiness.insert(name, email, password)

            // se a verificação der certo, usuário para tela inicial
            startActivity(Intent(this, MainActivity::class.java))
            // impede que ele volte para tela de login, "mato" a Activity login
            finish()

        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            //Toast.makeText(this, getString(R.string.Erro_Inesperado), Toast.LENGTH_LONG).show()


        }


    }
}
