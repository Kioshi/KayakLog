package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.LoginButton)
        loginButton.setOnClickListener{
            loginAction()
        }

        val signupButton = findViewById<Button>(R.id.SignUpButton)
        signupButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginAction(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
