package br.org.fundatec.jarvis.character

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.org.fundatec.jarvis.home.MainActivity
import br.org.fundatec.jarvis.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateCharacterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_character)

        findViewById<FloatingActionButton>(R.id.bt_return).setOnClickListener {
            val navegate = Intent(this, MainActivity::class.java)
            startActivity(navegate)
        }
    }
}