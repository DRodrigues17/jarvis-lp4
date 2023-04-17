package br.org.fundatec.jarvis.character.domain.model

sealed class CharacterErrorModel{
    object ErrorAoBuscarLista : CharacterErrorModel()
    object ErrorAoRemover : CharacterErrorModel()
    object ErrorAoSalvar : CharacterErrorModel()
}


