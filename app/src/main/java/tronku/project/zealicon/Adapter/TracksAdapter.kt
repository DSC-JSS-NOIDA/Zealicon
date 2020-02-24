package tronku.project.zealicon.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_search.view.*
import tronku.project.zealicon.Model.EventTrackDB
import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils
import java.util.ArrayList

enum class PlayerTarget {
    HOME,
    PLAYLIST,
    MY_PLAYLIST
}

class TracksAdapter(private val target: PlayerTarget): ListAdapter<EventTrackDB, TracksAdapter.ViewHolder>(EventDiffCallback){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, tracks: ArrayList<EventTrackDB>, target: PlayerTarget) {
            itemView.itemEventName.text = tracks[position].name?.capitalize()
            itemView.itemEventDate.text = "${tracks[position].day + 23}th March  •  ${tracks[position].category}"
            itemView.noteImg.setImageResource(getNote(position))

            when(tracks[position].day) {
                1 -> itemView.itemEventImageView.setImageResource(R.drawable.curved_card_blue)
                2 -> itemView.itemEventImageView.setImageResource(R.drawable.curved_card_purple)
                3 -> itemView.itemEventImageView.setImageResource(R.drawable.curved_card_green)
                4 -> itemView.itemEventImageView.setImageResource(R.drawable.curved_card_yellow)
            }

            val bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putParcelableArrayList("tracks", tracks)
            val navId = getNavId(target)
            itemView.setOnClickListener {
                AnimUtils.setClickAnimation(itemView, navId, bundle)
            }
        }

        private fun getNote(pos: Int) : Int {
            return when(pos%5) {
                0 -> R.drawable.ic_note_one
                1 -> R.drawable.ic_note_two
                2 -> R.drawable.ic_note_three
                3 -> R.drawable.ic_note_four
                else -> R.drawable.ic_note_five
            }
        }

        private fun getNavId(target: PlayerTarget): Int {
            return when(target) {
                PlayerTarget.HOME -> R.id.action_home_to_playerActivity
                PlayerTarget.PLAYLIST -> R.id.action_playlist_fragment_to_player_activity
                PlayerTarget.MY_PLAYLIST -> R.id.action_my_playlist_to_playerActivity
            }
        }
    }

    object EventDiffCallback: DiffUtil.ItemCallback<EventTrackDB>() {
        override fun areItemsTheSame(oldItem: EventTrackDB, newItem: EventTrackDB): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventTrackDB, newItem: EventTrackDB): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, ArrayList(currentList), target)
    }

}