package br.com.unipac.doar_me.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.unipac.doar_me.R
import br.com.unipac.doar_me.model.entity.Disciplina
import br.com.unipac.doar_me.model.entity.Pessoa
import br.com.unipac.doar_me.model.repository.DisciplinaRepository
import br.com.unipac.doar_me.model.repository.PessoaRepository
import kotlinx.android.synthetic.main.activity_disciplina_form.*

import kotlinx.android.synthetic.main.activity_pessoa_form.*

class DisciplinaFormActivity : AppCompatActivity() {

    val disciplinaRepository = DisciplinaRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disciplina_form)

        val edit = intent.getBooleanExtra("edit", false)
        val disciplinaId = intent.getIntExtra("disciplinaId", -1)

        //val nameEdt = findViewById<EditText>(R.id.nameEdt)
        //val btnInsert = findViewById<Button>(R.id.insertBtn)
        //val cancelBtn = findViewById<Button>(R.id.calcelBtn)

        if (edit) {
            val pessoa = disciplinaRepository.getById(disciplinaId)
            disciplinaEdt.setText(pessoa.name)
            insertDisciplinaBtn.setText("Editar")
        }

        insertDisciplinaBtn.setOnClickListener {

            var disciplinaEdt = disciplinaEdt.text.toString()

            if (disciplinaEdt == "") {
                Toast.makeText(this, "Disciplina não pode ser Vazio", Toast.LENGTH_LONG).show()
            } else {
                if (edit) {
                    val pessoa = Disciplina(disciplinaId, disciplinaEdt)
                    var edited = disciplinaRepository.update(pessoa)
                    if (edited)
                        Toast.makeText(this, "Disciplina Alterada", Toast.LENGTH_LONG).show()
                } else {
                    var pessoa = Disciplina(0, disciplinaEdt)
                    var saved = disciplinaRepository.add(pessoa)
                    if (saved)
                        Toast.makeText(this, "Disciplina cadastrada", Toast.LENGTH_LONG).show()
                }
            }
        }

        cancelDisciplinaBtn.setOnClickListener {
            finish()
        }
    }
}