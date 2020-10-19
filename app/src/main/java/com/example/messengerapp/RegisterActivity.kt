package com.example.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var refUsers : DatabaseReference

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

        mAuth  = FirebaseAuth.getInstance()

        register_btn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username:  String  =  username_register.text.toString()
        val email:  String  =  email_register.text.toString()
        val password:  String  =  password_register.text.toString()

        if(username  ==  ""){
            Toast.makeText(this,"Please write username.",Toast.LENGTH_LONG).show()
        }else if(email == ""){
            Toast.makeText(this,"Please write email.",Toast.LENGTH_LONG).show()
        }else if(password == ""){
            Toast.makeText(this,"Please write password.",Toast.LENGTH_LONG).show()
        }else{

        }
    }
}