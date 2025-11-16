package app.turtlefin.androidtv.ui.playback.overlay.action

import android.content.Context
import android.view.View
import app.turtlefin.androidtv.R
import app.turtlefin.androidtv.ui.livetv.TvManager
import app.turtlefin.androidtv.ui.playback.PlaybackController
import app.turtlefin.androidtv.ui.playback.overlay.CustomPlaybackTransportControlGlue
import app.turtlefin.androidtv.ui.playback.overlay.LeanbackOverlayFragment
import app.turtlefin.androidtv.ui.playback.overlay.VideoPlayerAdapter as VideoPlayerAdapter

class PreviousLiveTvChannelAction(
	context: Context,
	customPlaybackTransportControlGlue: CustomPlaybackTransportControlGlue,
) : CustomAction(context, customPlaybackTransportControlGlue) {
	init {
		initializeWithIcon(R.drawable.ic_previous_episode)
	}

	@Override
	override fun handleClickAction(
		playbackController: PlaybackController,
		videoPlayerAdapter: VideoPlayerAdapter,
		context: Context,
		view: View,
	) {
		videoPlayerAdapter.masterOverlayFragment.switchChannel(TvManager.getPrevLiveTvChannel())
	}
}
