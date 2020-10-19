package com.example.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var refUsers : DatabaseReference
    private var firebaseUserID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar: Toolbar = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar!!.title="Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this,WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        /*user anlık durum bilgisine erişim sağlanması*/
        mAuth  = FirebaseAuth.getInstance()

        register_btn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        /*element içeriklerine erişim sağlanması*/
        val username:  String  =  username_register.text.toString()
        val email:  String  =  email_register.text.toString()
        val password:  String  =  password_register.text.toString()

        /*kullanıcı edittext içeriği girdi mi koşul kontrolü*/
        if(username  ==  ""){
            Toast.makeText(this,"Please write username.",Toast.LENGTH_LONG).show()
        }else if(email == ""){
            Toast.makeText(this,"Please write email.",Toast.LENGTH_LONG).show()
        }else if(password == ""){
            Toast.makeText(this,"Please write password.",Toast.LENGTH_LONG).show()
        }
        /*her şey tamamsa  "save the information of the user inside  the service
        firebase real time database"*/
        else{
            /*kullanıcı kayıt işlemi*/
            mAuth.createUserWithEmailAndPassword(email,password)
                    /*işlem sonrasında*/
                .addOnCompleteListener{task ->
                    /*kayıt işlemi başarılıysa realtimedatabase'e kayıt yapılır*/
                    if (task.isSuccessful){
                        /*firebaseden ID çekilmesi ve activityde erişim sağlanması*/
                        firebaseUserID  = mAuth.currentUser!!.uid
                        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["username"] = username
                        /*aşağıda  verilen linkler  place holder, kullanıcı bu görseller yerine
                        istediğini ekleyebilecek*/
                        userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/messengerapp-1593f.appspot.com/o/profile.png?alt=media&token=e3db7571-4218-46c4-b9e8-479ced85ce7f"
                        userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/messengerapp-1593f.appspot.com/o/cover.jpg?alt=media&token=c19ce505-79c1-4454-8fac-25524a847901"
                        userHashMap["status"] = "offline"
                        userHashMap["search"] = username.toLowerCase()
                        userHashMap["facebook"] = "https://m.facebook.com"
                        userHashMap["instagram"] = "https://m.instagram.com"
                        userHashMap["website"] = "https://www.google.com"

                        refUsers.updateChildren(userHashMap).addOnCompleteListener{ task ->
                            if (task.isSuccessful){
                                val intent = Intent(this,MainActivity::class.java)
                                /*user back butonuna bastığında login veya  registeractivity'e
                                Logout'a  basmadığı sürece gitmesin*/
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                   /* kayıt işlemi işlem başarılı değilse Error Message: sistem çıktısı*/
                    else{
                        Toast.makeText(this,"Error Message:" + task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}