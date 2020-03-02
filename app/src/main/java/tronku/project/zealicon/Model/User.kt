package tronku.project.zealicon.Model

data class User (
    val isPaid: Boolean,
    val name: String,
    val email: String,
    val admissionNo: String,
    val mobile: String,
    val tempID: String?,
    val zealID: String?
)