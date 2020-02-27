package tronku.project.zealicon.Fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.subscription_fragment.*
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
    }

    private fun setListeners() {
        areYouHumanBtn.setOnClickListener {
            AnimUtils.setClickAnimation(it)
            changeVerifyBtn(ButtonState.LOADING)
            verifyUser()
        }

        registerBtn.setOnClickListener {
            AnimUtils.setClickAnimation(it)
            validateData() }
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
            // validated data
            registerUser(nameEditText.text.toString(), emailEditText.text.toString(), phoneEditText.text.toString(), admnoEditText.text.toString(), token)
        }
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
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                    registerBtn.apply {
                        isClickable = true
                        isEnabled = true
                    }
                    regText.visibility = View.VISIBLE
                    regLoader.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    parse(it.data?.getAsJsonObject("data"))
                    registerBtn.apply {
                        isClickable = true
                        isEnabled = true
                    }

                    regText.visibility = View.VISIBLE
                    regLoader.visibility = View.GONE
                }
            }
        })
    }

    private fun parse(data: JsonObject?) {
        if (data == null) {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
        } else {
            val user: User = Gson().fromJson(data.get("data").toString(),
                object : TypeToken<User>() {}.type)
            ExtraUtils.saveToPrefs(context!!, user.mobile,
                if (user.isPaid) user.zealID.toString() else user.tempID.toString(), user.isPaid)
        }
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun changeVerifyBtn(state: ButtonState) {
        when(state) {
            ButtonState.LOADING -> {
                areYouHumanBtn.apply {
                    isEnabled = false
                    isClickable = false
                }
                areYouHumanImg.visibility = View.GONE
                areYouHumanText.visibility = View.GONE
                loader.visibility = View.VISIBLE
            }

            ButtonState.RESET -> {
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
                changeVerifyBtn(ButtonState.RESET)
            }
    }

    enum class ButtonState {
        LOADING,
        RESET,
        VERIFIED
    }

}
