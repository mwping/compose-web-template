import androidx.compose.runtime.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

suspend fun doNetworkCall(): String {
    val client = HttpClient()
    val response: HttpResponse =
        client.get("https://atlasv-android.oss-cn-hangzhou.aliyuncs.com/shotcut/perf/perf-issue-home.json")
    return response.readText()
}

@Composable
fun Body() {
    var counter by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    Div {
        Text("${title}: ${counter}")
    }
    Button(
        attrs = {
            onClick { _ ->
                CoroutineScope(Dispatchers.Default).launch {
                    title = doNetworkCall()
                    counter += 10
                }
//                counter++
            }
        }
    ) {
        Text("Click")
    }
}
