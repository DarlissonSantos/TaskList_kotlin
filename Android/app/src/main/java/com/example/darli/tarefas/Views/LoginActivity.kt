package com.example.darli.tarefas.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.darli.tarefas.R
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import android.widget.Toast
import com.example.darli.tarefas.Business.UserBusiness
import com.example.darli.tarefas.Constants.TarefasConstantes
import com.example.darli.tarefas.Util.PreferenciaSeguranca

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var nUserBussiness: UserBusiness           //não instâncio logo pois não possuo um contexto pronto
    private lateinit var nPreferenciaSeguranca : PreferenciaSeguranca // instancio minha priferênciaSegurança para consultas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Instanciar as variáveis da classe
        nUserBussiness = UserBusiness(this)
        nPreferenciaSeguranca = PreferenciaSeguranca(this)

        setListeners()
        verifyLoggeUser()
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.buttonLogin -> {
                handleLogin() // colocar todo método handlelogin aqui
            }
        }

    }

    private  fun setListeners(){
        buttonLogin.setOnClickListener(this)
    }
    //Verifico se o susário ja fez loguin
    private fun verifyLoggeUser() {
        val userid = nPreferenciaSeguranca.getStoredString(TarefasConstantes.KEY.USER_ID)
        val name = nPreferenciaSeguranca.getStoredString(TarefasConstantes.KEY.USER_NAME)

        if(userid != "" && name != ""){ // Se na minha instacia nPreferencia de segurança conter valores, usuário já logado
            startActivity(Intent(this,MainActivity::class.java))
        }


    }

    private fun handleLogin(){
        val email= editEmail.text.toString()
        val password = editPassword.text.toString()

        if (nUserBussiness.login(email,password)){
            // se a verificação der certo, manda usuário para tela inicial
            startActivity(Intent(this,MainActivity::class.java))
            // impede que ele volte para tela de login, "mato" a Activity login
            finish()


        }else{
            Toast.makeText(this,getString(R.string.Usuario_Senha_Incorretos),Toast.LENGTH_LONG).show()
        }
    }
}
