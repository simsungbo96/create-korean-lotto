package com.sbsj.aop_part2_chapter_02

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoApi {

    @GET("/common.do")
    suspend fun getRecentWinningNumber(
        @Query("drwNo") drwNum: Int,
        @Query("method") method: String = "getLottoNumber"
    ): Response<LottoApiModel>

}