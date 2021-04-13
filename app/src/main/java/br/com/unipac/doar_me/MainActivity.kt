package br.com.unipac.doar_me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.unipac.doar_me.R
import br.com.unipac.doar_me.model.entity.Disciplina
import br.com.unipac.doar_me.model.entity.Pessoa
import br.com.unipac.doar_me.model.repository.PessoaRepository
import br.com.unipac.doar_me.ui.ListaPessoaAdapter
import br.com.unipac.doar_me.ui.PessoaFormActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var listaPessoaAdapter: ListaPessoaAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    // SQLite
    var pessoaList = ArrayList<Pessoa>()
    var pessoaRepository = PessoaRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        btnCallForm.setOnClickListener {
            var intent = Intent(this@MainActivity, PessoaFormActivity::class.java)
            startActivity(intent)
        }
        btnDisciplinas.setOnClickListener {
            var intent = Intent(this@MainActivity, DisciplinaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun deleteAdapter(position: Int){
        pessoaList.removeAt(position)
        listaPessoaAdapter!!.notifyItemRemoved(position)
    }

    private fun initView() {
        pessoaList = pessoaRepository.getAll()
        listaPessoaAdapter = ListaPessoaAdapter(pessoaList, this, this::deleteAdapter)
        linearLayoutManager = LinearLayoutManager(this)

        //var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        listPessoas.layoutManager = linearLayoutManager
        listPessoas.adapter = listaPessoaAdapter
    }
}