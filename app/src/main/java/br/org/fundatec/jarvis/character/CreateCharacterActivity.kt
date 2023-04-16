package br.org.fundatec.jarvis.character

import android.R
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.org.fundatec.jarvis.character.presentation.CreateCharacterViewModel
import br.org.fundatec.jarvis.client.CharacterClient
import br.org.fundatec.jarvis.data.Character
import br.org.fundatec.jarvis.databinding.ActivityCreateCharacterBinding
import br.org.fundatec.jarvis.home.MainActivity
import br.org.fundatec.jarvis.sealed.ViewCharacterState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class CreateCharacterActivity : AppCompatActivity() {

    private val viewModel: CreateCharacterViewModel by viewModels()
    private lateinit var binding: ActivityCreateCharacterBinding
    private lateinit var preferences: SharedPreferences

    val editoras = arrayOf("MARVEL", "DC")
    val tipos = arrayOf("HERO","VILLAIN")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCharacterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        preferences = getSharedPreferences("bd", MODE_PRIVATE)

        val spinnerEditora: Spinner = binding.sEditora
        spinnerEditora.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, editoras)

        val spinnerTipo: Spinner = binding.sTipo
        spinnerTipo.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, tipos)


        passarInformacoesParaViewModelValidar()

        viewModel.viewState.observe(this) { state ->
            when (state) {
                is ViewCharacterState.MostrarErroCamposNulos -> mostrarCamposNulosSnack()
                ViewCharacterState.MostarCasoDeSucesso -> casoDeSucesso()
                else -> {}
            }
        }

        binding.btReturn.setOnClickListener {
            val navigateToMainActivity = Intent(this, MainActivity::class.java)
            startActivity(navigateToMainActivity)
        }
    }

    private fun mostrarCamposNulosSnack() {
        val container = binding.container
        Snackbar.make(
                container, "Preencha todos os campos ", Snackbar.LENGTH_LONG
            ).show()
    }

    private fun casoDeSucesso() {
        val retrofit = Retrofit.Builder().baseUrl("https://fundatec.herokuapp.com//api/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val api = retrofit.create(CharacterClient::class.java)

        val container = binding.container

        GlobalScope.launch {
            val response = api.createCharacter(preferences.getInt("id", 0), pegarPersonagemInput())
            println(response.toString())

            Snackbar.make(container, "personagem cadastrado com sucesso", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun pegarPersonagemInput(): Character {

        val name = binding.tietName.text.toString()
        val imageLink = binding.tietUrl.toString()
        val description = binding.tietDescription.text.toString()
        val editora = binding.sEditora.getSelectedItem()
        val type = binding.sTipo.getSelectedItem()
        val age = binding.tietAge.text.toString()
        var date = binding.tietBirthday.text.toString()

        val formatter = SimpleDateFormat("dd/MM/yyyy");

        val formattedDate: Date? = formatter.parse(date)

        return Character(
            name,
            imageLink,
            description,
            editora.toString(),
            type.toString(),
            age.toInt(),
            formattedDate.toString()
        )
    }

    private fun passarInformacoesParaViewModelValidar() {
        binding.btCreateCharacter.setOnClickListener {
            viewModel.validarInputs(
                nome = binding.tietName.text.toString(),
                descricao = binding.tietDescription.text.toString(),
                idade = binding.tietAge.text.toString(),
                link = binding.tietUrl.toString(),
                editora = binding.sEditora.getSelectedItem().toString(),
                tipo = binding.sTipo.getSelectedItem().toString()
            )
        }
    }
}