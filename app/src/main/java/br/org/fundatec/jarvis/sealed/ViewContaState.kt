package br.org.fundatec.jarvis.sealed

sealed class ViewContaState {
    object MostrarErroCamposNulos : ViewContaState()
    object MostrarCasoDeSucesso : ViewContaState()
}