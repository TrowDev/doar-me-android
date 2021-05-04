package br.com.unipac.doar_me

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.unipac.doar_me.model.entity.Trabalho
import br.com.unipac.doar_me.model.repository.TrabalhoRepository
import br.com.unipac.doar_me.ui.ListaTrabalhoAdapter
import br.com.unipac.doar_me.ui.TrabalhoFormActivity
import kotlinx.android.synthetic.main.activity_trabalho.*

class TrabalhoActivity : AppCompatActivity() {
    var listaTrabalhoAdapter: ListaTrabalhoAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    // SQLite
    var trabalhoList = ArrayList<Trabalho>()
    var trabalhoRepository = TrabalhoRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabalho)

        initView()

        btnCallFormTrabalho.setOnClickListener {
            var intent = Intent(this@TrabalhoActivity, TrabalhoFormActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun deleteAdapter(position: Int){
        trabalhoList.removeAt(position)
        listaTrabalhoAdapter!!.notifyItemRemoved(position)
    }

    private fun initView() {
        trabalhoList = trabalhoRepository.getAll()
        listaTrabalhoAdapter = ListaTrabalhoAdapter(trabalhoList, this, this::deleteAdapter)
        linearLayoutManager = LinearLayoutManager(this)

        //var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        listTrabalhos.layoutManager = linearLayoutManager
        listTrabalhos.adapter = listaTrabalhoAdapter
    }
}