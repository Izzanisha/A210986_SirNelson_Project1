package com.example.a210986_sirnelson_lab1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*

class TrashViewModel : ViewModel() {

    /* =========================
       TRASH INFO
       ========================= */
    private val _trashInfo = MutableLiveData<TrashInfo>()
    val trashInfo: LiveData<TrashInfo> = _trashInfo

    fun setTrashInfo(area: String, date: String) {
        _trashInfo.value = TrashInfo(area, date)
    }

    /* =========================
       USER DATA
       ========================= */
    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    fun setUserData(name: String, area: String) {
        _userData.value = UserData(name, area)

        if (_trashInfo.value == null) {
            _trashInfo.value = TrashInfo(area, "Next date not set")
        } else {
            _trashInfo.value = _trashInfo.value?.copy(area = area)
        }

        //  update all reminders with new area
        _reminders.value = _reminders.value.map {
            it.copy(area = area)
        }
    }

    /* =========================
       REPORT DATA
       ========================= */
    private val _reportData = MutableLiveData<List<ReportData>>(emptyList())
    val reportData: LiveData<List<ReportData>> = _reportData

    private var reportCounter = 100000

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

    /* =========================
       REMINDERS
       ========================= */
    private val _reminders = MutableStateFlow(
        listOf(
            Reminder(
                type = "General Waste",
                date = "17 April 2026",
                time = "07:00 AM",
                area = "Kajang",
                enabled = true
            )
        )
    )

    val reminders: StateFlow<List<Reminder>> = _reminders

    /* =========================
       NOTIFICATION HISTORY
       ========================= */
    private val _notificationHistory = MutableStateFlow<List<String>>(emptyList())
    val notificationHistory: StateFlow<List<String>> = _notificationHistory

    /* =========================
       ✅ SYNC REMINDER WITH SCHEDULE (FIXED ✅)
       ========================= */
    fun syncReminderWithSchedule(type: String, date: String) {

        val area = _userData.value?.area ?: "Unknown"

        val scheduleReminder = Reminder(
            type = type,
            date = date,
            time = "07:00 AM",
            area = area,
            enabled = true,
            timings = listOf("1 day before")
        )

        // ✅ FIX: do NOT delete all reminders
        _reminders.value =
            listOf(scheduleReminder) +
                    _reminders.value.filter { it.type != type }

        val timestamp = SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(Date())

        _notificationHistory.value =
            _notificationHistory.value +
                    "Auto updated: $type on $date • $timestamp"
    }

    /* =========================
       ADD REMINDER
       ========================= */
    fun addReminder(type: String, date: String, time: String) {

        val area = _userData.value?.area ?: "Unknown"

        val newReminder = Reminder(
            type = type,
            date = date,
            time = time,
            area = area,
            enabled = true
        )

        _reminders.value = _reminders.value + newReminder
    }

    /* =========================
       TOGGLE
       ========================= */
    fun toggleReminder(index: Int, enabled: Boolean) {

        _reminders.value = _reminders.value.mapIndexed { i, r ->
            if (i == index) r.copy(enabled = enabled) else r
        }
    }

    /* =========================
       UPDATE TIMINGS
       ========================= */
    fun updateReminderTimings(index: Int, newTimings: List<String>) {
        _reminders.value = _reminders.value.mapIndexed { i, r ->
            if (i == index) r.copy(timings = newTimings) else r
        }
    }

    /* =========================
       DELETE
       ========================= */
    fun deleteReminder(index: Int) {
        val list = _reminders.value.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            _reminders.value = list
        }
    }

    /* =========================
       EDIT
       ========================= */
    fun editReminder(index: Int, newDate: String, newTime: String) {
        _reminders.value = _reminders.value.mapIndexed { i, r ->
            if (i == index) r.copy(date = newDate, time = newTime) else r
        }
    }

    /* =========================
       REPEAT
       ========================= */
    fun toggleRepeat(index: Int) {

        val timestamp = SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(Date())

        _notificationHistory.value =
            _notificationHistory.value +
                    "Repeat enabled for reminder ${index + 1} • $timestamp"
    }

    /* =========================
       CLEAR HISTORY
       ========================= */
    fun clearNotificationHistory() {
        _notificationHistory.value = emptyList()
    }
}
