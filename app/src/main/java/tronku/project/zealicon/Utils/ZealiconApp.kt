package tronku.project.zealicon.Utils

import android.app.Application
import com.facebook.stetho.Stetho

class ZealiconApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}