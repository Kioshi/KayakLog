package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val submit = findViewById<Button>(R.id.submitButton)
        submit.setOnClickListener(){
            signUpAction()

        }
    }

    private fun signUpAction(){
        val userName = findViewById<TextView>(R.id.userNameInput)
        val password = findViewById<TextView>(R.id.passwordInput)
        val email = findViewById<TextView>(R.id.emailInput)
        //val userData = userName.text.toString()


        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("userName", userName.text.toString())
            putExtra("userPassword", password.text.toString())
            putExtra("userEmail", email.text.toString())
        }
        startActivity(intent)
    }
}
