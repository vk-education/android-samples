package company.vk.lesson07

import android.content.UriMatcher
import androidx.core.net.toUri

object ContractPlates {
    const val AUTHORITY = "company.vk.lesson07.PlateContentProvider"

    const val PLATE_PATH = "plate"

    val PLATE_CONTENT_URI = "content://$AUTHORITY/$PLATE_PATH".toUri()

    const val URI_PLATES = 1
    const val URI_PLATE_ID = 2

    val PLATE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$PLATE_PATH"
    val PLATE_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.$PLATE_PATH"

    val PLATE_URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, PLATE_PATH, URI_PLATES)
        addURI(AUTHORITY, "$PLATE_PATH/#", URI_PLATE_ID)
    }

    val PLATES = mapOf(
        URI_PLATES to PLATE_CONTENT_TYPE,
        URI_PLATE_ID to PLATE_CONTENT_ITEM_TYPE
    )

    const val COLUMN_COLOR = "COLOR"
    const val COLUMN_VALUE = "VALUE"

    const val VALUE_ALL = -1L
}
