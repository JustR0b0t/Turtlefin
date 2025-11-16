package app.turtlefin.androidtv.di

import app.turtlefin.androidtv.auth.AccountManagerMigration
import app.turtlefin.androidtv.auth.repository.AuthenticationRepository
import app.turtlefin.androidtv.auth.repository.AuthenticationRepositoryImpl
import app.turtlefin.androidtv.auth.repository.ServerRepository
import app.turtlefin.androidtv.auth.repository.ServerRepositoryImpl
import app.turtlefin.androidtv.auth.repository.ServerUserRepository
import app.turtlefin.androidtv.auth.repository.ServerUserRepositoryImpl
import app.turtlefin.androidtv.auth.repository.SessionRepository
import app.turtlefin.androidtv.auth.repository.SessionRepositoryImpl
import app.turtlefin.androidtv.auth.store.AuthenticationPreferences
import app.turtlefin.androidtv.auth.store.AuthenticationStore
import org.koin.dsl.module

val authModule = module {
	single { AccountManagerMigration(get()) }
	single { AuthenticationStore(get(), get()) }
	single { AuthenticationPreferences(get()) }

	single<AuthenticationRepository> {
		AuthenticationRepositoryImpl(get(), get(), get(), get(), get(), get(defaultDeviceInfo))
	}
	single<ServerRepository> { ServerRepositoryImpl(get(), get()) }
	single<ServerUserRepository> { ServerUserRepositoryImpl(get(), get()) }
	single<SessionRepository> {
		SessionRepositoryImpl(get(), get(), get(), get(), get(defaultDeviceInfo), get(), get(), get())
	}

	factory {
		val serverRepository = get<ServerRepository>()
		serverRepository.currentServer.value?.serverVersion ?: ServerRepository.minimumServerVersion
	}
}
