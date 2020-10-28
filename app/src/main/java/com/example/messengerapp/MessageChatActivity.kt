package com.example.messengerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_message_chat.*

class MessageChatActivity : AppCompatActivity() {

    var userIdVisit: String? = ""
    var firebaseUser: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat)

        intent = intent
        userIdVisit= intent.getStringExtra("visit_id")
        firebaseUser = FirebaseAuth.getInstance().currentUser

        send_message_btn.setOnClickListener {
            val message = text_message.text.toString()
            if(message == ""){
                Toast.makeText(this,"Please write a message first...", Toast.LENGTH_LONG).show()
            }else{
                sendMessageToUser(firebaseUser!!.uid, userIdVisit, message)
            }
        }
    }

    private fun sendMessageToUser(uid: String, userIdVisit: String?, message: String) {

    }
}