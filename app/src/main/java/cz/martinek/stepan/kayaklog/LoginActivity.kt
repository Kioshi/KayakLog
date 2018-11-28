package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import cz.martinek.stepan.kayaklog.retrofit.ServerInfo
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loading(false)
    }

    fun signUp(view: View)
    {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun loading(show: Boolean) {
        if (show) {
            mainLayout.visibility = View.GONE
            loadingLayout.visibility = View.VISIBLE
        }
        else
        {
            mainLayout.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
        }
    }

    fun loginAction(view: View){
        if (userNameInput.text.isEmpty() || passwordInput.text.isEmpty())
        {
            Toast.makeText(this,"Please fill both username and password!",Toast.LENGTH_LONG).show();
            return;
        }

        val context = this
        GlobalScope.launch(Dispatchers.Main) {
            loading(true)
            val successfull = async(Dispatchers.Default) {
                return@async ServerInfo.login(context, userNameInput.text.toString(), passwordInput.text.toString())
            }.await()

            if (successfull)
            {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("userName", userNameInput.text.toString())
                startActivity(intent)
            }
            else
            {
                Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_LONG).show()
                loading(false)
            }
        }
    }
}
