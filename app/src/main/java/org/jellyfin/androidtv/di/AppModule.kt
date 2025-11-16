package app.turtlefin.androidtv.di

import android.content.Context
import android.os.Build
import androidx.lifecycle.ProcessLifecycleOwner
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.network.NetworkFetcher
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.serviceLoaderEnabled
import coil3.svg.SvgDecoder
import coil3.util.Logger
import app.turtlefin.androidtv.BuildConfig
import app.turtlefin.androidtv.auth.repository.ServerRepository
import app.turtlefin.androidtv.auth.repository.UserRepository
import app.turtlefin.androidtv.auth.repository.UserRepositoryImpl
import app.turtlefin.androidtv.data.eventhandling.SocketHandler
import app.turtlefin.androidtv.data.model.DataRefreshService
import app.turtlefin.androidtv.data.repository.CustomMessageRepository
import app.turtlefin.androidtv.data.repository.CustomMessageRepositoryImpl
import app.turtlefin.androidtv.data.repository.ItemMutationRepository
import app.turtlefin.androidtv.data.repository.ItemMutationRepositoryImpl
import app.turtlefin.androidtv.data.repository.NotificationsRepository
import app.turtlefin.androidtv.data.repository.NotificationsRepositoryImpl
import app.turtlefin.androidtv.data.repository.UserViewsRepository
import app.turtlefin.androidtv.data.repository.UserViewsRepositoryImpl
import app.turtlefin.androidtv.data.service.BackgroundService
import app.turtlefin.androidtv.integration.dream.DreamViewModel
import app.turtlefin.androidtv.ui.InteractionTrackerViewModel
import app.turtlefin.androidtv.ui.itemhandling.ItemLauncher
import app.turtlefin.androidtv.ui.navigation.Destinations
import app.turtlefin.androidtv.ui.navigation.NavigationRepository
import app.turtlefin.androidtv.ui.navigation.NavigationRepositoryImpl
import app.turtlefin.androidtv.ui.playback.PlaybackControllerContainer
import app.turtlefin.androidtv.ui.playback.nextup.NextUpViewModel
import app.turtlefin.androidtv.ui.playback.segment.MediaSegmentRepository
import app.turtlefin.androidtv.ui.playback.segment.MediaSegmentRepositoryImpl
import app.turtlefin.androidtv.ui.playback.stillwatching.StillWatchingViewModel
import app.turtlefin.androidtv.ui.player.photo.PhotoPlayerViewModel
import app.turtlefin.androidtv.ui.search.SearchFragmentDelegate
import app.turtlefin.androidtv.ui.search.SearchRepository
import app.turtlefin.androidtv.ui.search.SearchRepositoryImpl
import app.turtlefin.androidtv.ui.search.SearchViewModel
import app.turtlefin.androidtv.ui.startup.ServerAddViewModel
import app.turtlefin.androidtv.ui.startup.StartupViewModel
import app.turtlefin.androidtv.ui.startup.UserLoginViewModel
import app.turtlefin.androidtv.util.KeyProcessor
import app.turtlefin.androidtv.util.MarkdownRenderer
import app.turtlefin.androidtv.util.PlaybackHelper
import app.turtlefin.androidtv.util.apiclient.ReportingHelper
import app.turtlefin.androidtv.util.coil.CoilTimberLogger
import app.turtlefin.androidtv.util.coil.createCoilConnectivityChecker
import app.turtlefin.androidtv.util.sdk.SdkPlaybackHelper
import org.jellyfin.sdk.android.androidDevice
import org.jellyfin.sdk.api.client.HttpClientOptions
import org.jellyfin.sdk.api.okhttp.OkHttpFactory
import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.jellyfin.sdk.Jellyfin as JellyfinSdk

val defaultDeviceInfo = named("defaultDeviceInfo")

val appModule = module {
	// SDK
	single(defaultDeviceInfo) { androidDevice(get()) }
	single { OkHttpFactory() }
	single { HttpClientOptions() }
	single {
		createJellyfin {
			context = androidContext()

			// Add client info
			val clientName = buildString {
				append("Jellyfin Android TV")
				if (BuildConfig.DEBUG) append(" (debug)")
			}
			clientInfo = ClientInfo(clientName, BuildConfig.VERSION_NAME)
			deviceInfo = get(defaultDeviceInfo)

			// Change server version
			minimumServerVersion = ServerRepository.minimumServerVersion

			// Use our own shared factory instance
			apiClientFactory = get<OkHttpFactory>()
			socketConnectionFactory = get<OkHttpFactory>()
		}
	}

	single {
		// Create an empty API instance, the actual values are set by the SessionRepository
		get<JellyfinSdk>().createApi(httpClientOptions = get<HttpClientOptions>())
	}

	single { SocketHandler(get(), get(), get(), get(), get(), get(), get(), get(), get(), ProcessLifecycleOwner.get().lifecycle) }

	// Coil (images)
	single {
		val okHttpFactory = get<OkHttpFactory>()
		val httpClientOptions = get<HttpClientOptions>()

		@OptIn(ExperimentalCoilApi::class)
		OkHttpNetworkFetcherFactory(
			callFactory = { okHttpFactory.createClient(httpClientOptions) },
			connectivityChecker = ::createCoilConnectivityChecker,
		)
	}

	single {
		ImageLoader.Builder(androidContext()).apply {
			serviceLoaderEnabled(false)
			logger(CoilTimberLogger(if (BuildConfig.DEBUG) Logger.Level.Warn else Logger.Level.Error))

			components {
				add(get<NetworkFetcher.Factory>())

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(AnimatedImageDecoder.Factory())
				else add(GifDecoder.Factory())
				add(SvgDecoder.Factory())
			}
		}.build()
	}

	// Non API related
	single { DataRefreshService() }
	single { PlaybackControllerContainer() }
	single { InteractionTrackerViewModel(get(), get()) }

	single<UserRepository> { UserRepositoryImpl() }
	single<UserViewsRepository> { UserViewsRepositoryImpl(get()) }
	single<NotificationsRepository> { NotificationsRepositoryImpl(get(), get()) }
	single<ItemMutationRepository> { ItemMutationRepositoryImpl(get(), get()) }
	single<CustomMessageRepository> { CustomMessageRepositoryImpl() }
	single<NavigationRepository> { NavigationRepositoryImpl(Destinations.home) }
	single<SearchRepository> { SearchRepositoryImpl(get()) }
	single<MediaSegmentRepository> { MediaSegmentRepositoryImpl(get(), get()) }

	viewModel { StartupViewModel(get(), get(), get(), get()) }
	viewModel { UserLoginViewModel(get(), get(), get(), get(defaultDeviceInfo)) }
	viewModel { ServerAddViewModel(get()) }
	viewModel { NextUpViewModel(get(), get(), get()) }
	viewModel { StillWatchingViewModel(get(), get(), get(), get()) }
	viewModel { PhotoPlayerViewModel(get()) }
	viewModel { SearchViewModel(get()) }
	viewModel { DreamViewModel(get(), get(), get(), get(), get()) }

	single { BackgroundService(get(), get(), get(), get(), get()) }

	single { MarkdownRenderer(get()) }
	single { ItemLauncher() }
	single { KeyProcessor() }
	single { ReportingHelper(get(), get()) }
	single<PlaybackHelper> { SdkPlaybackHelper(get(), get(), get(), get()) }

	factory { (context: Context) -> SearchFragmentDelegate(context, get(), get()) }
}
