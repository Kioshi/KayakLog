package cz.martinek.stepan.kayaklog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import cz.martinek.stepan.kayaklog.model.User
import cz.martinek.stepan.kayaklog.model.UserFields
import cz.martinek.stepan.kayaklog.retrofit.API
import cz.martinek.stepan.kayaklog.retrofit.ServerInfo
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Utils.user != null) {
            onBackPressed()
            return
        }

        ServerInfo.loadAuthInfo(this)
        userNameInput.setText(getPreferences(Context.MODE_PRIVATE).getString(Utils.USERNAME, ""))

        val context = this
        ui.launch {
            loading(true)
            val successfull = bg.async {
                return@async ServerInfo.relog(context)
            }.await()
            if (successfull) {
                val pref = context.getPreferences(Context.MODE_PRIVATE)
                val username = pref.getString(Utils.USERNAME, "");

                var user = DB.realm.where<User>().equalTo(UserFields.USERNAME, username).findFirst()
                val copy = if (user != null) DB.realm.copyFromRealm(user) else null
                var user2 = bg.async {
                    if (copy != null)
                    {
                        try {
                            return@async API.updateUser(context,copy)
                        }
                        catch (ex: Exception)
                        {
                            Log.d("Retrofit:UpdateUser",ex.message)
                        }
                    }
                    else
                    {
                        try {
                            return@async API.getUser(context)
                        }
                        catch (ex: Exception)
                        {
                            Log.d("Retrofit:GetUser",ex.message)
                        }
                    }
                    return@async null
                }.await()


                if (user2 != null)
                {
                    DB.realm.beginTransaction()
                    DB.realm.where<User>().equalTo(UserFields.USERNAME, username).findAll().deleteAllFromRealm()
                    Utils.user = DB.realm.copyToRealmOrUpdate(user2)
                    DB.realm.commitTransaction()
                    finish()
                    return@launch
                }


                if (user != null)
                {
                    Utils.user = user
                    finish()
                    return@launch
                }
                loading(false)

            } else {
                loading(false)
            }
        }
    }

    fun offline(view: View)
    {
        Utils.user = DB.realm.where<User>().equalTo(UserFields.USERNAME, "OFFLINE").findFirst()
        if (Utils.user == null)
        {
            DB.realm.beginTransaction()
            Utils.user = DB.realm.createObject(User::class.java, "OFFLINE")
            DB.realm.commitTransaction()
        }

        finish()

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

        val username = userNameInput.text.toString();

        val context = this
        ui.launch {
            loading(true)
            val successfull = bg.async {
                return@async ServerInfo.login(context, username, passwordInput.text.toString())
            }.await()

            if (successfull)
            {
                Utils.user = DB.realm.where<User>().equalTo(UserFields.USERNAME, username).findFirst()
                if (Utils.user == null)
                {
                    DB.realm.beginTransaction()
                    Utils.user = DB.realm.createObject(User::class.java, username)
                    DB.realm.commitTransaction()
                }

                val offline = DB.realm.where<User>().equalTo(UserFields.USERNAME, "OFFLINE").findFirst()
                if (offline != null && Utils.user != null)
                {
                    val user = Utils.user!!
                    DB.realm.beginTransaction()
                    offline.achievements!!.forEach{off ->
                        if (user.achievements!!.none{us -> us.achievementId == off.achievementId})
                            user.achievements!!.add(off)
                    }
                    offline.trips!!.forEach{off ->
                        if (user.trips!!.none{us -> us.guid == off.guid})
                            user.trips!!.add(off)
                    }
                    offline.deleteFromRealm()
                    DB.realm.commitTransaction()
                }

                val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(Utils.USERNAME, userNameInput.text.toString())
                editor.apply()
                finish()
            }
            else
            {
                Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_LONG).show()
                loading(false)
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}


