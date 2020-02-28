package tronku.project.zealicon.Activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_player.*
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Fragment.InfoBottomSheetFragment
import tronku.project.zealicon.Model.EventTrackDB
import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils
import tronku.project.zealicon.Viewmodel.PlayerViewModel

class PlayerActivity : AppCompatActivity() {

    private lateinit var media: MediaPlayer
    private var isMute = false
    private var isPlaying = true
    private var tracks = ArrayList<EventTrackDB>()
    private lateinit var currentTrack: EventTrackDB
    private var currentPos = 0
    private var isRegistered = false

    private val db by lazy { RoomDB(this) }
    private val viewModel by lazy { PlayerViewModel(db) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        tracks = intent.getParcelableArrayListExtra("tracks")
        currentPos = intent.getIntExtra("position", 0)
        inflateUI()
        addClickEvents()
        setObservers()
    }

    private fun initMediaPlayer() {
        media =  MediaPlayer.create(this, R.raw.gurbax_boom_shankar)
            .apply {
                AudioManager.STREAM_MUSIC
                AudioManager.AUDIOFOCUS_REQUEST_GRANTED
            }

        media.isLooping = true
        playPauseMusic()
    }

    override fun onResume() {
        super.onResume()
        initMediaPlayer()
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
                    Toast.makeText(this@PlayerActivity, "You have registered for this event!", Toast.LENGTH_SHORT).show()
                }
                viewModel.isAdded.value == true -> {
                    viewModel.removeFromPlaylist(currentTrack.id)
                    Toast.makeText(this@PlayerActivity, "Removed from Playlist", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.addToPlaylist(currentTrack.id)
                    Toast.makeText(this@PlayerActivity, "Added to Playlist", Toast.LENGTH_SHORT).show()
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
            shareEvent()
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
        else
            media.pause()
    }

    override fun onPause() {
        super.onPause()
        media.stop()
    }

}
