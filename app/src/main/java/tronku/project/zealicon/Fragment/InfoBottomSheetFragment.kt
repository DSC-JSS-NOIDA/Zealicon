package tronku.project.zealicon.Fragment


import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_info_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_info_bottom_sheet.view.*
import kotlinx.android.synthetic.main.search_dialog_layout.*
import kotlinx.android.synthetic.main.search_dialog_layout.regLoader
import kotlinx.android.synthetic.main.search_dialog_layout.regText
import kotlinx.android.synthetic.main.subscription_fragment.*
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.EventTrack
import tronku.project.zealicon.Model.EventTrackDB
import tronku.project.zealicon.Model.Status
import tronku.project.zealicon.Model.User

import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils
import tronku.project.zealicon.Utils.ExtraUtils
import tronku.project.zealicon.Viewmodel.InfoViewModel

/**
 * A simple [Fragment] subclass.
 */
class InfoBottomSheetFragment(private val currentTrack: EventTrackDB) : BottomSheetDialogFragment() {

    private lateinit var sheet: BottomSheetDialog
    private val viewModel by lazy { InfoViewModel() }
    private val db by lazy { RoomDB(context!!) }

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
        v.eventprize1.text = "₹${currentTrack.firstPrize.toString()}"
        v.eventprize2.text = "₹${currentTrack.secondPrize.toString()}"
        if (currentTrack.secondPrize == 0)
            layoutPrize2.visibility = View.GONE

        v.eventContactName.text = currentTrack.contactName
        v.eventContact.text = currentTrack.contactNo

        viewModel.checkIfReg(currentTrack.id, db)
        viewModel.isRegistered.observe(this, Observer {
            v.registeredButton.visibility = if (it) View.VISIBLE else View.INVISIBLE
            v.registerButton.visibility = if (it) View.INVISIBLE else View.VISIBLE
        })

        v.registerButton.setOnClickListener {
            AnimUtils.setClickAnimation(it)
            regEvent(v)
        }
    }

    private fun regEvent(v: View) {
        if (ExtraUtils.getUser(context!!) != null) {
            ExtraUtils.getUser(context!!)?.let {
                if (it.zealID == null) {
                    Toast.makeText(context, "Please submit the registration amount first!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.registerForEvent(it, currentTrack.id).observe(this, Observer { res ->
                        when (res.status) {
                            Status.LOADING -> {
                                v.registerButton.visibility = View.INVISIBLE
                                v.loader.visibility = View.VISIBLE
                            }
                            Status.ERROR -> {
                                Log.e("REG_ERROR", res.msg.toString())
                                v.registeredButton.visibility = View.VISIBLE
                                v.loader.visibility = View.INVISIBLE
                                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                            }
                            Status.SUCCESS -> {
                                Toast.makeText(
                                    context,
                                    "You have been registered!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                viewModel.addAsRegistered(currentTrack.id, db)
                                v.loader.visibility = View.INVISIBLE
                                v.registeredButton.visibility = View.VISIBLE
                            }
                        }
                    })
                }
            }
        } else {
            showSearchDialog()
        }
    }

    private fun showSearchDialog() {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.search_dialog_layout)
        val window = dialog.window
        window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        dialog.searchBtn.setOnClickListener {
            val queryStr = dialog.searchEditText.text.toString()
            if (queryStr.isEmpty()) {
                dialog.searchEditText.error = "Enter a query"
            } else {
                AnimUtils.setClickAnimation(it)
                searchUser(queryStr, dialog)
            }
        }
        dialog.show()
    }

    private fun searchUser(query: String, dialog: Dialog) {
        viewModel.searchUser(query).observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    dialog.regLoader.visibility = View.VISIBLE
                    dialog.regText.visibility = View.GONE
                    dialog.searchEditText.isEnabled = false
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                    dialog.regLoader.visibility = View.GONE
                    dialog.regText.visibility = View.VISIBLE
                    dialog.searchEditText.isEnabled = true
                    dialog.searchEditText.text.clear()
                }
                Status.SUCCESS -> {
                    Log.e("SEARCH_SUCCESS", it.data.toString())
                    dialog.regLoader.visibility = View.GONE
                    dialog.regText.visibility = View.VISIBLE
                    dialog.searchEditText.isEnabled = true
                    dialog.searchEditText.text.clear()
                    dialog.dismiss()
                }
            }
        })
    }
}
