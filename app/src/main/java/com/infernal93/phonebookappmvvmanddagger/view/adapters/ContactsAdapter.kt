package com.infernal93.phonebookappmvvmanddagger.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.view.activities.DetailsActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Armen Mkhitaryan on 03.01.2020.
 */
class ContactsAdapter (private val context: Context, private val mContactsList: ArrayList<ContactsRoom> = ArrayList())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setupContacts(contactsList: ArrayList<ContactsRoom>) {
        mContactsList.clear()
        mContactsList.addAll(contactsList) }

    fun sortByName() {
        mContactsList.sortBy { it.firstName }
        notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.inner_contact, parent, false)
        return ContactsViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return mContactsList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContactsViewHolder) {
            holder.bind(contactsModel = mContactsList[position])

            holder.itemView.setOnClickListener {
                val contactsModel: ContactsRoom = mContactsList[position]
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("contact", contactsModel)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                context.startActivity(intent)
            }
        }
    }

    class ContactsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var mContactIcon: CircleImageView = itemView.findViewById(R.id.contact_image)
        private var mContactFirstName: TextView = itemView.findViewById(R.id.contact_first_name)
        private var mContactPhone: TextView = itemView.findViewById(R.id.contact_phone)

        fun bind(contactsModel: ContactsRoom) {

            if (contactsModel.images.isNullOrEmpty()) {
                mContactIcon.setImageResource(R.drawable.ic_person_placeholder)
            } else {
                Picasso.with(itemView.context).load(contactsModel.images)
                    .placeholder(R.drawable.ic_person_placeholder)
                    .into(mContactIcon)
            }
//            contactsModel.images?.let { url ->
//                Picasso.with(itemView.context).load(url)
//                    .placeholder(R.drawable.ic_person_placeholder)
//                    .error(R.drawable.ic_person_placeholder)
//                    .into(mContactIcon)
//                    }

            mContactFirstName.text =  contactsModel.firstName
            mContactPhone.text = contactsModel.phone
        }
    }
}