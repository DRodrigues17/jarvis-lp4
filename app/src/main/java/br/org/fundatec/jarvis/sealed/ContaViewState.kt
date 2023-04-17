package br.org.fundatec.jarvis.sealed

sealed class ContaViewState {
    object MostrarErroCamposNulos : ContaViewState()
    object MostrarCasoDeSucesso : ContaViewState()
}