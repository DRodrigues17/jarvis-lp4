package br.org.fundatec.jarvis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import br.org.fundatec.jarvis.databinding.ActivityCriarContaBinding
import br.org.fundatec.jarvis.login.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CriarContaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCriarContaBinding

    private val viewModel: CriarContaViewModel by viewModels()



    private val moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        configurarBotaoCriarConta()

        viewModel.viewState.observe(this) { state ->
            when (state) {
                is ViewContaState -> mostrarCamposNulosSnack()
                ViewContaState.MostrarErroEmailInvalido -> mostrarEmailInvalidoSnack()
                ViewContaState.MostrarCasoDeSucesso -> casoDeSucesso()
                else -> {}
            }
        }

        findViewById<FloatingActionButton>(R.id.bt_return).setOnClickListener {
            val navegateToNewCharacterActivity = Intent(this, LoginActivity::class.java)
            startActivity(navegateToNewCharacterActivity)
        }
    }

    private fun configurarBotaoCriarConta() {
        binding.btCreateAccount.setOnClickListener{
            viewModel.validarInsersoesUsuario(
                nome = binding.etNome.text.toString(),
                email = binding.etEmail.text.toString(),
                senha = binding.etSenha?.text.toString(),
            )
        }
    }


    private fun mostrarEmailInvalidoSnack(){
        val container = findViewById<ConstraintLayout>(R.id.container)
        Snackbar
            .make(container, "Email invalido", Snackbar.LENGTH_LONG)
            .show()
    }

    private fun mostrarCamposNulosSnack() {
        val container = findViewById<ConstraintLayout>(R.id.container)
        Snackbar
            .make(container, "Preencha todos os campos", Snackbar.LENGTH_LONG)
            .show()
    }

    private fun casoDeSucesso() {

        val preferences = getPreferences(MODE_PRIVATE)

        val usuarioIdString = moshi
            .adapter(String::class.java)
            .toJson(criarUser().id.toString())

        preferences.edit().putString("User", usuarioIdString).apply()


        val container = findViewById<ConstraintLayout>(R.id.container)
        Snackbar
            .make(
                container,
                "Conta criada com sucesso, espere um tempo e poderá fazer login",
                Snackbar.LENGTH_LONG
            )
            .show()
    }

    private fun criarUser(): User {
        val nome = findViewById<TextInputEditText>(R.id.et_nome).toString()
        val email = findViewById<TextInputEditText>(R.id.et_email).toString()
        val senha = findViewById<TextInputEditText>(R.id.et_senha).toString()

        return User(null , nome, email, senha)
    }
}

  data class User(
      val id: Number?,
      val name: String,
      val email: String,
      val senha: String
  )