package com.example.a210986_sirnelson_lab1

// Trash collection info
data class TrashInfo(
    val area: String,
    val nextCollectionDate: String
)

// User profile info
data class UserData(
    val name: String,
    val area: String
)

// Reported issues (with serial number, area, and status)
data class ReportData(
    val id: String,          // Serial number like MPKJ-123456
    val issueType: String,   // e.g. "Missed Collection", "Damaged Bin"
    val description: String, // User-provided details
    val area: String,        // Auto-filled from Profile
    val status: String       // e.g. "Under Review", "Reviewed", "Settled"
)

// Resident info (Task 2 minimum requirement)
data class ResidentInfo(
    val name: String,
    val place: String
)