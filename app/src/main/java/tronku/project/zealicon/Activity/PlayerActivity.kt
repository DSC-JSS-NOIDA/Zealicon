package tronku.project.zealicon.Activity

import android.Manifest
import android.animation.ObjectAnimator
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_player.*
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Fragment.InfoBottomSheetFragment
import tronku.project.zealicon.Model.EventTrackDB
import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils
import tronku.project.zealicon.Utils.ExtraUtils
import tronku.project.zealicon.Viewmodel.PlayerViewModel
import java.util.*
import kotlin.Exception
import kotlin.collections.ArrayList

class PlayerActivity : AppCompatActivity() {

    private lateinit var media: MediaPlayer
    private var isMute = false
    private var isPlaying = true
    private var tracks = ArrayList<EventTrackDB>()
    private lateinit var currentTrack: EventTrackDB
    private var currentPos = 0
    private var isRegistered = false
    private var isAdded = false
    private val CALENDER_CODE = 101
    private lateinit var musicList: ArrayList<Int>
    private var create = true

    private val db by lazy { RoomDB(this) }
    private val viewModel by lazy { PlayerViewModel(db) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        musicList = ArrayList()
        musicList.add(R.raw.i_walk_alone)
        musicList.add(R.raw.edm_drop)
        musicList.add(R.raw.martin_edm)

        tracks = intent.getParcelableArrayListExtra("tracks")
        currentPos = intent.getIntExtra("position", 0)
        inflateUI()
        addClickEvents()
        setObservers()
    }

    private fun initMediaPlayer() {
        create = false
        var song = musicList.get(currentPos%musicList.size)
        media =  MediaPlayer.create(this, song)
            .apply {
                AudioManager.STREAM_MUSIC
                AudioManager.AUDIOFOCUS_REQUEST_GRANTED
            }
        media.isLooping = true
        playPauseMusic()
    }

    override fun onResume() {
        super.onResume()
        if (create)
            initMediaPlayer()
        else
            playPauseMusic()
    }

    private fun inflateUI() {
        eventName.isSelected = true
        currentTrack = tracks[currentPos]
        eventName.text = currentTrack.name
        eventType.text = currentTrack.category
        viewModel.checkAdded(currentTrack.id)
        viewModel.checkIfReg(currentTrack.id)
    }

    private fun setObservers() {
        viewModel.isAdded.observe(this, Observer {
            addRemoveButton.setImageResource(if (it) R.drawable.ic_playlist_added else R.drawable.ic_playlist_add)
            isAdded = it
        })

        viewModel.isRegistered.observe(this, Observer {
            isRegistered = it
        })
    }

    private fun addClickEvents() {
        val rotator = ObjectAnimator.ofFloat(trackImage, View.ROTATION, 0f, 360f)
        rotator.duration = 6000
        rotator.repeatCount = ObjectAnimator.INFINITE
        rotator.interpolator = LinearInterpolator()
        rotator.start()

        buttonPlayPause.setOnClickListener {
            AnimUtils.setClickAnimation(buttonPlayPause)
           if (isPlaying) {
               isPlaying = false
               buttonPlayPause.setImageResource(R.drawable.play_button)
               rotator.pause()
               waveView.pause()
               playPauseMusic()
           } else {
               isPlaying = true
               buttonPlayPause.setImageResource(R.drawable.pause_icon)
               rotator.resume()
               waveView.play()
               playPauseMusic()
           }
        }

        buttonBack.setOnClickListener {
            AnimUtils.setClickAnimation(buttonBack)
            if (currentPos == 0) {
                Toast.makeText(this@PlayerActivity, "No tracks available", Toast.LENGTH_SHORT).show()
            } else {
                currentPos--
                media.release()
                initMediaPlayer()
                inflateUI()
            }
        }

        buttonNext.setOnClickListener {
            AnimUtils.setClickAnimation(buttonNext)
            if (currentPos == tracks.size - 1) {
                Toast.makeText(this@PlayerActivity, "No tracks available", Toast.LENGTH_SHORT)
                    .show()
            } else {
                currentPos++
                media.release()
                initMediaPlayer()
                inflateUI()
            }
        }

        backButton.setOnClickListener {
            AnimUtils.setClickAnimation(backButton)
            onBackPressed()
        }

        addRemoveButton.setOnClickListener {
            AnimUtils.setClickAnimation(addRemoveButton)
            when {
                isRegistered -> {
                    Toast.makeText(this@PlayerActivity, "You have registered for this event!",
                        Toast.LENGTH_SHORT).show()
                }
                isAdded -> {
                    getPermission()
                }
                else -> {
                    getPermission()
                }
            }
        }

        buttonMuteUnmute.setOnClickListener {
            AnimUtils.setClickAnimation(buttonMuteUnmute)
            if (isMute) {
                isMute = false
                buttonMuteUnmute.setImageResource(R.drawable.ic_unmute)
            } else {
                isMute = true
                buttonMuteUnmute.setImageResource(R.drawable.ic_mute)
            }
            playPauseMusic()
        }

        infoButton.setOnClickListener {
            AnimUtils.setClickAnimation(infoButton)
            val bottomSheet = InfoBottomSheetFragment.newInstance(currentTrack)
            bottomSheet.show(supportFragmentManager, InfoBottomSheetFragment.TAG)
        }

        buttonShare.setOnClickListener {
            AnimUtils.setClickAnimation(it)
            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show()
            shareEvent()
        }
    }

    private fun getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) !=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR
                ),
                CALENDER_CODE
            )
        } else {
            addReminderInCalendar()
        }
    }

    private fun addReminderInCalendar() {
        if (!isAdded) {
            val cr = contentResolver
            val timeZone = TimeZone.getTimeZone("Asia/Kolkata")
            val baseStartDate = Date(1585040400000)
            val baseEndDate = Date(1585083600000)
            val name = currentTrack.name
            val description = currentTrack.description
            val day = currentTrack.day

            var values = ContentValues().apply {
                put(CalendarContract.Events.CALENDAR_ID, 1)
                put(CalendarContract.Events.TITLE, name)
                put(CalendarContract.Events.DESCRIPTION, description)
                put(CalendarContract.Events.ALL_DAY, 1)
                put(CalendarContract.Events.DTSTART, baseStartDate.time * day)
                put(CalendarContract.Events.DTEND, baseEndDate.time * day)
                put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.id)
                put(CalendarContract.Events.HAS_ALARM, true)
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) !=
                PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR
                    ), CALENDER_CODE
                )
            }

            val eventUri = cr.insert(CalendarContract.Events.CONTENT_URI, values)
            val eventId = eventUri!!.lastPathSegment!!.toLong()
            ExtraUtils.saveToPrefs(this, currentTrack.id.toString(), eventId.toString())
            viewModel.addToPlaylist(currentTrack.id)

            values = ContentValues().apply {
                put(CalendarContract.Reminders.EVENT_ID, eventId)
                put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
                put(CalendarContract.Reminders.MINUTES, 30)
            }
            try {
                cr.insert(CalendarContract.Reminders.CONTENT_URI, values)
                Toast.makeText(this, "Event reminder has been added!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("REMINDER ERROR", e.message.toString())
                Toast.makeText(this, "Event added to the playlist!", Toast.LENGTH_SHORT).show()
            }

        } else if (isAdded && !isRegistered) {
            val calenderEventId: String = ExtraUtils.getFromPrefs(this, currentTrack.id.toString())
            try {
                val uri = ContentUris.withAppendedId(
                    CalendarContract.Events.CONTENT_URI,
                    calenderEventId.toLong()
                )
                contentResolver.delete(uri, null, null)
                Toast.makeText(this, "Event reminder has been removed!", Toast.LENGTH_SHORT).show()
                viewModel.removeFromPlaylist(currentTrack.id)
            } catch (e: Exception) {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun shareEvent() {
        val message = "Hey! I just explored *${currentTrack.name}* on Zealicon app. It's going to be" +
                " happen on *day ${currentTrack.day}* of the fest. Here's the event's detail -\n${currentTrack.description}" +
                "\n\nYou can register for the event on the app and website. " +
                "\nDownload now - https://play.google.com/store/apps/details?id=tronku.project.zealicon"

        val share = Intent()
        share.action = Intent.ACTION_SEND
        share.putExtra(Intent.EXTRA_TEXT, message)
        share.type = "text/plain"
        startActivity(Intent.createChooser(share, "Share via: "))
    }

    private fun playPauseMusic(){
        if (!isMute and isPlaying)
            media.start()
        else if (media.isPlaying)
            media.pause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CALENDER_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addReminderInCalendar()
            } else {
                Toast.makeText(this@PlayerActivity, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        media.pause()
    }

    override fun onStop() {
        super.onStop()
        media.pause()
    }

}
