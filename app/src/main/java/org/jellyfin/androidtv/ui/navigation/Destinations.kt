package app.turtlefin.androidtv.ui.navigation

import kotlinx.serialization.json.Json
import app.turtlefin.androidtv.constant.Extras
import app.turtlefin.androidtv.ui.browsing.BrowseGridFragment
import app.turtlefin.androidtv.ui.browsing.BrowseRecordingsFragment
import app.turtlefin.androidtv.ui.browsing.BrowseScheduleFragment
import app.turtlefin.androidtv.ui.browsing.BrowseViewFragment
import app.turtlefin.androidtv.ui.browsing.ByGenreFragment
import app.turtlefin.androidtv.ui.browsing.ByLetterFragment
import app.turtlefin.androidtv.ui.browsing.CollectionFragment
import app.turtlefin.androidtv.ui.browsing.GenericFolderFragment
import app.turtlefin.androidtv.ui.browsing.SuggestedMoviesFragment
import app.turtlefin.androidtv.ui.home.HomeFragment
import app.turtlefin.androidtv.ui.itemdetail.FullDetailsFragment
import app.turtlefin.androidtv.ui.itemdetail.ItemListFragment
import app.turtlefin.androidtv.ui.itemdetail.MusicFavoritesListFragment
import app.turtlefin.androidtv.ui.livetv.LiveTvGuideFragment
import app.turtlefin.androidtv.ui.playback.AudioNowPlayingFragment
import app.turtlefin.androidtv.ui.playback.CustomPlaybackOverlayFragment
import app.turtlefin.androidtv.ui.playback.nextup.NextUpFragment
import app.turtlefin.androidtv.ui.playback.stillwatching.StillWatchingFragment
import app.turtlefin.androidtv.ui.player.photo.PhotoPlayerFragment
import app.turtlefin.androidtv.ui.player.video.VideoPlayerFragment
import app.turtlefin.androidtv.ui.search.SearchFragment
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.ItemSortBy
import org.jellyfin.sdk.model.api.SeriesTimerInfoDto
import org.jellyfin.sdk.model.api.SortOrder
import java.util.UUID

@Suppress("TooManyFunctions")
object Destinations {
	// General
	val home = fragmentDestination<HomeFragment>()
	fun search(query: String? = null) = fragmentDestination<SearchFragment>(
		SearchFragment.EXTRA_QUERY to query,
	)

	// Browsing
	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryBrowser(item: BaseItemDto) = fragmentDestination<BrowseGridFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryBrowser(item: BaseItemDto, includeType: String) =
		fragmentDestination<BrowseGridFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
			Extras.IncludeType to includeType,
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun librarySmartScreen(item: BaseItemDto) = fragmentDestination<BrowseViewFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun collectionBrowser(item: BaseItemDto) = fragmentDestination<CollectionFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun folderBrowser(item: BaseItemDto) = fragmentDestination<GenericFolderFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryByGenres(item: BaseItemDto, includeType: String) =
		fragmentDestination<ByGenreFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
			Extras.IncludeType to includeType,
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryByLetter(item: BaseItemDto, includeType: String) =
		fragmentDestination<ByLetterFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
			Extras.IncludeType to includeType,
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun librarySuggestions(item: BaseItemDto) =
		fragmentDestination<SuggestedMoviesFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
		)

	// Item details
	fun itemDetails(item: UUID) = fragmentDestination<FullDetailsFragment>(
		"ItemId" to item.toString(),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun channelDetails(item: UUID, channel: UUID, programInfo: BaseItemDto) =
		fragmentDestination<FullDetailsFragment>(
			"ItemId" to item.toString(),
			"ChannelId" to channel.toString(),
			"ProgramInfo" to Json.Default.encodeToString(programInfo),
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun seriesTimerDetails(item: UUID, seriesTimer: SeriesTimerInfoDto) =
		fragmentDestination<FullDetailsFragment>(
			"ItemId" to item.toString(),
			"SeriesTimer" to Json.Default.encodeToString(seriesTimer),
		)

	fun itemList(item: UUID) = fragmentDestination<ItemListFragment>(
		"ItemId" to item.toString(),
	)

	fun musicFavorites(parent: UUID) = fragmentDestination<MusicFavoritesListFragment>(
		"ParentId" to parent.toString(),
	)

	// Live TV
	val liveTvGuide = fragmentDestination<LiveTvGuideFragment>()
	val liveTvSchedule = fragmentDestination<BrowseScheduleFragment>()
	val liveTvRecordings = fragmentDestination<BrowseRecordingsFragment>()
	val liveTvSeriesRecordings = fragmentDestination<BrowseViewFragment>(Extras.IsLiveTvSeriesRecordings to true)

	// Playback
	val nowPlaying = fragmentDestination<AudioNowPlayingFragment>()

	fun photoPlayer(
		item: UUID,
		autoPlay: Boolean,
		albumSortBy: ItemSortBy?,
		albumSortOrder: SortOrder?,
	) = fragmentDestination<PhotoPlayerFragment>(
		PhotoPlayerFragment.ARGUMENT_ITEM_ID to item.toString(),
		PhotoPlayerFragment.ARGUMENT_ALBUM_SORT_BY to albumSortBy?.serialName,
		PhotoPlayerFragment.ARGUMENT_ALBUM_SORT_ORDER to albumSortOrder?.serialName,
		PhotoPlayerFragment.ARGUMENT_AUTO_PLAY to autoPlay,
	)

	fun videoPlayer(position: Int?) = fragmentDestination<CustomPlaybackOverlayFragment>(
		"Position" to (position ?: 0)
	)

	fun videoPlayerNew(position: Int?) = fragmentDestination<VideoPlayerFragment>(
		VideoPlayerFragment.EXTRA_POSITION to position
	)

	fun nextUp(item: UUID) = fragmentDestination<NextUpFragment>(
		NextUpFragment.ARGUMENT_ITEM_ID to item.toString()
	)

	fun stillWatching(item: UUID) = fragmentDestination<StillWatchingFragment>(
		NextUpFragment.ARGUMENT_ITEM_ID to item.toString()
	)
}
