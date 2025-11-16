package app.turtlefin.androidtv.ui.preference.screen

import app.turtlefin.androidtv.R
import app.turtlefin.androidtv.constant.HomeSectionType
import app.turtlefin.androidtv.preference.UserSettingPreferences
import app.turtlefin.androidtv.ui.preference.dsl.OptionsFragment
import app.turtlefin.androidtv.ui.preference.dsl.enum
import app.turtlefin.androidtv.ui.preference.dsl.optionsScreen
import org.jellyfin.preference.store.PreferenceStore
import org.koin.android.ext.android.inject

class HomePreferencesScreen : OptionsFragment() {
	private val userSettingPreferences: UserSettingPreferences by inject()

	override val stores: Array<PreferenceStore<*, *>>
		get() = arrayOf(userSettingPreferences)

	override val screen by optionsScreen {
		setTitle(R.string.home_prefs)

		category {
			setTitle(R.string.home_sections)

			userSettingPreferences.homesections.forEachIndexed { index, section ->
				enum<HomeSectionType> {
					title = getString(R.string.home_section_i, index + 1)
					bind(userSettingPreferences, section)
				}
			}
		}
	}
}
