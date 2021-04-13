package br.com.unipac.doar_me.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.unipac.doar_me.R
import br.com.unipac.doar_me.model.entity.Disciplina
import br.com.unipac.doar_me.model.entity.Pessoa
import br.com.unipac.doar_me.model.repository.DisciplinaRepository
import br.com.unipac.doar_me.model.repository.PessoaRepository
import kotlinx.android.synthetic.main.content_item_disciplina.view.*
import kotlinx.android.synthetic.main.content_item_pessoa.view.*

class ListaDisciplinasAdapter (disciplinaList: List<Disciplina>, internal var cxt: Context, private val callbacks: (Int) -> Unit): RecyclerView.Adapter<ListaDisciplinasAdapter.ViewHolder>()  {

    internal var disciplinaList: List<Disciplina> = ArrayList<Disciplina>()
    init {
        this.disciplinaList = disciplinaList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(cxt).inflate(R.layout.content_item_disciplina, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var disciplina = disciplinaList[position]

        holder.tvName.text = disciplina.name
        if (position % 2 == 0)
            holder.tvName.setBackgroundColor(Color.GRAY)
        else
            holder.tvName.setBackgroundColor(Color.WHITE)

        holder.tvName.setOnClickListener {
            var intent = Intent(cxt, DisciplinaFormActivity::class.java)
            intent.putExtra("edit", true)
            intent.putExtra("disciplinaId", disciplina.id)
            cxt.startActivity(intent)
        }

        holder.btnDeletar.setOnClickListener {
            val disciplinaRepository = DisciplinaRepository(cxt)
            disciplinaRepository.delete(disciplina.id)
            callbacks(position)
        }
    }

    override fun getItemCount(): Int {
        return disciplinaList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.tvAdpDisciplinaName
        var btnDeletar: Button = view.btnAdpDelDisciplina
    }
}