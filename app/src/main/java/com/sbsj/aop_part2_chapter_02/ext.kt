package com.sbsj.aop_part2_chapter_02

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat

fun setNumberBackground(number:Int,textView: TextView ,context :Context){
    when(number){
        in 1..10 -> textView.background = ContextCompat.getDrawable(context,R.drawable.circle_yellow)
        in 11..20 -> textView.background = ContextCompat.getDrawable(context,R.drawable.circle_blue)
        in 21..30 -> textView.background = ContextCompat.getDrawable(context,R.drawable.circle_red)
        in 31..40 -> textView.background = ContextCompat.getDrawable(context,R.drawable.circle_green)
        else -> textView.background = ContextCompat.getDrawable(context,R.drawable.circle_gray)
    }

}