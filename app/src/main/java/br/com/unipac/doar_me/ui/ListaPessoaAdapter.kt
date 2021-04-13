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
import br.com.unipac.doar_me.model.repository.PessoaRepository
import kotlinx.android.synthetic.main.content_item_pessoa.view.*

class ListaPessoaAdapter (pessoaList: List<Pessoa>, internal var cxt: Context, private val callbacks: (Int) -> Unit): RecyclerView.Adapter<ListaPessoaAdapter.ViewHolder>()  {

    internal var pessoaList: List<Pessoa> = ArrayList<Pessoa>()
    init {
        this.pessoaList = pessoaList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(cxt).inflate(R.layout.content_item_pessoa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var pessoa = pessoaList[position]

        holder.tvName.text = pessoa.name
        if (position % 2 == 0)
            holder.tvName.setBackgroundColor(Color.GRAY)
        else
            holder.tvName.setBackgroundColor(Color.WHITE)

        holder.tvName.setOnClickListener {
            var intent = Intent(cxt, PessoaFormActivity::class.java)
            intent.putExtra("edit", true)
            intent.putExtra("pessoaId", pessoa.id)
            cxt.startActivity(intent)
        }

        holder.btnDeletar.setOnClickListener {
            val pessoaRepository = PessoaRepository(cxt)
            pessoaRepository.delete(pessoa.id)
            callbacks(position)
        }
    }

    override fun getItemCount(): Int {
        return pessoaList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.tvAdpName
        var btnDeletar: Button = view.btnAdpDel
    }
}