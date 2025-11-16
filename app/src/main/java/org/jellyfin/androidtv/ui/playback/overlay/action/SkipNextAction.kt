package app.turtlefin.androidtv.ui.playback.overlay.action

import android.content.Context
import androidx.leanback.widget.PlaybackControlsRow
import app.turtlefin.androidtv.ui.playback.overlay.VideoPlayerAdapter

class SkipNextAction(context: Context) : PlaybackControlsRow.SkipNextAction(context),
	AndroidAction {
	override fun onActionClicked(videoPlayerAdapter: VideoPlayerAdapter) =
		videoPlayerAdapter.next()
}
