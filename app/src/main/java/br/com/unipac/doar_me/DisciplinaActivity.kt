package br.com.unipac.doar_me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.unipac.doar_me.model.entity.Disciplina
import br.com.unipac.doar_me.model.repository.DisciplinaRepository
import br.com.unipac.doar_me.ui.DisciplinaFormActivity
import br.com.unipac.doar_me.ui.ListaDisciplinasAdapter
import br.com.unipac.doar_me.ui.PessoaFormActivity
import kotlinx.android.synthetic.main.activity_disciplinas.*
import kotlinx.android.synthetic.main.activity_main.*

class DisciplinaActivity : AppCompatActivity() {
    var listaDisciplinaAdapter: ListaDisciplinasAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    // SQLite
    var disciplinaList = ArrayList<Disciplina>()
    var disciplinaRepository = DisciplinaRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disciplinas)

        initView()

        btnCallFormDisciplinas.setOnClickListener {
            var intent = Intent(this@DisciplinaActivity, DisciplinaFormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun deleteAdapter(position: Int){
        disciplinaList.removeAt(position)
        listaDisciplinaAdapter!!.notifyItemRemoved(position)
    }

    private fun initView() {
        disciplinaList = disciplinaRepository.getAll()
        listaDisciplinaAdapter = ListaDisciplinasAdapter(disciplinaList, this, this::deleteAdapter)
        linearLayoutManager = LinearLayoutManager(this)

        //var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        listDisciplinas.layoutManager = linearLayoutManager
        listDisciplinas.adapter = listaDisciplinaAdapter
    }
}