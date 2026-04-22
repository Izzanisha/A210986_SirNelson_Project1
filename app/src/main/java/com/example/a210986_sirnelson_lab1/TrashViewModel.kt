package com.example.a210986_sirnelson_lab1
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrashViewModel : ViewModel() {
    //Trash collection info
    private val _trashInfo = MutableLiveData<TrashInfo>()
    val trashInfo: LiveData<TrashInfo> = _trashInfo
    fun setTrashInfo(area: String, date: String) {
        _trashInfo.value = TrashInfo(area, date)
    }

    // User profile info
    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData
    fun setUserData(name: String, area: String) {
        _userData.value = UserData(name, area)
        // keep trashInfo in sync with userData
        if (_trashInfo.value == null) {
            _trashInfo.value = TrashInfo(area, "Next date not set")
        } else {
            _trashInfo.value = _trashInfo.value?.copy(area = area)
        }
    }


    // Reported issues (supports multiple reports with serial number + status)
    private val _reportData = MutableLiveData<List<ReportData>>(emptyList())
    val reportData: LiveData<List<ReportData>> = _reportData

    private var reportCounter = 100000 // starting number for serial IDs

    // Add new report (status always "Under Review")
    fun addReport(issueType: String, description: String, area: String) {
        reportCounter++
        val newReport = ReportData(
            id = "MPKJ-$reportCounter",
            issueType = issueType,
            description = description,
            area = area,
            status = "Under Review"
        )
        _reportData.value = _reportData.value!! + newReport
    }
}