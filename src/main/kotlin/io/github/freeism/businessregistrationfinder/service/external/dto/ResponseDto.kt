package io.github.freeism.businessregistrationfinder.service.external.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.github.freeism.businessregistrationfinder.const.BusinessStatus
import java.time.LocalDate

/**
 * @author freeism
 * @since 25/10/2019
 */
data class ResponseDto(
        @JsonProperty(value = "smpcBmanTrtCntn") val message: String,
        @JsonProperty(value = "trtCntn") val messageDetail: String) {

    fun isActive(): Boolean {
        return this.message.startsWith("등록되어 있는 사업자등록번호 입니다.")
    }

    fun getBusinessStatus(): BusinessStatus {
        when {
            isActive() && this.messageDetail.contains("일반과세자") -> return BusinessStatus.GENERAL_TAX_PAYER
            isActive() && this.messageDetail.contains("간이과세자") -> return BusinessStatus.SIMPLIFIED_TAX_PAYER
            isActive() && this.messageDetail.contains("면세사업자") -> return BusinessStatus.TAX_FREE_BUSINESS

            this.messageDetail.contains("폐업자") -> return BusinessStatus.CLOSED
            this.messageDetail.contains("휴업자") -> return BusinessStatus.CLOSED_TEMPORARILY
        }

        return BusinessStatus.ERROR
    }

    fun getClosedDate(): LocalDate? {
        val regex = "폐업일자:(\\d{4}-\\d{2}-\\d{2})".toRegex()

        regex.find(this.messageDetail)?.groups?.get(1)?.value?.let {
            return LocalDate.parse(it)
        }

        return null
    }

    companion object {
        fun emptyObject(): ResponseDto {
            return ResponseDto("오류입니다", "오류입니다")
        }
    }
}
