package tronku.project.zealicon.Utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import tronku.project.zealicon.Model.User


object ExtraUtils {

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun saveToPrefs(context: Context, data: String) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("user", data)
        editor.apply()
    }

    fun getFromPrefs(context: Context, fieldName: String): String {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getString(fieldName, "") ?: ""
    }

    fun getUser(context: Context): User? {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return if (pref.getString("user", "").isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(pref.getString("user", ""), User::class.java)
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}