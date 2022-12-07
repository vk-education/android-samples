package com.vkeducation.lection07.db.cp

import android.net.Uri

object DbUri {
    const val AUTHORITY = "com.vkeducation.lection07.db.cp"

    val texts by lazy(LazyThreadSafetyMode.NONE) { "texts" }
    val ids by lazy(LazyThreadSafetyMode.NONE) { "ids" }
    fun text(id: String) = "/ids/$id/text"

    fun create(path: String): Uri {
        return Uri.parse("content://$AUTHORITY/$path")
    }

}