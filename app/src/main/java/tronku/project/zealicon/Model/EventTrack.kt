package tronku.project.zealicon.Model

data class EventTrack(
    val id: Int,
    val name: String,
    val description: String,
    val startDateTime: String,
    val endDateTime: String,
    val category: String
)