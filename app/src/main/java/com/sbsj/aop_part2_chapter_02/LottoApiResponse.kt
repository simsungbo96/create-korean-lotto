package com.sbsj.aop_part2_chapter_02

data class LottoApiResponse(
    val status: Status,
    val data: LottoApiModel?,
    val error: String?
) {
    companion object {
        fun loading(): LottoApiResponse = LottoApiResponse(Status.LOADING, null, null)

        fun success(lotto: LottoApiModel): LottoApiResponse =
            LottoApiResponse(Status.SUCCESS, lotto, null)

        fun error(error: String): LottoApiResponse =
            LottoApiResponse(Status.ERROR, null, error)
    }

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}