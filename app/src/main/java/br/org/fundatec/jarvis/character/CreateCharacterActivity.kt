package br.org.fundatec.jarvis.character

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.org.fundatec.jarvis.R
import br.org.fundatec.jarvis.character.presentation.CreateCharacterViewModel
import br.org.fundatec.jarvis.client.Character
import br.org.fundatec.jarvis.client.CharacterClient
import br.org.fundatec.jarvis.databinding.ActivityCreateCharacterBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class CreateCharacterActivity : AppCompatActivity() {

    private val viewModel: CreateCharacterViewModel by viewModels()
    private lateinit var binding: ActivityCreateCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCharacterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val preferences = getSharedPreferences("bd", MODE_PRIVATE)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fundatec.herokuapp.com//api/login")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CharacterClient::class.java)


        GlobalScope.launch {
            val response = api.createCharacter(preferences.getInt("id", 0), pegarPersonagemInput())
            println(response.toString())
        }
    }

    private fun pegarPersonagemInput(): Character {

        val name = findViewById<TextInputEditText>(R.id.tiet_name).toString()
        val imageLink = findViewById<TextInputEditText>(R.id.tiet_url).toString()
        val description = findViewById<TextInputEditText>(R.id.tiet_description).toString()
        val editora = findViewById<TextInputEditText>(R.id.s_editora).toString()
        val type = findViewById<TextInputEditText>(R.id.s_tipo).toString()
        val age = findViewById<TextInputEditText>(R.id.tiet_age).toString()
        var date = findViewById<TextInputEditText>(R.id.tiet_birthday)

        val formatter = SimpleDateFormat("dd/MM/yyyy");

        val formattedDate: Date? = formatter.parse(date.getText().toString())

        return Character(name, imageLink, description, editora, type, age.toInt(), formattedDate)
    }
}