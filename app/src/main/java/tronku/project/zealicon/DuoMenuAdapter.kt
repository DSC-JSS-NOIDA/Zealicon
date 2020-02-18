package tronku.project.zealicon

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import nl.psdcompany.duonavigationdrawer.views.DuoOptionView
import java.util.*

internal class DuoMenuAdapter(options: ArrayList<String>) :
    BaseAdapter() {
    private var mOptions = ArrayList<String>()
    private val mOptionViews =
        ArrayList<DuoOptionView>()

    override fun getCount(): Int {
        return mOptions.size
    }

    override fun getItem(position: Int): Any {
        return mOptions[position]
    }

    fun setViewSelected(
        position: Int,
        selected: Boolean
    ) {
        for (i in mOptionViews.indices) {
            if (i == position) {
                mOptionViews[i].isSelected = selected
            } else {
                mOptionViews[i].isSelected = !selected
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val option = mOptions[position]
        // Using the DuoOptionView to easily recreate the demo
        val optionView: DuoOptionView
        optionView = if (convertView == null) {
            DuoOptionView(parent.context)
        } else {
            convertView as DuoOptionView
        }
        optionView.bind(option, null, null)
        mOptionViews.add(optionView)
        return optionView
    }

    init {
        mOptions = options
    }
}