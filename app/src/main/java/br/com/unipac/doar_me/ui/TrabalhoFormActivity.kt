package br.com.unipac.doar_me.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.unipac.doar_me.R
import br.com.unipac.doar_me.model.entity.Trabalho
import br.com.unipac.doar_me.model.repository.TrabalhoRepository

import kotlinx.android.synthetic.main.activity_pessoa_form.calcelBtn
import kotlinx.android.synthetic.main.activity_trabalho_form.*

class TrabalhoFormActivity : AppCompatActivity() {

    val trabalhoRepository = TrabalhoRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabalho_form)

        val edit = intent.getBooleanExtra("edit", false)
        val trabalhoId = intent.getIntExtra("trabalhoId", -1)

        //val nameEdt = findViewById<EditText>(R.id.nameEdt)
        //val btnInsert = findViewById<Button>(R.id.insertBtn)
        //val cancelBtn = findViewById<Button>(R.id.calcelBtn)

        if (edit) {
            val trabalho = trabalhoRepository.getById(trabalhoId)
            tituloEdt.setText(trabalho.titulo)
            descricaoEdt.setText(trabalho.descricao)
            notaEdt.setText("${trabalho.nota}")
            trabalhoBtn.setText("Editar")
        }

        trabalhoBtn.setOnClickListener {

            var tituloEdt = tituloEdt.text.toString()
            var descricaoEdt = descricaoEdt.text.toString()
            var notaEdt = notaEdt.text.toString()

            if (tituloEdt == "") {
                Toast.makeText(this, "Nome não pode ser Vazio", Toast.LENGTH_LONG).show()
            } else if(descricaoEdt == ""){
                Toast.makeText(this, "Descrição não pode ser vazia", Toast.LENGTH_LONG).show()
            } else if(notaEdt == ""){
                Toast.makeText(this, "Nota não pode ser vazia", Toast.LENGTH_LONG).show()
            }else {
                if (edit) {
                    val trab = Trabalho(trabalhoId, tituloEdt, descricaoEdt, notaEdt.toDouble())
                    var edited = trabalhoRepository.update(trab)
                    if (edited)
                        Toast.makeText(this, "Trabalho Alterado", Toast.LENGTH_LONG).show()
                } else {
                    val trab = Trabalho(0, tituloEdt, descricaoEdt, notaEdt.toDouble())
                    var saved = trabalhoRepository.add(trab)
                    if (saved)
                        Toast.makeText(this, "Trabalho cadastross", Toast.LENGTH_LONG).show()
                }
            }
        }

        calcelBtn.setOnClickListener {
            finish()
        }
    }
}