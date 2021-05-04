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
import br.com.unipac.doar_me.model.entity.Pessoa
import br.com.unipac.doar_me.model.entity.Trabalho
import br.com.unipac.doar_me.model.repository.PessoaRepository
import br.com.unipac.doar_me.model.repository.TrabalhoRepository
import kotlinx.android.synthetic.main.content_item_pessoa.view.*

class ListaTrabalhoAdapter (trabalhoList: List<Trabalho>, internal var cxt: Context, private val callbacks: (Int) -> Unit): RecyclerView.Adapter<ListaTrabalhoAdapter.ViewHolder>()  {

    internal var trabalhoList: List<Trabalho> = ArrayList<Trabalho>()
    init {
        this.trabalhoList = trabalhoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(cxt).inflate(R.layout.content_item_trabalho, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var trab = trabalhoList[position]

        holder.tvName.text = trab.titulo
        if (position % 2 == 0)
            holder.tvName.setBackgroundColor(Color.GRAY)
        else
            holder.tvName.setBackgroundColor(Color.WHITE)

        holder.tvName.setOnClickListener {
            var intent = Intent(cxt, TrabalhoFormActivity::class.java)
            intent.putExtra("edit", true)
            intent.putExtra("trabalhoId", trab.id)
            cxt.startActivity(intent)
        }

        holder.btnDeletar.setOnClickListener {
            val trabalhoRepository = TrabalhoRepository(cxt)
            trabalhoRepository.delete(trab.id)
            callbacks(position)
        }
    }

    override fun getItemCount(): Int {
        return trabalhoList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.tvAdpName
        var btnDeletar: Button = view.btnAdpDel
    }
}