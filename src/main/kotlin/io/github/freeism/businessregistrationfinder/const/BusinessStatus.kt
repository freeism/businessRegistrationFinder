package io.github.freeism.businessregistrationfinder.const

/**
 * @author freeism
 * @since 2019/11/12
 */
enum class BusinessStatus(val description: String) {
    GENERAL_TAX_PAYER("일반과세자"),
    SIMPLIFIED_TAX_PAYER("간이과세자"),
    TAX_FREE_BUSINESS("면세사업자"),
    CLOSED_TEMPORARILY("휴업자"),
    CLOSED("폐업자"),
    UNCHECKED("미확인"),
    ERROR("오류");
}