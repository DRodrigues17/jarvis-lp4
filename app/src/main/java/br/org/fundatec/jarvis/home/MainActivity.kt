package br.org.fundatec.jarvis.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.org.fundatec.jarvis.R
import br.org.fundatec.jarvis.character.CreateCharacterActivity
import br.org.fundatec.jarvis.client.UserClient
import br.org.fundatec.jarvis.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fundatec.herokuapp.com//api/login")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(UserClient::class.java)

        GlobalScope.launch {
            //val response = api.getUser()
            //println(response.toString())
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        findViewById<FloatingActionButton>(R.id.fb_new_character).setOnClickListener {
            val navegateToNewCharacterActivity = Intent(this, CreateCharacterActivity::class.java)
            startActivity(navegateToNewCharacterActivity)
        }
    }
}