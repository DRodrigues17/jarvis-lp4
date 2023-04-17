package br.org.fundatec.jarvis.sealed

sealed class CreateCharacterViewState {
    object MostrarErroCamposNulos : CreateCharacterViewState()
    object MostarCasoDeSucesso : CreateCharacterViewState()
}
