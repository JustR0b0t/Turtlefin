package app.turtlefin.androidtv.ui.playback.overlay.action

import android.content.Context
import android.view.View
import app.turtlefin.androidtv.R
import app.turtlefin.androidtv.ui.playback.PlaybackController
import app.turtlefin.androidtv.ui.playback.overlay.CustomPlaybackTransportControlGlue
import app.turtlefin.androidtv.ui.playback.overlay.LeanbackOverlayFragment
import app.turtlefin.androidtv.ui.playback.overlay.VideoPlayerAdapter

class GuideAction(
	context: Context,
	customPlaybackTransportControlGlue: CustomPlaybackTransportControlGlue
) : CustomAction(context, customPlaybackTransportControlGlue) {
	init {
		initializeWithIcon(R.drawable.ic_guide)
	}

	@Override
	override fun handleClickAction(
		playbackController: PlaybackController,
		videoPlayerAdapter: VideoPlayerAdapter,
		context: Context,
		view: View,
	) {
		videoPlayerAdapter.leanbackOverlayFragment.hideOverlay()
		videoPlayerAdapter.masterOverlayFragment.showGuide()
	}
}
