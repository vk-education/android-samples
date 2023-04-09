package company.vk.Lesson06

import okhttp3.mockwebserver.MockResponse

object ResponseUtils {
	fun successResponse(responseFileName: String): MockResponse {
		val stringBody = AssetsUtils.fromAssets("responses/$responseFileName")
		return MockResponse().setResponseCode(200).setBody(stringBody)
	}
}