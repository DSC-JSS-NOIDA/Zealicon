package tronku.project.zealicon.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tronku.project.zealicon.Model.CategoryModel
import tronku.project.zealicon.Model.TeamModel
import tronku.project.zealicon.R

class TeamAdapter( val member : List<TeamModel>)  : RecyclerView.Adapter<TeamViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_team_member, parent, false)
        val viewHolder = TeamViewHolder(view)

        return viewHolder


    }

    override fun getItemCount(): Int {

        return member.size

    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {

        holder.name.setText(member.get(position).name)
        holder.image.setImageResource(member.get(position).image)
        holder.designation.setText(member.get(position).designation)

    }
}

class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image = itemView.findViewById<ImageView>(R.id.memberImage)
    val name = itemView.findViewById<TextView>(R.id.memberName)
    val designation = itemView.findViewById<TextView>(R.id.memberDesignation)

}