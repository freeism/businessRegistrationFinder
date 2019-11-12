package io.github.freeism.businessregistrationfinder.service.external

import io.github.freeism.businessregistrationfinder.const.BusinessStatus
import io.github.freeism.businessregistrationfinder.model.BusinessRegistration
import io.github.freeism.businessregistrationfinder.service.external.dto.RequestDto
import io.github.freeism.businessregistrationfinder.service.external.dto.ResponseDto
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

/**
 * @author freeism
 * @since 25/10/2019
 */
@ActiveProfiles("test")
@SpringBootTest
internal class HomeTaxApiTest(@Autowired val homeTaxApi: HomeTaxApi) {
    @Nested
    inner class RequestByDto {
        @Test
        fun `일반과세사업자`() {
            homeTaxApi.request(RequestDto("1208800767"))
                    .verifyResult(true, BusinessStatus.GENERAL_TAX_PAYER, null)
        }

        @Test
        fun `정상사업자 하이픈`() {
            homeTaxApi.request(RequestDto("120-88-00767"))
                    .verifyResult(false, BusinessStatus.ERROR, null)
        }

        @Test
        fun `비정상사업자`() {
            homeTaxApi.request(RequestDto("123123"))
                    .verifyResult(false, BusinessStatus.ERROR, null)
        }

        @Test
        fun `폐업사업자`() {
            homeTaxApi.request(RequestDto("1231212312"))
                    .verifyResult(false, BusinessStatus.CLOSED, LocalDate.parse("1986-09-30"))
        }

        @Test
        fun `간이사업자`() {
            homeTaxApi.request(RequestDto("4145200474"))
                    .verifyResult(true, BusinessStatus.SIMPLIFIED_TAX_PAYER, null)
        }

        @Test
        fun `면세사업자`() {
            homeTaxApi.request(RequestDto("6799500710"))
                    .verifyResult(true, BusinessStatus.TAX_FREE_BUSINESS, null)
        }

        @Test
        fun `면세사업자-폐업`() {  // 폐업자가 우선
            homeTaxApi.request(RequestDto("1079012341"))
                    .verifyResult(false, BusinessStatus.CLOSED, LocalDate.parse("1998-12-31"))
        }

        @Test
        fun `휴업사업자`() {
            homeTaxApi.request(RequestDto("5821100435"))
                    .verifyResult(false, BusinessStatus.CLOSED_TEMPORARILY, null)
        }
    }

    @Nested
    inner class RequestByBusinessRegistration {
        @Test
        fun `일반과세사업자`() {
            homeTaxApi.request(BusinessRegistration("1208800767"))
                    .verifyResult("1208800767", BusinessStatus.GENERAL_TAX_PAYER, null)
        }

        @Test
        fun `정상사업자 하이픈`() {
            homeTaxApi.request(BusinessRegistration("120-88-00767"))
                    .verifyResult("1208800767", BusinessStatus.GENERAL_TAX_PAYER, null)
        }

        @Test
        fun `비정상사업자`() {
            assertThatThrownBy { homeTaxApi.request(BusinessRegistration("123123")) }
                    .isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `폐업사업자`() {
            homeTaxApi.request(BusinessRegistration("1231212312"))
                    .verifyResult("1231212312", BusinessStatus.CLOSED, LocalDate.parse("1986-09-30"))
        }

        @Test
        fun `간이사업자`() {
            homeTaxApi.request(BusinessRegistration("4145200474"))
                    .verifyResult("4145200474", BusinessStatus.SIMPLIFIED_TAX_PAYER, null)
        }

        @Test
        fun `면세사업자`() {
            homeTaxApi.request(BusinessRegistration("6799500710"))
                    .verifyResult("6799500710", BusinessStatus.TAX_FREE_BUSINESS, null)
        }

        @Test
        fun `면세사업자-폐업`() {  // 폐업자가 우선
            homeTaxApi.request(BusinessRegistration("1079012341"))
                    .verifyResult("1079012341", BusinessStatus.CLOSED, LocalDate.parse("1998-12-31"))
        }

        @Test
        fun `휴업사업자`() {
            homeTaxApi.request(BusinessRegistration("5821100435"))
                    .verifyResult("5821100435", BusinessStatus.CLOSED_TEMPORARILY, null)
        }
    }
}

private fun ResponseDto.verifyResult(activeExpected: Boolean, businessStatusExpected: BusinessStatus, closedDateExpected: LocalDate?) {
    assertAll(
            { assertThat(this.isActive()).isEqualTo(activeExpected) },
            { assertThat(this.getBusinessStatus()).isEqualTo(businessStatusExpected) },
            { assertThat(this.getClosedDate()).isEqualTo(closedDateExpected) }
    )
}

private fun BusinessRegistration.verifyResult(numberExpected: String, businessStatusExpected: BusinessStatus, closedDateExpected: LocalDate?) {
    assertAll(
            { assertThat(this.number).isEqualTo(numberExpected) },
            { assertThat(this.status).isEqualTo(businessStatusExpected) },
            { assertThat(this.closedDate).isEqualTo(closedDateExpected) }
    )
}