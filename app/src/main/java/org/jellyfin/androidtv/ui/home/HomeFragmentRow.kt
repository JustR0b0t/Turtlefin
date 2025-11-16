package app.turtlefin.androidtv.ui.home

import android.content.Context
import androidx.leanback.widget.Row
import app.turtlefin.androidtv.ui.presentation.CardPresenter
import app.turtlefin.androidtv.ui.presentation.MutableObjectAdapter

interface HomeFragmentRow {
	fun addToRowsAdapter(context: Context, cardPresenter: CardPresenter, rowsAdapter: MutableObjectAdapter<Row>)
}
