import AppStylesheet.issueGroupText
import AppStylesheet.issueNameText
import androidx.compose.runtime.*
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.renderComposable
import io.ktor.client.statement.*
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.*

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

suspend fun loadPerfIssues(): List<PerfIssue> {
    val client = HttpClient {}
    val response: HttpResponse =
        client.get("https://atlasv-android.oss-cn-hangzhou.aliyuncs.com/shotcut/perf/perf-issue-home.csv")
    return PerfIssue.fromCsv(response.readText())
}

@Composable
fun Body() {
    Style(AppStylesheet)
    var issueList by remember {
        mutableStateOf(listOf<PerfIssue>())
    }
    CoroutineScope(Dispatchers.Default).launch {
        issueList = loadPerfIssues()
    }
    Ul(attrs = {
        classes(AppStylesheet.issueUl)
    }, content = {
        issueList.map { perfIssue ->
            Li {
                P(attrs = { classes(issueGroupText) }, content = {
                    Text(perfIssue.title)
                })
                Ul(content = {
                    perfIssue.details.map { detail ->
                        Li {
                            P(attrs = { classes(issueNameText) }, content = {
                                Text(detail.name)
                            })
                        }
                    }
                })
            }
        }
    })
}
