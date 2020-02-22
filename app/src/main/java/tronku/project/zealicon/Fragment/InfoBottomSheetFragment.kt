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

import tronku.project.zealicon.R

/**
 * A simple [Fragment] subclass.
 */
class InfoBottomSheetFragment(val currentTrack: EventTrack) : BottomSheetDialogFragment() {

    private lateinit var sheet: BottomSheetDialog
    companion object {
        fun newInstance(track: EventTrack) = InfoBottomSheetFragment(track)
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
        v.eventType.text = currentTrack.categoryId.toString()
        v.eventDescription.text = currentTrack.description
    }
}
