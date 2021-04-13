package br.com.unipac.doar_me.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.unipac.doar_me.R
import br.com.unipac.doar_me.model.entity.Pessoa
import br.com.unipac.doar_me.model.repository.PessoaRepository

import kotlinx.android.synthetic.main.activity_pessoa_form.*

class PessoaFormActivity : AppCompatActivity() {

    val pessoaRepository = PessoaRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pessoa_form)

        val edit = intent.getBooleanExtra("edit", false)
        val pessoaId = intent.getIntExtra("pessoaId", -1)

        //val nameEdt = findViewById<EditText>(R.id.nameEdt)
        //val btnInsert = findViewById<Button>(R.id.insertBtn)
        //val cancelBtn = findViewById<Button>(R.id.calcelBtn)

        if (edit) {
            val pessoa = pessoaRepository.getById(pessoaId)
            nameEdt.setText(pessoa.name)
            insertBtn.setText("Editar")
        }

        insertBtn.setOnClickListener {

            var nameEdt = nameEdt.text.toString()

            if (nameEdt == "") {
                Toast.makeText(this, "Nome não pode ser Vazio", Toast.LENGTH_LONG).show()
            } else {
                if (edit) {
                    val pessoa = Pessoa(pessoaId, nameEdt)
                    var edited = pessoaRepository.update(pessoa)
                    if (edited)
                        Toast.makeText(this, "Dados Alterados", Toast.LENGTH_LONG).show()
                } else {
                    var pessoa = Pessoa(0, nameEdt)
                    var saved = pessoaRepository.add(pessoa)
                    if (saved)
                        Toast.makeText(this, "Dados cadastross", Toast.LENGTH_LONG).show()
                }
            }
        }

        calcelBtn.setOnClickListener {
            finish()
        }
    }
}