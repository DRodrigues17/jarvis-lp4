package br.org.fundatec.jarvis.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.org.fundatec.jarvis.CriarContaActivity
import br.org.fundatec.jarvis.R
import br.org.fundatec.jarvis.client.UserClient
import br.org.fundatec.jarvis.data.UserRequest
import br.org.fundatec.jarvis.databinding.ActivityLoginBinding
import br.org.fundatec.jarvis.home.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        preferences = getSharedPreferences("bd", MODE_PRIVATE)

        passarInformacoesParaViewModelValidar()

        viewModel.viewState.observe(this) { state ->
            when (state) {
                is ViewLoginState.MostrarErroCamposNulos -> mostrarCamposNulosSnack()
                ViewLoginState.MostrarCasoDeSucesso -> casoDeSucesso()
                else -> {}
            }
        }

        binding.tvCreateAccount.setOnClickListener {
            val navegateToNewCharacterActivity = Intent(this, CriarContaActivity::class.java)
            startActivity(navegateToNewCharacterActivity)
        }
    }

    private fun passarInformacoesParaViewModelValidar() {
        binding.btLogin.setOnClickListener {
            viewModel.validarInsersoesUsuario(
                email = binding.etEmail.text.toString(),
                senha = binding.etPassword.text.toString()
            )
        }
    }

    private fun mostrarCamposNulosSnack() {
        val container = binding.container
        Snackbar
            .make(
                container,
                "Preencha todos os campos " + binding.etEmail.text + binding.etPassword.text,
                Snackbar.LENGTH_LONG
            )
            .show()
    }

    private fun casoDeSucesso() {
        val navigateToMainActivity = Intent(this, MainActivity::class.java)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fundatec.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(UserClient::class.java)

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("API Call Error", "API call failed: ${throwable.message}", throwable)
        }

        GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val userRequest: UserRequest = pegarDadosInputUser()

            val response = api.getUser(userRequest.password, userRequest.email)

            val container = binding.container

            preferences.edit().putInt("id", response.id).apply()

            println("id do usuario $response")



            Snackbar
                .make(container, "login realizado com sucesso", Snackbar.LENGTH_LONG)
                .show()

            startActivity(navigateToMainActivity)
        }
    }


    private fun pegarDadosInputUser(): UserRequest {

        val email = binding.etEmail.text.toString()
        val senha = binding.etPassword.text.toString()

        Log.e("email do login", email)
        Log.e("senha do login", senha)

        return UserRequest(" ", email, senha)

    }
}