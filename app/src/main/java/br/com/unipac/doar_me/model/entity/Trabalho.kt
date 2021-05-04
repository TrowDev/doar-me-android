package br.com.unipac.doar_me.model.entity

import java.util.*

data class Trabalho(
    var id: Int = 0,
    var titulo: String = "",
    var descricao: String = "",
    var nota: Double = 0.0
)
