package app.turtlefin.androidtv.ui.playback.overlay.action

import app.turtlefin.androidtv.ui.playback.overlay.VideoPlayerAdapter

interface AndroidAction {
	fun onActionClicked(
		videoPlayerAdapter: VideoPlayerAdapter
	)
}
