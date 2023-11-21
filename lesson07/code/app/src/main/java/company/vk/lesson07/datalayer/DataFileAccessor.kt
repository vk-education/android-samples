package company.vk.lesson07.datalayer

import android.content.Context
import android.graphics.Color
import android.os.Environment
import androidx.core.content.edit
import company.vk.lesson07.objects.Plate
import java.io.File

class DataFileAccessor(context: Context): AbstractFileAccessor(context.filesDir, "DataFileAccessor")
