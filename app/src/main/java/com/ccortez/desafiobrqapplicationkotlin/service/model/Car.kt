package com.ccortez.desafiobrqapplicationkotlin.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car (

    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "nome")
    var nome: String? = null,

    @ColumnInfo(name = "marca")
    var marca: String? = null,

    @ColumnInfo(name = "descricao")
    var descricao: String? = null,

    @ColumnInfo(name = "quantidade")
    var quantidade: Int = 0,

    @ColumnInfo(name = "preco")
    var preco: Int = 0,

    @JvmField
    @ColumnInfo(name = "imagem")
    var imagem: String? = null

) {
    override fun toString(): String {
        return "Car(id=$id, nome=$nome, marca=$marca, descricao=$descricao, quantidade=$quantidade, preco=$preco, imagem=$imagem)"
    }
}