package tronku.project.zealicon.Fragment

import android.app.Activity
import android.app.Dialog
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.search_dialog_layout.*
import kotlinx.android.synthetic.main.subscription_fragment.*
import kotlinx.android.synthetic.main.subscription_fragment.regLoader
import kotlinx.android.synthetic.main.subscription_fragment.regText
import tronku.project.zealicon.BuildConfig
import tronku.project.zealicon.Model.Status
import tronku.project.zealicon.Model.User
import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils
import tronku.project.zealicon.Utils.ExtraUtils
import tronku.project.zealicon.Viewmodel.SubscriptionViewModel


class SubscriptionFragment : Fragment() {

    companion object {
        fun newInstance() = SubscriptionFragment()
    }

    private val viewModel by lazy { SubscriptionViewModel() }
    private var isVerified = false
    private var token = ""
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subscription_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUI()
        setListeners()
    }

    private fun setUI() {
        areYouHumanBg.background.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN)
        verifiedBg.background.setColorFilter(resources.getColor(R.color.green_700), PorterDuff.Mode.SRC_IN)

        if (ExtraUtils.getUser(context!!) != null) {
            afterRegLayout.visibility = View.VISIBLE
            registrationLayout.visibility = View.GONE
            showUserDetails()
        }
    }

    private fun setListeners() {
        areYouHumanBtn.setOnClickListener {
            AnimUtils.setClickAnimation(it)
            changeVerifyBtn(ButtonState.LOADING)
            verifyUser()
        }

        registerBtn.setOnClickListener {
            AnimUtils.setClickAnimation(it)
            ExtraUtils.hideKeyboard(context as Activity)
            validateData()
        }

        regNewUserBtn.setOnClickListener {
            AnimUtils.setClickAnimation(it)
            showTransition(afterRegLayout, registrationLayout)
        }

        alreadyRegUser.setOnClickListener {
            ExtraUtils.hideKeyboard(context as Activity)
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
        viewModel.searchUser(query).observe(viewLifecycleOwner, Observer {
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
                    parse(it.data, true)
                    dialog.dismiss()
                }
            }
        })
    }

    private fun validateData() {
        if (nameEditText.text.isNullOrEmpty()) {
            nameEditText.requestFocus()
            nameEditText.error = "Enter name"
        } else if (emailEditText.text.isNullOrEmpty()) {
            emailEditText.requestFocus()
            emailEditText.error = "Enter email-id"
        } else if (!isValidEmail(emailEditText.text)) {
            emailEditText.requestFocus()
            emailEditText.error = "Enter valid email-id"
        } else if (phoneEditText.text.isNullOrEmpty()) {
            phoneEditText.requestFocus()
            phoneEditText.error = "Enter phone number"
        } else if (phoneEditText.text.length != 10) {
            phoneEditText.requestFocus()
            phoneEditText.error = "Enter 10-digits long number"
        } else if (admnoEditText.text.isNullOrEmpty()) {
            admnoEditText.requestFocus()
            admnoEditText.error = "Enter admission number"
        } else if (!isVerified) {
            areYouHumanBtn.requestFocus()
            Toast.makeText(context, "Please verify yourself!", Toast.LENGTH_SHORT).show()
        } else {
            registerUser(nameEditText.text.toString(), emailEditText.text.toString(),
                phoneEditText.text.toString(), admnoEditText.text.toString(), token)
        }
    }

    private fun resetData() {
        nameEditText.text.clear()
        emailEditText.text.clear()
        phoneEditText.text.clear()
        admnoEditText.text.clear()
        isVerified = false
        token = ""
        changeVerifyBtn(ButtonState.RESET)
    }

    private fun showUserDetails() {
        showTransition(registrationLayout, afterRegLayout)
        currentUser = ExtraUtils.getUser(context!!)
        if (currentUser != null) {
            userName.text = currentUser!!.name
            userAdmNo.text = currentUser!!.admissionNo
            userId.text = if (currentUser!!.isPaid) currentUser!!.zealID else currentUser!!.tempID
            paymentText.visibility = if (currentUser!!.isPaid) View.GONE else View.VISIBLE
        }
    }

    private fun showTransition(layout1: View, layout2: View) {
        // layout1 to layout2
        layout2.visibility = View.VISIBLE
        val alphaGoneAnim = AlphaAnimation(1f, 0f)
        val alphaShowAnim = AlphaAnimation(0f, 1f)
        alphaShowAnim.duration = 1000
        alphaGoneAnim.duration = 1000
        alphaShowAnim.fillAfter = true
        alphaGoneAnim.fillAfter = true
        layout1.startAnimation(alphaGoneAnim)
        layout2.startAnimation(alphaShowAnim)
        alphaGoneAnim.start()
        alphaShowAnim.start()
    }

    private fun registerUser(name: String, email: String, phone: String, admNo: String, token: String) {
        val map = HashMap<String, String>()
        map["name"] = name
        map["email"] = email
        map["mobile"] = phone
        map["admissionNo"] = admNo
        map["token"] = token
        viewModel.regUser(map).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    registerBtn.apply {
                        isClickable = false
                        isEnabled = false
                    }
                    regText.visibility = View.GONE
                    regLoader.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                    registerBtn.apply {
                        isClickable = true
                        isEnabled = true
                    }
                    regText.visibility = View.VISIBLE
                    regLoader.visibility = View.GONE
                    resetData()
                }
                Status.SUCCESS -> {
                    parse(it.data?.getAsJsonObject("data"))
                    registerBtn.apply {
                        isClickable = true
                        isEnabled = true
                    }
                    resetData()
                    regText.visibility = View.VISIBLE
                    regLoader.visibility = View.GONE
                }
            }
        })
    }

    private fun parse(data: JsonObject?, isSearch: Boolean = false) {
        if (data == null) {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
        } else {
            val user: User = Gson().fromJson(data.get("data").toString(),
                object : TypeToken<User>() {}.type)
            ExtraUtils.saveToPrefs(context!!, Gson().toJson(user))
            if (!isSearch)
                resetData()
            showUserDetails()
        }
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun changeVerifyBtn(state: ButtonState) {
        when(state) {
            ButtonState.LOADING -> {
                verifiedBtn.visibility = View.INVISIBLE
                areYouHumanBtn.apply {
                    isEnabled = false
                    isClickable = false
                }
                areYouHumanImg.visibility = View.GONE
                areYouHumanText.visibility = View.GONE
                loader.visibility = View.VISIBLE
            }

            ButtonState.RESET -> {
                verifiedBtn.visibility = View.INVISIBLE
                areYouHumanBtn.apply {
                    isEnabled = true
                    isClickable = true
                }
                areYouHumanImg.visibility = View.VISIBLE
                areYouHumanText.visibility = View.VISIBLE
                loader.visibility = View.GONE
            }

            ButtonState.VERIFIED -> {
                verifiedBtn.visibility = View.VISIBLE
                areYouHumanBtn.visibility = View.GONE
                verifiedBtn.isEnabled = false
                loader.visibility = View.GONE
            }
        }
    }

    private fun verifyUser() {
        SafetyNet.getClient(context!!).verifyWithRecaptcha(BuildConfig.SITE_KEY)
            .addOnCompleteListener {
                areYouHumanBtn.isClickable = true
                areYouHumanBtn.isEnabled = true
            }
            .addOnCanceledListener {
                changeVerifyBtn(ButtonState.RESET)
            }
            .addOnSuccessListener {
                token = it.tokenResult
                changeVerifyBtn(ButtonState.VERIFIED)
                isVerified = true
            }
            .addOnFailureListener {
                if (it is ApiException) {
                    Log.d("Subscription", "Error: ${CommonStatusCodes.getStatusCodeString(it.statusCode)}")
                } else {
                    Log.d("Subscription", "Error: ${it.message}")
                }
                Toast.makeText(context, "You entered wrong answers. Please try again.", Toast.LENGTH_LONG).show()
                changeVerifyBtn(ButtonState.RESET)
            }
    }

    enum class ButtonState {
        LOADING,
        RESET,
        VERIFIED
    }

}
