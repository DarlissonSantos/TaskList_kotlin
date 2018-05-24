package com.example.darli.tarefas.Views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.darli.tarefas.Adapter.ListaTarefasAdapter
import com.example.darli.tarefas.Business.TarefaBussiness
import com.example.darli.tarefas.Constants.TarefasConstantes

import com.example.darli.tarefas.R
import com.example.darli.tarefas.Util.PreferenciaSeguranca

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListaTarefasFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListaTarefasFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ListaTarefasFragment : Fragment(), View.OnClickListener {

    private lateinit var nContext: Context
    private lateinit var  nRecycleList: RecyclerView
    private lateinit var nTarefasBusiness: TarefaBussiness
    private lateinit var nPreferenciaSeguranca: PreferenciaSeguranca


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListaTarefasFragment.
         */

        //@JvmStatic
        fun newInstance(): ListaTarefasFragment {
            //                    arguments = Bundle().apply
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
            return ListaTarefasFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
    }

    //inflater -> inicia um layout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_lista_tarefas, container, false)
        rootView.findViewById<FloatingActionButton>(R.id.addtarefa).setOnClickListener(this)
        nContext = rootView.context

        nTarefasBusiness = TarefaBussiness(nContext)
        nPreferenciaSeguranca = PreferenciaSeguranca(nContext)

        //implementar minha reclyclerview
        // Obter o elemento,,,definir a recycleview
        nRecycleList = rootView.findViewById(R.id.recyclerViewListaTarefa)

        //Definir o itens de listagem


//        for(i in 0..50){
//            listatarefa.add(listatarefa[0].copy(description = "descrição &i"))
//        }
        nRecycleList.adapter = ListaTarefasAdapter(mutableListOf())

        // definir um layout
        nRecycleList.layoutManager = LinearLayoutManager(nContext)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTarefa()
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.addtarefa -> {
                startActivity(Intent(nContext, TarefasFormularioActivity::class.java))
            }
        }
    }
    private fun loadTarefa(){
        val userId = nPreferenciaSeguranca.getStoredString(TarefasConstantes.KEY.USER_ID).toInt()
        val listatarefa = nTarefasBusiness.getList(userId)

        nRecycleList.adapter = ListaTarefasAdapter(listatarefa)

    }
}


