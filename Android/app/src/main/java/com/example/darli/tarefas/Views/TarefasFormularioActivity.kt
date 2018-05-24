package com.example.darli.tarefas.Views

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.example.darli.tarefas.Business.PriorityBussiness
import com.example.darli.tarefas.Business.TarefaBussiness
import com.example.darli.tarefas.Constants.TarefasConstantes
import com.example.darli.tarefas.Entidades.PriorityEntity
import com.example.darli.tarefas.Entidades.TarefaEntity
import com.example.darli.tarefas.R
import com.example.darli.tarefas.Util.PreferenciaSeguranca
import kotlinx.android.synthetic.main.activity_tarefas_formulario.*
import java.text.SimpleDateFormat


class TarefasFormularioActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private lateinit var nPriorityBussiness: PriorityBussiness
    private lateinit var nTarefaBussiness: TarefaBussiness
    private lateinit var nSecurityPreferenciaSeguranca: PreferenciaSeguranca
    private val nsimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy") // formatar minha data

    private var lstPrioritiesEntity: MutableList<PriorityEntity> = mutableListOf() // excluir
    private var lstPriorityId: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas_formulario)

        nPriorityBussiness = PriorityBussiness(this)
        nTarefaBussiness = TarefaBussiness(this)
        nSecurityPreferenciaSeguranca = PreferenciaSeguranca(this)

        setListeners()
        //carrega minha listagem de prioridades
        carregarPriorities()

    }
    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonDate -> {
                openDatePickerDialog()
            }
            R.id.buttonSave -> {
                buttonsalvar()
            }
        }

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)
        buttonDate.text = nsimpleDateFormat.format(calendar.time)

    }

    private fun setListeners(){
        buttonDate.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    private fun buttonsalvar(){
       try {
           val descricao: String = editDescription.text.toString()
           val complete : Boolean = checkboxComplete.isChecked
           val date : String = buttonDate.text.toString()
           val userId = nSecurityPreferenciaSeguranca.getStoredString(TarefasConstantes.KEY.USER_ID).toInt()
           val priority = lstPriorityId[spinnerPriority.selectedItemPosition] // excluir

           val tarefaEntity = TarefaEntity(0, userId, priority, descricao, date, complete)
           nTarefaBussiness.insert(tarefaEntity)

           finish()


       }catch (e: Exception){
           Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
           //Toast.makeText(this,getString(R.string.Erro_Inesperado),Toast.LENGTH_LONG).show()
       }
    }


    private fun openDatePickerDialog(){
        val n = java.util.Calendar.getInstance()
        val year = n.get(java.util.Calendar.YEAR)
        val month = n.get(java.util.Calendar.MONTH)
        val day = n.get(java.util.Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, day).show()

    }

    private fun carregarPriorities(){
        lstPrioritiesEntity = nPriorityBussiness.getList() // carregar minha lista
        lstPriorityId = lstPrioritiesEntity.map { it.id }.toMutableList()
        val lstPriorities = lstPrioritiesEntity.map{ it.description }    // collection MAP transforma um lista em outra
//
// val lstPriorities: MutableList<String> = mutableListOf()
//        for (i in 0..lstPrioritiesEntity.count()){
//            lstPriorities.add(lstPrioritiesEntity[i].description)
//        }


        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lstPriorities) //layout padrão // no spinner tenho que passar uma lista
        spinnerPriority.adapter = adapter

    }
}
