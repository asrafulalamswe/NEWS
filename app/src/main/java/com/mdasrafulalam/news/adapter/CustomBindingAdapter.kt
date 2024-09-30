package com.mdasrafulalam.news.adapter

import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mdasrafulalam.news.R
import java.sql.Time
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("app:setImageResources")
fun setImageResources(imageView: ImageView, imgUrl: String) {
    imgUrl.let {
        Glide.with(imageView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.downloading)
            )
            .error(R.drawable.broken_image)
            .into(imageView)
    }
}

@BindingAdapter("app:setBookMarkedIcon")
fun setBookMarkedIcon(imageView: ImageView, bookmarked: Boolean) {
    if (bookmarked) {
        imageView.setImageResource(R.drawable.bookmark)
    } else {
        imageView.setImageResource(R.drawable.bookmark_grey)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("app:dateTime")
fun setDateTime(textView: TextView, datetime: String) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val dt = LocalDateTime.parse(datetime, formatter)
    val calendar = Calendar.getInstance()
    calendar.set(dt.year, dt.monthValue, dt.dayOfMonth, dt.hour, dt.minute)
    val today = LocalDateTime.now()
    val todayMonth = today.monthValue
    val todayDay = today.dayOfMonth
    val todayYear = today.year
    val fromDay = dt.dayOfMonth
    var fromMonth = dt.monthValue
    var fromYear = dt.year
    val hourDelay = today.hour - dt.hour
    val dayDelay: Int
    var monthDelay = todayMonth - fromMonth
    var yearDelay = todayYear - fromYear
    val start = Time(today.hour, today.minute, today.second)
    val stop = Time(dt.hour, dt.minute, dt.second)
    val diff: Time
    if (todayDay < fromDay) {
        dayDelay = (todayDay + 30) - fromDay
        fromMonth += 1
        if (todayMonth < fromMonth) {
            monthDelay = (todayMonth + 12) - fromMonth
            fromYear += 1
        } else {
            monthDelay = todayMonth - fromMonth
            yearDelay = todayYear - fromYear
        }
    } else {
        dayDelay = todayDay - fromDay
        monthDelay = todayMonth - fromMonth
        yearDelay = todayYear - fromYear
    }
    if (dayDelay > 0) {
        textView.text =
            String.format("Published at: " + dt.dayOfMonth + "/" + dt.monthValue + "/" + dt.year)
    } else {
        diff = difference(start, stop)
        var hours = diff.hours
        if (hours < 0.0) {
            hours = hours * -1
        }
        textView.text = String.format("Published $hours hours ago")
    }
    Log.d("checktime", "delay: $hourDelay, $hourDelay")
}

fun difference(start: Time, stop: Time): Time {
    val diff = Time(0, 0, 0)
    if (stop.seconds > start.seconds) {
        --start.minutes
        start.seconds += 60
    }
    diff.seconds = start.seconds - stop.seconds
    if (stop.minutes > start.minutes) {
        --start.hours
        start.minutes += 60
    }
    diff.minutes = start.minutes - stop.minutes
    diff.hours = start.hours - stop.hours
    return diff
}