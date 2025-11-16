package app.turtlefin.androidtv.di

import app.turtlefin.androidtv.preference.LiveTvPreferences
import app.turtlefin.androidtv.preference.PreferencesRepository
import app.turtlefin.androidtv.preference.SystemPreferences
import app.turtlefin.androidtv.preference.TelemetryPreferences
import app.turtlefin.androidtv.preference.UserPreferences
import app.turtlefin.androidtv.preference.UserSettingPreferences
import org.koin.dsl.module

val preferenceModule = module {
	single { PreferencesRepository(get(), get(), get()) }

	single { LiveTvPreferences(get()) }
	single { UserSettingPreferences(get()) }
	single { UserPreferences(get()) }
	single { SystemPreferences(get()) }
	single { TelemetryPreferences(get()) }
}
