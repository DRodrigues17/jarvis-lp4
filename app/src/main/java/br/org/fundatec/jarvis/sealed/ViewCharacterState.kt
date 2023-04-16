package br.org.fundatec.jarvis.sealed

sealed class ViewCharacterState {
    object MostrarErroCamposNulos : ViewCharacterState()
    object MostarCasoDeSucesso : ViewCharacterState()
}
