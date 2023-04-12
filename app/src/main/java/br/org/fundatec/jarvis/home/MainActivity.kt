package br.org.fundatec.jarvis.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.org.fundatec.jarvis.R
import br.org.fundatec.jarvis.character.CreateCharacterActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FloatingActionButton>(R.id.fb_new_character).setOnClickListener {
            val navegateToNewCharacterActivity = Intent(this, CreateCharacterActivity::class.java)
            startActivity(navegateToNewCharacterActivity)
        }
    }
}