package app.turtlefin.androidtv.ui.player.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.turtlefin.androidtv.ui.composable.rememberQueueEntry
import app.turtlefin.androidtv.ui.player.base.PlayerOverlayLayout
import app.turtlefin.androidtv.ui.player.base.rememberPlayerOverlayVisibility
import org.jellyfin.playback.core.PlaybackManager
import org.jellyfin.playback.jellyfin.queue.baseItem
import org.jellyfin.playback.jellyfin.queue.baseItemFlow
import org.koin.compose.koinInject

@Composable
fun VideoPlayerOverlay(
	modifier: Modifier = Modifier,
	playbackManager: PlaybackManager = koinInject(),
) {
	val visibilityState = rememberPlayerOverlayVisibility()

	val entry by rememberQueueEntry(playbackManager)
	val item = entry?.run { baseItemFlow.collectAsState(baseItem) }?.value

	PlayerOverlayLayout(
		visibilityState = visibilityState,
		modifier = modifier,
		header = {
			VideoPlayerHeader(
				item = item,
			)
		},
		controls = {
			VideoPlayerControls(
				playbackManager = playbackManager,
			)
		},
	)
}
