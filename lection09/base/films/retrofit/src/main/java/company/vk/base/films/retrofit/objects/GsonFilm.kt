package company.vk.base.films.retrofit.objects

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import company.vk.base.films.objects.Film

class GsonFilm: Film() {
	@SerializedName("id") @Expose override var id = ""
	@SerializedName("title") @Expose override var title = ""
	@SerializedName("original_title") @Expose override var originalTitle = ""
	@SerializedName("image") @Expose override var image = ""
	@SerializedName("release_date") @Expose override var releaseDate = ""
	@SerializedName("running_time") @Expose override var runningTime = ""
	@SerializedName("rt_score") @Expose override var rtScore = ""
}