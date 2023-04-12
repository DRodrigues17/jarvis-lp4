package br.org.fundatec.jarvis.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.org.fundatec.jarvis.CriarContaActivity
import br.org.fundatec.jarvis.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<TextView>(R.id.tv_create_account).setOnClickListener {
            val navegateToNewCharacterActivity = Intent(this, CriarContaActivity::class.java)
            startActivity(navegateToNewCharacterActivity)
        }
    }
}