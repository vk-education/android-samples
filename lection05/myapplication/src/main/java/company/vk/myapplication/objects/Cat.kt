package company.vk.myapplication.objects

import com.google.gson.annotations.SerializedName

class Cat {
	@SerializedName("_id") var id = ""

	fun imageId() = id
}