package com.example.darli.tarefas.Views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.darli.tarefas.Constants.TarefasConstantes
import com.example.darli.tarefas.R
import com.example.darli.tarefas.Util.PreferenciaSeguranca
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var nPreferenciaSeguranca: PreferenciaSeguranca

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

       /* fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

    // quando algum item do menu for clicado mainActivity trata
        nav_view.setNavigationItemSelectedListener(this)

        // Instanciar Variáveis
        nPreferenciaSeguranca = PreferenciaSeguranca(this)

        iniciarDefaultFragment()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_done -> {             // lidar com ação do botão das tarefas Feitas
                fragment = ListaTarefasFragment.newInstance()
            }
            R.id.nav_todo -> {
                fragment = ListaTarefasFragment.newInstance()
            }
            R.id.nav_Logout -> {
                handleLogout()
            }
        }

         val framentManager =supportFragmentManager
        framentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()     // fragment = conteúdo

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun iniciarDefaultFragment(){
        val fragment: Fragment? = ListaTarefasFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()     // fragment = conteúdo
    }

    private fun handleLogout(){
        //apagar os dados do usuário
        nPreferenciaSeguranca.removeStoredString(TarefasConstantes.KEY.USER_ID)
        nPreferenciaSeguranca.removeStoredString(TarefasConstantes.KEY.USER_NAME)
        nPreferenciaSeguranca.removeStoredString(TarefasConstantes.KEY.USER_EMAIL)

        startActivity(Intent(this, LoginActivity::class.java))
        // impede que ele volte para tela de login, "mato" a Activity login
        finish()

    }
}
