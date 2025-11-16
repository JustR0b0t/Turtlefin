package app.turtlefin.androidtv.ui.browsing

interface RowLoader {
	fun loadRows(rows: MutableList<BrowseRowDef>)
}
