package app.turtlefin.androidtv.ui.browsing;

import app.turtlefin.androidtv.ui.livetv.TvManager;
import app.turtlefin.androidtv.ui.presentation.CardPresenter;

public class BrowseScheduleFragment extends EnhancedBrowseFragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void setupQueries(final RowLoader rowLoader) {
        TvManager.getScheduleRowsAsync(this, null, new CardPresenter(true), mRowsAdapter);
    }
}
