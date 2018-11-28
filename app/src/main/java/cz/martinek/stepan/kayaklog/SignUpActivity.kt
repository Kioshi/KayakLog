package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import cz.martinek.stepan.kayaklog.retrofit.NewUser
import cz.martinek.stepan.kayaklog.retrofit.Registered
import cz.martinek.stepan.kayaklog.retrofit.ServerInfo
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        loading(false)
        submitButton.setOnClickListener(){
            signUpAction()
        }
    }
    private fun loading(show: Boolean) {
        if (show) {
            signupLayout.visibility = GONE
            loadingLayout.visibility = VISIBLE
        }
        else
        {
            signupLayout.visibility = VISIBLE
            loadingLayout.visibility = GONE
        }
    }

    private fun signUpAction(){

        if (userNameInput.text.isEmpty() || passwordInput.text.isEmpty())
        {
            Toast.makeText(this,"Please fill both username and password!",Toast.LENGTH_LONG).show();
            return;
        }

        val context = this
        loading(true);
        GlobalScope.launch(Dispatchers.Main) {

            val job = async<Response<Registered>?>(Dispatchers.Default) {
                var response: Response<Registered>? = null
                try {
                    response = ServerInfo.retrofitAPI.register(NewUser(userNameInput.text.toString(), passwordInput.text.toString())).execute()

                }catch (ex: Exception)
                {
                    Log.d("Retrofit:Register", ex.message)
                }
                return@async response
            }

            val response = job.await()

            if (response == null)
            {
                Toast.makeText(context,"Could not connect to server!",Toast.LENGTH_LONG).show()
                loading(false);
                return@launch
            }

            if (!response.isSuccessful) {
                userNameInput.text.clear()
                loading(false);
                Toast.makeText(context, "User already exists. Choose different username please!", Toast.LENGTH_LONG).show();
                return@launch;
            }

            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("userName", userNameInput.text.toString())
            }
            startActivity(intent)
        }



    }
}
