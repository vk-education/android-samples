class TextView {
    var text: String = ""
    var textSize = 10.0
    private var paddingTop: Int = 0
    private var paddingRight: Int = 0
    private var paddingBottom: Int = 0
    private var paddingLeft: Int = 0

    fun setPadding(
        paddingTop: Int,
        paddingRight: Int,
        paddingBottom: Int,
        paddingLeft: Int
    ) {
        this.paddingTop = paddingTop
        this.paddingRight = paddingRight
        this.paddingBottom = paddingBottom
        this.paddingLeft = paddingLeft
    }
}

fun createViewWithCustomAttributes(): TextView {
    return null!!
}