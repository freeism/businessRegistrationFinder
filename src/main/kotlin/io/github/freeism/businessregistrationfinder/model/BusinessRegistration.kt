package io.github.freeism.businessregistrationfinder.model

import io.github.freeism.businessregistrationfinder.const.BusinessStatus
import java.time.LocalDate

/**
 * @author freeism
 * @since 2019/11/12
 */
class BusinessRegistration(rawNumber: String) {
    val number: String
    var status: BusinessStatus = BusinessStatus.UNCHECKED
    var closedDate: LocalDate? = null

    init {
        val numberOnly = rawNumber.replace("-", "")

        require(numberOnly.length == 10) { "사업자번호의 형식이 맞지 않습니다 : $rawNumber" }

        this.number = numberOnly
    }

    constructor(number: String, status: BusinessStatus, closedDate: LocalDate?) : this(number) {
        this.status = status
        this.closedDate = closedDate
    }

    fun getNumberPrettier(): String {
        return number.substring(0, 3) + "-" + number.substring(3, 5) + "-" + number.substring(5, 10)
    }
}