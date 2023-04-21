package br.org.fundatec.jarvis.character

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.org.fundatec.jarvis.App
import br.org.fundatec.jarvis.character.data.remote.CharacterRemoteDataSource
import br.org.fundatec.jarvis.character.presentation.CreateCharacterViewModel
import br.org.fundatec.jarvis.data.Character
import br.org.fundatec.jarvis.databinding.ActivityCreateCharacterBinding
import br.org.fundatec.jarvis.home.MainActivity
import br.org.fundatec.jarvis.sealed.CreateCharacterViewState
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateCharacterActivity : AppCompatActivity() {

    private val viewModel: CreateCharacterViewModel by viewModels()
    private lateinit var binding: ActivityCreateCharacterBinding

    val editoras = arrayOf("MARVEL", "DC")
    val tipos = arrayOf("HERO", "VILLAIN")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinnerEditora: Spinner = binding.sEditora
        spinnerEditora.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, editoras)

        val spinnerTipo: Spinner = binding.sTipo
        spinnerTipo.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, tipos)

        passarInformacoesParaViewModelValidar()

        mudarImagemPersonagem()
        viewModel.viewState.observe(this) { state ->
            when (state) {
                is CreateCharacterViewState.MostrarErroCamposNulos -> mostrarCamposNulosSnack()
                is CreateCharacterViewState.MostarCasoDeSucesso -> casoDeSucesso()
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

        GlobalScope.launch {
            val userId = App.context.getSharedPreferences("bd", MODE_PRIVATE).getInt("id", 0)
            var result = CharacterRemoteDataSource().createCharacter(userId, pegarPersonagemInput())

            Log.e("corpo personagem", pegarPersonagemInput().toString())
            Log.e("corpo de response", result.toString())
            val container = binding.container
            Snackbar.make(
                container,
                "personagem cadastrado com sucesso",
                Snackbar.LENGTH_LONG).show()
        }
    }

    private fun pegarPersonagemInput(): Character {

        val name = binding.tietName.text.toString()
        val description = binding.tietDescription.text.toString()
        val imageLink = binding.tietUrl.text.toString()
        val editora = binding.sEditora.selectedItem
        val type = binding.sTipo.selectedItem
        val age = binding.tietAge.text.toString()
        val date = binding.tietBirthday.text.toString()

        return Character(
            name, description, imageLink, editora.toString(), type.toString(), age.toInt(), date
        )
    }

    private fun passarInformacoesParaViewModelValidar() {
        binding.btCreateCharacter.setOnClickListener {
            viewModel.validarInputs(
                nome = binding.tietName.text.toString(),
                descricao = binding.tietDescription.text.toString(),
                idade = binding.tietAge.text.toString(),
                link = binding.tietUrl.text.toString(),
                editora = binding.sEditora.selectedItem.toString(),
                tipo = binding.sTipo.selectedItem.toString()
            )
        }
    }

    private fun mudarImagemPersonagem() {
        binding.tietUrl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                Glide.with(binding.ivPicture.context).load(s.toString()).into(binding.ivPicture)
            }

        })
    }
}