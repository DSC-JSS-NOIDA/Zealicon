package tronku.project.zealicon.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tronku.project.zealicon.Model.CategoryModel
import tronku.project.zealicon.R

class CategoryAdapter( val category : List<CategoryModel>)  : RecyclerView.Adapter<CategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_category, parent, false)
        val viewHolder = CategoryViewHolder(view)

        return viewHolder


    }

    override fun getItemCount(): Int {

        return category.size

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.name.setText(category.get(position).name)
        holder.image.setImageResource(category.get(position).image)
        holder.gradient.setImageResource(category.get(position).gradientDrawable)

    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image = itemView.findViewById<ImageView>(R.id.categoryImage)
    val gradient = itemView.findViewById<ImageView>(R.id.categoryGradient)
    val name = itemView.findViewById<TextView>(R.id.categoryName)

}