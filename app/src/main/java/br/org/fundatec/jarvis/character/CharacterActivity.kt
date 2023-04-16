package br.org.fundatec.jarvis.character

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.org.fundatec.jarvis.R
import br.org.fundatec.jarvis.databinding.ActivityPersonagemBinding
import br.org.fundatec.jarvis.home.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonagemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personagem)

        binding = ActivityPersonagemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.btReturnHome.setOnClickListener {
            val navegateToNewCharacterActivity = Intent(this, MainActivity::class.java)
            startActivity(navegateToNewCharacterActivity)
        }
    }
}