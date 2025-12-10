package company.vk.lesson08.businesslayer

object Music {
    enum class Items(val title: String, val author: String, val path: String) {
        TRACK_1("Celtic", "Crowander", "asset:///music/track_01.mp3"),
        TRACK_2("Celtic Warrior", "Damiano Baldoni", "asset:///music/track_02.mp3"),
        TRACK_3("Lark in the Morning. The Atholl Highlanders", "Sláinte", "asset:///music/track_03.mp3")
    }
}