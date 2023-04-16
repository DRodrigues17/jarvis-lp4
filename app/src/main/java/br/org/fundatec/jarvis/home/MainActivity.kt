package br.org.fundatec.jarvis.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.org.fundatec.jarvis.R
import br.org.fundatec.jarvis.character.CreateCharacterActivity
import br.org.fundatec.jarvis.client.CharacterClient
import br.org.fundatec.jarvis.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private  lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = getSharedPreferences("bd", MODE_PRIVATE)
        Log.e("user id", preferences.getInt("id", 0).toString())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fundatec.herokuapp.com//api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CharacterClient::class.java)

        GlobalScope.launch {
            val response = api.getCharacters(preferences.getInt("id", 0))
            println(response.toString())
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fbNewCharacter.setOnClickListener {
            val navegateToNewCharacterActivity = Intent(this, CreateCharacterActivity::class.java)
            startActivity(navegateToNewCharacterActivity)
        }
    }
}