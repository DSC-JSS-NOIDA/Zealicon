package tronku.project.zealicon.Fragment


import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_info_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_info_bottom_sheet.view.*
import tronku.project.zealicon.Model.EventTrack
import tronku.project.zealicon.Model.EventTrackDB

import tronku.project.zealicon.R

/**
 * A simple [Fragment] subclass.
 */
class InfoBottomSheetFragment(private val currentTrack: EventTrackDB) : BottomSheetDialogFragment() {

    private lateinit var sheet: BottomSheetDialog
    companion object {
        fun newInstance(track: EventTrackDB) = InfoBottomSheetFragment(track)
        const val TAG = "infoBottomSheet"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        sheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.fragment_info_bottom_sheet, null)
        sheet.setContentView(view)

        val behavior = BottomSheetBehavior.from(view.parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        view.rootView.minimumHeight = Resources.getSystem().displayMetrics.heightPixels

        view.closeButton.setOnClickListener {
            sheet.dismiss()
        }

        inflateUI(view)
        return sheet
    }

    private fun inflateUI(v: View) {
        v.eventName.text = currentTrack.name
        v.eventType.text = currentTrack.category
        v.eventDescription.text = currentTrack.description
        v.eventRules.text = currentTrack.rule
        if (currentTrack.rule.isNullOrBlank()){
            v.eventRules.visibility = View.GONE
            v.rulesText.visibility
        }
        v.eventprize1.text = currentTrack.firstPrize.toString()
        v.eventprize2.text = currentTrack.secondPrize.toString()
        if (currentTrack.secondPrize == 0)
            layoutPrize2.visibility = View.GONE
        v.eventContactName.text = currentTrack.contactName
        v.eventContact.text = currentTrack.contactNo

    }
}
