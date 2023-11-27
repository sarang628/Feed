package com.sryang.torang.data1

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

/** 알림 리스트 데이터 */
data class AlarmListItem(
    val id: Int = 0,
    val user: AlarmUser? = null,
    val contents: String = "",
    val otherPictureUrl: String = "",
    val createdDate: String = "",
    val indexDate: String = "",
    val type: AlarmType? = null
) {
    fun toTextViewMessage(clickUser: ClickableSpan, clickPost: ClickableSpan): SpannableString {
        if (user == null)
            return SpannableString("")

        return when (type) {
            AlarmType.LIKE -> getLikeMessage(
                userName = user.name,
                clickUser = clickUser,
                clickPost = clickPost
            )

            AlarmType.REPLY -> getReplyMessage(
                userName = user.name,
                clickUser = clickUser,
                clickPost = clickPost
            )

            AlarmType.FOLLOW -> getFollowMessage(userName = user.name, clickUser = clickUser)
            else -> SpannableString("")
        }
    }

    fun getLikeMessage(
        userName: String,
        clickUser: ClickableSpan? = null,
        clickPost: ClickableSpan? = null
    ): SpannableString {
        val sb = StringBuffer()
        sb.append(userName)
        sb.append("님이 ")
        val startIndex = sb.length
        sb.append("이 포스트")
        val lastIndex = sb.length
        sb.append("를 좋아합니다.")
        var sp = SpannableString(sb.toString());
        sp.setSpan(clickUser, 0, userName.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        sp.setSpan(clickPost, startIndex, lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return sp
    }

    fun getReplyMessage(
        userName: String,
        clickUser: ClickableSpan? = null,
        clickPost: ClickableSpan? = null
    ): SpannableString {
        val sb = StringBuffer()
        sb.append(userName)
        sb.append("님이 ")
        val startIndex = sb.length
        sb.append("이 포스트")
        val lastIndex = sb.length
        sb.append("에 댓글을 달았습니다.")
        var sp = SpannableString(sb.toString());
        sp.setSpan(clickUser, 0, userName.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        sp.setSpan(clickPost, startIndex, lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return sp
    }

    fun getFollowMessage(
        userName: String,
        clickUser: ClickableSpan? = null
    ): SpannableString {
        val sb = StringBuffer()
        sb.append(userName)
        sb.append("님이")
        sb.append(" 팔로우 하였습니다.")
        var sp = SpannableString(sb.toString());
        sp.setSpan(clickUser, 0, userName.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return sp
    }

    fun transformDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val diff = Date(System.currentTimeMillis()).time - sdf.parse(createdDate).time
        val sb = StringBuilder()
        if (diff < TimeUnit.MINUTES.toMillis(1)) {
            return TimeUnit.MILLISECONDS.toSeconds(diff).toString() + "초 전"
        } else if (diff > TimeUnit.MINUTES.toMillis(1)
            && diff < TimeUnit.HOURS.toMillis(1)
        ) {
            return TimeUnit.MILLISECONDS.toMinutes(diff).toString() + "분 전"
        } else if (diff > TimeUnit.HOURS.toMillis(1)
            && diff < TimeUnit.DAYS.toMillis(1)
        ) {
            return TimeUnit.MILLISECONDS.toHours(diff).toString() + "시간 전"
        } else if (diff > TimeUnit.DAYS.toMillis(1)
            && diff < TimeUnit.DAYS.toMillis(7)
        ) {
            return TimeUnit.MILLISECONDS.toDays(diff).toString() + "일 전"
        }
        return (TimeUnit.MILLISECONDS.toDays(diff) / 7).toString() + "주 전"
    }
}