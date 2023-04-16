package br.org.fundatec.jarvis.data

data class Character(
    val name: String,
    val description: String,
    val imageLink: String,
    val universeType: String,
    val characterType: String,
    val age: Int,
    val date: String?
)
