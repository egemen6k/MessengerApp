package com.example.messengerapp.AdapterClasses

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.ModelClasses.Chat
import com.example.messengerapp.R
import com.example.messengerapp.ViewFullImageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.message_item_left.view.*

class ChatsAdapter(
    val mContext: Context,
    val mChatList: List<Chat>,
    val imageUrl: String
): RecyclerView.Adapter<ChatsAdapter.ViewHolder>()
{
    var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat: Chat = mChatList[position]

        Picasso.get().load(imageUrl).into(holder.profile_image)

        //images-messages
        if(chat.getMessage().equals("sent you an image.") && !chat.getUrl().equals("")){
            //image message - right side
            if(chat.getSender().equals(firebaseUser!!.uid)){
                holder.show_text_message!!.visibility = View.GONE
                holder.right_image_view!!.visibility = View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.right_image_view)

                holder.right_image_view!!.setOnClickListener {
                    val options = arrayOf<CharSequence>(
                        "View Full Image",
                        "Delete Image",
                        "Cancel"
                    )

                    var builder: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("What do you want?")

                    builder.setItems(options, DialogInterface.OnClickListener{
                            dialog, which ->
                        if(which == 0){
                            val intent = Intent(mContext,ViewFullImageActivity::class.java)
                            intent.putExtra("url",chat.getUrl())
                            mContext.startActivity(intent)
                        }else if(which == 1){
                            deleteSentMessage(position,holder)
                        }
                    })
                    builder.show()
                }

                //image message- left side
            }else if(!chat.getSender().equals(firebaseUser!!.uid)){
                holder.show_text_message!!.visibility = View.GONE
                holder.left_image_view!!.visibility = View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.left_image_view)

                holder.left_image_view!!.setOnClickListener {
                    val options = arrayOf<CharSequence>(
                        "View Full Image",
                        "Cancel"
                    )

                    var builder: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("What do you want?")

                    builder.setItems(options, DialogInterface.OnClickListener{
                            dialog, which ->
                        if(which == 0){
                            val intent = Intent(mContext,ViewFullImageActivity::class.java)
                            intent.putExtra("url",chat.getUrl())
                            mContext.startActivity(intent)
                        }
                    })
                    builder.show()
                }
            }
        }
        //text messages
        else{
            holder.show_text_message!!.text = chat.getMessage()

            holder.show_text_message!!.setOnClickListener {
                val options = arrayOf<CharSequence>(
                    "Delete Message",
                    "Cancel"
                )

                var builder: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(holder.itemView.context)
                builder.setTitle("What do you want?")

                builder.setItems(options, DialogInterface.OnClickListener{
                        dialog, which ->
                        if(which == 0){
                        deleteSentMessage(position,holder)
                    }
                })
                builder.show()
            }
        }

        //sent and seen message
        //size sayıyı veriyor, position indexi. ondan dolayı -1
        if(position == mChatList.size-1){

            if(chat.getisSeen()){
                holder.text_seen!!.text = "Seen"

                if(chat.getMessage().equals("sent you an image.") && !chat.getUrl().equals("")){
                    val lp: RelativeLayout.LayoutParams? = holder.text_seen!!.layoutParams as RelativeLayout.LayoutParams?
                    lp!!.setMargins(0,245,10,0)
                    holder.text_seen!!.layoutParams = lp
                }
            }else{
                holder.text_seen!!.text = "Sent"

                if(chat.getMessage().equals("sent you an image.") && !chat.getUrl().equals("")){
                    val lp: RelativeLayout.LayoutParams? = holder.text_seen!!.layoutParams as RelativeLayout.LayoutParams?
                    lp!!.setMargins(0,245,10,0)
                    holder.text_seen!!.layoutParams = lp
                }
            }

        }else{
            holder.text_seen!!.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return mChatList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder
    {
        return if(position == 1) {
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.message_item_right,parent,false)
            ViewHolder(view)
        }
        else {
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.message_item_left,parent,false)
            ViewHolder(view)
        }
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        var profile_image: CircleImageView? = itemview.findViewById(R.id.profile_image)
        var show_text_message: TextView? = itemview.findViewById(R.id.show_text_message)
        var left_image_view: ImageView? = itemview.findViewById(R.id.left_image_view)
        var text_seen: TextView? = itemview.findViewById(R.id.text_seen)
        var right_image_view: ImageView? = itemview.findViewById(R.id.right_image_view)

    }

    override fun getItemViewType(position: Int): Int {

        return if (mChatList[position].getSender().equals(firebaseUser!!.uid)){
            1
        }
        else{
            0
        }
    }

    private fun deleteSentMessage(position: Int, holder: ChatsAdapter.ViewHolder)
    {
        val ref = FirebaseDatabase.getInstance().reference.child("Chats")
            .child(mChatList[position].getMessageId()!!)
            .removeValue()
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    Toast.makeText(holder.itemView.context,
                    "Deleted successfully.",
                    Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(holder.itemView.context,
                        "Failed, not deleted.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}