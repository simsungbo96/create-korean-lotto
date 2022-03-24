package com.sbsj.aop_part2_chapter_02

class LottoRepository {
    suspend fun getRecentWinningNumber(number: Int): Result<LottoApiModel> {
        val response = RetrofitInstance.api.getRecentWinningNumber(number)
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.Success(it)
            }
        }
        return Result.Error("error")
    }

    sealed class Result<out T : Any> {
        data class Success<out T : Any>(val data: T) : Result<T>()
        data class Error(val error: String) : Result<Nothing>()
    }
}