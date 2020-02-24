package tronku.project.zealicon.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import tronku.project.zealicon.Model.CategoryModel
import tronku.project.zealicon.R

class CategoryAdapter( val category : List<CategoryModel>)  : RecyclerView.Adapter<CategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount() = category.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(category[position])
    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(category: CategoryModel) {
        itemView.categoryName.text = category.name
        itemView.categoryGradient.setImageResource(category.gradientDrawable)
        itemView.categoryImage.setImageResource(category.image)
        itemView.musicNotes.setColorFilter(ContextCompat.getColor(itemView.context, category.color), android.graphics.PorterDuff.Mode.SRC_IN)
    }
}