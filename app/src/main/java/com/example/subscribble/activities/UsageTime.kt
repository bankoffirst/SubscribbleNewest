package com.example.subscribble.activities


import android.app.AlertDialog
import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.Calendar



val nameYou = "Youtube"
val nameDis = "DisneyPlus"
val nameNet = "Netflix"
fun getUsageStatsForWeeks(context: Context, packageName: String): List<Float> {
    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val calendar = Calendar.getInstance()
    val dataPoints = mutableListOf<Float>()
    val currentMonth = calendar.get(Calendar.MONTH)

    for (weekIndex in 0 until 4) {
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        calendar.add(Calendar.WEEK_OF_MONTH, weekIndex)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_WEEK, 6)

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endTime = calendar.timeInMillis

        val appUsageData = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )
        var weeklyUsage = 0f

        for (usageStats in appUsageData) {
            if (usageStats.packageName == packageName) {
                weeklyUsage += usageStats.totalTimeInForeground / (1000 * 60).toFloat()
            }
        }
        dataPoints.add(weeklyUsage)
    }
    return dataPoints.reversed()
}
fun ShowsaveData(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Save Data")
        .setMessage("Save/update Data Completion")
        .setPositiveButton("OK") { dialog, which ->
        }
        .show()
}