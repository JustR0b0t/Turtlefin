package app.turtlefin.androidtv.data.model

data class AppNotification(
	val message: String,
	val dismiss: () -> Unit,
	val public: Boolean,
)
