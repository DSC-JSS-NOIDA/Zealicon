package tronku.project.zealicon.Utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.preference.PreferenceManager

object ExtraUtils {

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun saveToPrefs(context: Context, phone: String, id: String, isPaid: Boolean) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putBoolean("isPaid", isPaid)
        editor.putString("phone", phone)
        editor.putString("id", id)
        editor.apply()
    }
}