package app.turtlefin.androidtv.ui

fun interface ValueChangedListener<T> {
	fun onValueChanged(value: T)
}
