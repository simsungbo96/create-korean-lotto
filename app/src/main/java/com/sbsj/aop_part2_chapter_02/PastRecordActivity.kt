package com.sbsj.aop_part2_chapter_02

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.sbsj.aop_part2_chapter_02.databinding.ActivityPastRecordBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class PastRecordActivity : AppCompatActivity() {


    private lateinit var mContext : Context

    private val mainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var _binding: ActivityPastRecordBinding? = null
    private val binding get() = _binding!!

    override fun onBackPressed() {
        exitApp(mContext,this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext =this
        _binding = ActivityPastRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dec = DecimalFormat("#,###원")

        binding.exitButton.setOnClickListener {
            finish()
        }
        binding.searchButton.setOnClickListener {
            if(binding.etPrNumber.text.toString().toInt() > recentDate() || binding.etPrNumber.text.toString() == "0"){
                Toast.makeText(this,"아직 진행되지 않은 회차입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mainViewModel.getRecentWinningNumber(binding.etPrNumber.text.toString().toInt())
        }
        binding.etPrNumber.setText(recentDate().toString())
        mainViewModel.getRecentWinningNumber(recentDate())
        mainViewModel.lottoApiResponseLiveData.observe(this) { response ->
            when (response.status) {
                LottoApiResponse.Status.LOADING -> {
                }
                LottoApiResponse.Status.SUCCESS -> {
                    response.data?.let { lotto ->
                        binding.recordDate.text = lotto.drwNoDate
                        binding.tvNumber1.text = lotto.drwtNo1.toString()
                        binding.tvNumber2.text = lotto.drwtNo2.toString()
                        binding.tvNumber3.text = lotto.drwtNo3.toString()
                        binding.tvNumber4.text = lotto.drwtNo4.toString()
                        binding.tvNumber5.text = lotto.drwtNo5.toString()
                        binding.tvNumber6.text = lotto.drwtNo6.toString()
                        binding.tvNumber7.text = lotto.bnusNo.toString()
                        binding.firstPrize.text = dec.format(lotto.firstWinamnt).toString()
                        binding.peopleNumber.text = lotto.firstPrzwnerCo.toString()+"명"
                        setNumberBackground(lotto.drwtNo1,binding.tvNumber1,mContext)
                        setNumberBackground(lotto.drwtNo2,binding.tvNumber2,mContext)
                        setNumberBackground(lotto.drwtNo3,binding.tvNumber3,mContext)
                        setNumberBackground(lotto.drwtNo4,binding.tvNumber4,mContext)
                        setNumberBackground(lotto.drwtNo5,binding.tvNumber5,mContext)
                        setNumberBackground(lotto.drwtNo6,binding.tvNumber6,mContext)
                        binding.tvNumber7.background = ContextCompat.getDrawable(mContext,R.drawable.circle_pupple)
                    }
                }
                LottoApiResponse.Status.ERROR -> {
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun recentDate(): Int {
        val startDate = "2002-12-07 23:59:59"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val cDate = Date()
        val sDate: Date = dateFormat.parse(startDate)
        val diff: Long = cDate.time - sDate.time
        val nextEpi = diff / (86400 * 1000 * 7) + 2
        Log.d("최근", "getNextEpisodeBasedonDate: ${ nextEpi.toInt()} ")
        return nextEpi.toInt() -1
    }


}