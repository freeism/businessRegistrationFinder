package io.github.freeism.businessregistrationfinder.service.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author freeism
 * @since 25/10/2019
 */
data class RequestDto(@JsonProperty(value = "txprDscmNo") val businessRegistrationNumber: String) {
    val inqrTrgtClCd: String = "1"
}