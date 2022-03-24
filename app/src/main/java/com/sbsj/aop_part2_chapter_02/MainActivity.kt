package com.sbsj.aop_part2_chapter_02

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }

    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val pastRecordButton: Button by lazy {
        findViewById<Button>(R.id.pastButton)
    }

    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.tv_firstPic),
            findViewById<TextView>(R.id.tv_secondPic),
            findViewById<TextView>(R.id.tv_thirdPic),
            findViewById<TextView>(R.id.tv_fourthPic),
            findViewById<TextView>(R.id.tv_fifthPic),
            findViewById<TextView>(R.id.tv_sixthPic),
            findViewById<TextView>(R.id.tv_seventhPic)
        )
    }

    private var didRun = false
    private var playing = false
    private val pickNumberSet = hashSetOf<Int>()
    private lateinit var mContext: Context

    override fun onBackPressed() {
        exitApp(mContext,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        numberPicker.minValue = 1
        numberPicker.maxValue = 45





        val countDownTimer = object : CountDownTimer(3000, 100) {
            override fun onTick(p0: Long) {
                initRunButton()

            }

            override fun onFinish() {
                playing = false
            }
        }
        runButton.setOnClickListener {
            if (!playing) {
                countDownTimer.start()
                playing = true
            } else if (playing) {
                Toast.makeText(this, "...자동 생성 중 입니다... \n..잠시만 기다려주세요..", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }


        }
        initAddButton()
        initClearButton()
        initPastButton()
    }

    private fun initPastButton() {
        pastRecordButton.setOnClickListener {
            var intent = Intent(this, PastRecordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRunButton() {
        val list = getRandomNumber()

        list.forEachIndexed { index, number ->  /*이떄 number은 list[index]의 값을 뜻함.*/
            val textView = numberTextViewList[index]
            textView.text = number.toString()
            textView.isVisible = true
            setNumberBackground(number, textView, mContext)
            if (index == 6) {
                textView.text = number.toString()
                textView.isVisible = true
                textView.background =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.circle_pupple)

            }

        }


    }

    private fun initAddButton() {

        addButton.setOnClickListener {

            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if (pickNumberSet.size >= 6) {
                Toast.makeText(this, "번호는 6개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 숫자는 선택 할 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (playing) {
                Toast.makeText(this, "자동 생성중에 번호를 추가 할 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            setNumberBackground(numberPicker.value, textView, mContext)

            pickNumberSet.add(numberPicker.value)

        }


    }

    private fun getRandomNumber(): List<Int> {

        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    if (pickNumberSet.contains(i)) {
                        continue
                    }
                    this.add(i)
                }
            }
        numberList.shuffle()

        return (pickNumberSet.toList() + numberList.subList(
            0,
            6 - pickNumberSet.size
        )).sorted() + numberList[7]
    }

    private fun initClearButton() {

        clearButton.setOnClickListener {
            if (playing) {
                Toast.makeText(this, "자동 생성 중에 번호를 초기화 할 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false

            }
            didRun = false
        }


    }


}