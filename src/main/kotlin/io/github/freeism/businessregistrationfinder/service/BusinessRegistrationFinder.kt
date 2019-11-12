package io.github.freeism.businessregistrationfinder.service

import io.github.freeism.businessregistrationfinder.model.BusinessRegistration
import io.github.freeism.businessregistrationfinder.service.external.HomeTaxApi
import org.springframework.stereotype.Component

/**
 * @author freeism
 * @since 2019/11/12
 */
@Component
class BusinessRegistrationFinder(val homeTaxApi: HomeTaxApi) {
    fun find(businessRegistrationNumber: String): BusinessRegistration {
        return homeTaxApi.request(BusinessRegistration(businessRegistrationNumber))
    }
}