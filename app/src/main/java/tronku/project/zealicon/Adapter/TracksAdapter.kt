package tronku.project.zealicon.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_search.view.*
import tronku.project.zealicon.Model.EventTrack
import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils

class TracksAdapter: ListAdapter<EventTrack, TracksAdapter.ViewHolder>(EventDiffCallback){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(eventTrack: EventTrack) {
            itemView.itemEventName.text = eventTrack.name
            itemView.itemEventDate.text = eventTrack.startDateTime
            val bundle = Bundle()
            bundle.putString("name", eventTrack.name)
            bundle.putString("dateTime", eventTrack.startDateTime)
            itemView.setOnClickListener {
                AnimUtils.setClickAnimation(itemView, R.id.action_playlist_fragment_to_player_activity, bundle)
            }
        }
    }

    object EventDiffCallback: DiffUtil.ItemCallback<EventTrack>() {
        override fun areItemsTheSame(oldItem: EventTrack, newItem: EventTrack): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventTrack, newItem: EventTrack): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}