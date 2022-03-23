package com.example.lecture5

import android.app.Activity
import android.os.Bundle
import android.util.Log

private const val TAG_ACTIVITY = "ActivityTag"

fun Activity.log(state: String, bundle: Bundle? = null) {
    val bundleInfo = bundle?.keySet()
        ?.joinToString(", ", "Bundle: {", "}") { key ->
            "$key=${bundle[key]}"
        }
    val activityInfo =
        "Activity: ${this.localClassName}(#${System.identityHashCode(this).toString(radix = 16)})"
    Log.d(TAG_ACTIVITY, "$activityInfo; state: $state; ${bundleInfo ?: ""}")
}