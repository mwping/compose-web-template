data class PerfIssue(val title: String, val details: List<PerfIssueDetail>) {
    companion object {
        fun fromCsv(content: String): List<PerfIssue> {
            val issueDetailList = content.split("\n").map {
                val strs = it.split(",")
                console.log(strs)
                PerfIssueDetail(
                    group = strs[0],
                    name = strs[1],
                    detail = strs.getOrNull(2) ?: ""
                )
            }
            return issueDetailList.map { it.group }.toSet().map {
                PerfIssue(
                    title = it,
                    details = issueDetailList.filter { detail ->
                        detail.group == it
                    }
                )
            }
        }
    }
}