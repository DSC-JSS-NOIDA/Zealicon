package tronku.project.zealicon.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import tronku.project.zealicon.Model.ContactMemberModel
import tronku.project.zealicon.R


class RouteAdapter( val member : List<ContactMemberModel>)  : RecyclerView.Adapter<RouteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_route_member, parent, false)
        val viewHolder = RouteViewHolder(view)

        return viewHolder


    }

    override fun getItemCount(): Int {

        return member.size

    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {

        holder.name.setText(member.get(position).name)
        holder.image.setImageResource(member.get(position).image)
        holder.designation.setText(member.get(position).designation)
        holder.contact.setText(member.get(position).contact)

        holder.container.setOnClickListener {
            when (member.get(holder.adapterPosition).type){
                "email" -> {
                    val intent: Intent = Intent(Intent.ACTION_SEND)
                    intent.type = "plain/text"
                    intent.putExtra(
                        Intent.EXTRA_EMAIL,
                        arrayOf(member.get(holder.adapterPosition).contact)
                    )
                    startActivity(holder.container.context, intent, null)
                }
                "mobile" -> {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${member.get(holder.adapterPosition).contact}")
                    startActivity(holder.container.context, intent, null)
                }
            }
        }

    }
}

class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image = itemView.findViewById<ImageView>(R.id.memberImage)
    val name = itemView.findViewById<TextView>(R.id.memberName)
    val designation = itemView.findViewById<TextView>(R.id.memberDesignation)
    val contact = itemView.findViewById<TextView>(R.id.memberContact)
    val container = itemView.findViewById<LinearLayout>(R.id.itemLayout)
}