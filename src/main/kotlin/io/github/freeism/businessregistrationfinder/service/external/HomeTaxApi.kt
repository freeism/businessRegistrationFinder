package io.github.freeism.businessregistrationfinder.service.external

import io.github.freeism.businessregistrationfinder.model.BusinessRegistration
import io.github.freeism.businessregistrationfinder.service.external.dto.RequestDto
import io.github.freeism.businessregistrationfinder.service.external.dto.ResponseDto
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

/**
 * @author freeism
 * @since 25/10/2019
 */
@Component
class HomeTaxApi {
    val restTemplate = RestTemplate()
    val uri = URI("https://teht.hometax.go.kr/wqAction.do?actionId=ATTABZAA001R08&screenId=UTEABAAA13&popupYn=false&realScreenId=")

    fun request(businessRegistration: BusinessRegistration): BusinessRegistration {
        val responseDto = request(RequestDto(businessRegistration.number))

        return BusinessRegistration(businessRegistration.number, responseDto.getBusinessStatus(), responseDto.getClosedDate())
    }

    internal fun request(requestDto: RequestDto): ResponseDto {
        return restTemplate.postForObject(uri, requestDto, ResponseDto::class.java) ?: ResponseDto.emptyObject()
    }
}