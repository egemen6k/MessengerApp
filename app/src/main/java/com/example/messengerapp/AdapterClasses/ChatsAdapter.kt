package com.example.messengerapp.AdapterClasses

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.ModelClasses.Chat
import com.example.messengerapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
/*            if(chat.getSender().equals(firebaseUser!!.uid)){
                holder.show_text_message!!.visibility = View.GONE
                holder.right_image_view!!.visibility = View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.right_image_view)

                //image message- left side
            }else if(!chat.getSender().equals(firebaseUser!!.uid)){
                holder.show_text_message!!.visibility = View.GONE
                holder.left_image_view!!.visibility = View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.left_image_view)
            }*/
/*          Normal kullanımda zaten viewu biz belirliyoruz. Imageye tek isim verip onu visible yaparsak
            hangi viewsa ona atayarak düzgün çalışıyor.*/
            holder.show_text_message!!.visibility = View.GONE
            holder.image_view!!.visibility = View.VISIBLE
            Picasso.get().load(chat.getUrl()).into(holder.image_view)
        }
        //text messages
        else{
            holder.show_text_message!!.text = chat.getMessage()
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
        var image_view: ImageView? = itemview.findViewById(R.id.image_view)
        var text_seen: TextView? = itemview.findViewById(R.id.text_seen)
        /*var right_image_view: ImageView? = itemview.findViewById(R.id.right_image_view)*/

    }

    override fun getItemViewType(position: Int): Int {

        return if (mChatList[position].getSender().equals(firebaseUser!!.uid)){
            1
        }
        else{
            0
        }
    }
}