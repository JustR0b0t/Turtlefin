package app.turtlefin.androidtv.ui.preference.category

import android.os.Build
import app.turtlefin.androidtv.BuildConfig
import app.turtlefin.androidtv.R
import app.turtlefin.androidtv.ui.preference.dsl.OptionsScreen
import app.turtlefin.androidtv.ui.preference.dsl.link
import app.turtlefin.androidtv.ui.preference.screen.LicensesScreen

fun OptionsScreen.aboutCategory() = category {
	setTitle(R.string.pref_about_title)

	link {
		// Hardcoded strings for troubleshooting purposes
		title = "Turtlefin app version"
		content = "turtlefin-androidtv ${BuildConfig.VERSION_NAME} ${BuildConfig.BUILD_TYPE}"
		icon = R.drawable.ic_jellyfin
	}

	link {
		setTitle(R.string.pref_device_model)
		content = "${Build.MANUFACTURER} ${Build.MODEL}"
		icon = R.drawable.ic_tv
	}

	link {
		setTitle(R.string.licenses_link)
		setContent(R.string.licenses_link_description)
		icon = R.drawable.ic_guide
		withFragment<LicensesScreen>()
	}
}
