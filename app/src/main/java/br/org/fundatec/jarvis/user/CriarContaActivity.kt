package br.org.fundatec.jarvis

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.org.fundatec.jarvis.client.UserClient
import br.org.fundatec.jarvis.client.UserRequest
import br.org.fundatec.jarvis.databinding.ActivityCriarContaBinding
import br.org.fundatec.jarvis.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CriarContaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCriarContaBinding

    private val viewModel: CriarContaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        binding = ActivityCriarContaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        passarInformacoesParaViewModelValidar()

        viewModel.viewState.observe(this) { state ->
            when (state) {
                is ViewContaState.MostrarErroCamposNulos -> mostrarCamposNulosSnack()
                ViewContaState.MostrarCasoDeSucesso -> casoDeSucesso()
                else -> {}
            }
        }
        binding.btReturn.setOnClickListener {
            val navegateToLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(navegateToLoginActivity)
        }
    }

    private fun passarInformacoesParaViewModelValidar() {
        binding.btCreateAccount.setOnClickListener {
            viewModel.validarInsersoesUsuario(
                nome = binding.etNome.text.toString(),
                email = binding.etEmail.text.toString(),
                senha = binding.etSenha.text.toString()
            )
        }
    }

    private fun mostrarCamposNulosSnack() {
        val container = binding.container
        Snackbar
            .make(
                container,
                "Preencha todos os campos " + binding.etNome.text + binding.etEmail.text + binding.etSenha.text,
                Snackbar.LENGTH_LONG
            )
            .show()
    }

    private fun casoDeSucesso() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fundatec.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(UserClient::class.java)

        GlobalScope.launch {
            val response = api.createUser(criarUser())
            println(response.toString())
        }

        val container = binding.container
        Snackbar
            .make(
                container,
                "Conta criada com sucesso, espere um tempo e poderá fazer login",
                Snackbar.LENGTH_LONG
            )
            .show()
    }

    private fun criarUser(): UserRequest {
        val nome = binding.etNome.text.toString()
        val email = binding.etEmail.text.toString()
        val senha = binding.etSenha.text.toString()

        return UserRequest(nome, email, senha)
    }
}