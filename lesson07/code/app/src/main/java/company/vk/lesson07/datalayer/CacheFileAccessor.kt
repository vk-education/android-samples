package company.vk.lesson07.datalayer

import android.content.Context
import android.graphics.Color
import androidx.core.content.edit
import company.vk.lesson07.objects.Plate
import java.io.File

class CacheFileAccessor(context: Context): AbstractFileAccessor(context.cacheDir, "CacheFileAccessor")
