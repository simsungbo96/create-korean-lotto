package com.sbsj.aop_part2_chapter_02

import android.app.Activity
import android.content.Context
import android.widget.TextView
import android.widget.Toast

import androidx.core.app.ActivityCompat.finishAffinity
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
private var backKeyPressedTime : Long =0

 fun exitApp(context: Context , activity : Activity){
    if (System.currentTimeMillis() > backKeyPressedTime + 2500){
        backKeyPressedTime =System.currentTimeMillis()
        Toast.makeText(context, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        return
    }
    if (System.currentTimeMillis() <= backKeyPressedTime + 2500){
        finishAffinity(activity)
    }
}