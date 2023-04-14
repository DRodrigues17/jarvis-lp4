package br.org.fundatec.jarvis.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.org.fundatec.jarvis.CriarContaActivity
import br.org.fundatec.jarvis.R
import br.org.fundatec.jarvis.client.UserClient
import br.org.fundatec.jarvis.client.UserRequest
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val preferences = getSharedPreferences("bd", MODE_PRIVATE)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fundatec.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(UserClient::class.java)

        findViewById<TextView>(R.id.tv_create_account).setOnClickListener {
            val navegateToNewCharacterActivity = Intent(this, CriarContaActivity::class.java)
            startActivity(navegateToNewCharacterActivity)
        }

        findViewById<Button>(R.id.bt_login).setOnClickListener {

            GlobalScope.launch {
                val userRequest: UserRequest = pegarDadosInputUser()

                val response = api.getUser(userRequest.password, userRequest.email)

                preferences.edit().putInt("id", response.id).apply()

                println(response.toString())
            }
        }


    }

    private fun pegarDadosInputUser(): UserRequest {

        val email = findViewById<EditText>(R.id.et_email).text.toString()
        val senha = findViewById<EditText>(R.id.et_password).text.toString()

        Log.e("teste", email)
        Log.e("teste", senha)
        return UserRequest(" ", email, senha)
    }
}