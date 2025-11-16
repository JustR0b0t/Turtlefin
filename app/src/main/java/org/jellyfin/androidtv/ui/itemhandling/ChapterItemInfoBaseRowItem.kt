package app.turtlefin.androidtv.ui.itemhandling

import android.content.Context
import app.turtlefin.androidtv.constant.ImageType
import app.turtlefin.androidtv.data.model.ChapterItemInfo
import app.turtlefin.androidtv.util.ImageHelper
import app.turtlefin.androidtv.util.TimeUtils
import org.jellyfin.sdk.model.extensions.ticks

class ChapterItemInfoBaseRowItem(
	val chapterInfo: ChapterItemInfo,
) : BaseRowItem(
	baseRowType = BaseRowType.Chapter,
	staticHeight = true,
) {
	override fun getImageUrl(
		context: Context,
		imageHelper: ImageHelper,
		imageType: ImageType,
		fillWidth: Int,
		fillHeight: Int
	) = chapterInfo.imagePath

	override val itemId get() = chapterInfo.itemId
	override fun getFullName(context: Context) = chapterInfo.name
	override fun getName(context: Context) = chapterInfo.name

	override fun getSubText(context: Context) =
		chapterInfo.startPositionTicks.ticks.inWholeMilliseconds.let(TimeUtils::formatMillis)
}
