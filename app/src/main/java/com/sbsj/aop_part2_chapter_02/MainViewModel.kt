package com.sbsj.aop_part2_chapter_02

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val lottoRepository = LottoRepository()

    private val _lottoApiResponseLiveData = MutableLiveData<LottoApiResponse>()
    val lottoApiResponseLiveData: MutableLiveData<LottoApiResponse> get() = _lottoApiResponseLiveData

    fun getRecentWinningNumber(number: Int) = viewModelScope.launch {
        _lottoApiResponseLiveData.value = LottoApiResponse.loading()

        when (val result = lottoRepository.getRecentWinningNumber(number)) {
            is LottoRepository.Result.Success -> {
                _lottoApiResponseLiveData.value = LottoApiResponse.success(result.data)
            }
            is LottoRepository.Result.Error -> {
                _lottoApiResponseLiveData.value = LottoApiResponse.error(result.error)
            }
        }

    }

}