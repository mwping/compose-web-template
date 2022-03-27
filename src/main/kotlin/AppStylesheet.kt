import org.jetbrains.compose.web.css.*

object AppStylesheet : StyleSheet() {
    val issueUl by style {
    }

    val issueGroupText by style {
        fontSize(18.px)
        color(Color.blueviolet)
    }

    val issueNameText by style {
        fontSize(16.px)
        color(Color.green)
    }
}