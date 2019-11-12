package io.github.freeism.businessregistrationfinder.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * @author freeism
 * @since 2019/11/12
 */
@Aspect
@Component
class RequestDelayer {
    var lastRequestedAt: LocalDateTime = LocalDateTime.now()

    /**
     * 너무 잦은 요청은 hometax에서 거부하게 된다 (아마 5초?)
     */
    @Around("execution(* io.github.freeism.businessregistrationfinder.service.external.HomeTaxApi.*(..))")
    fun delay(jp: ProceedingJoinPoint): Any {
        while (lastRequestedAt.plusSeconds(6L) > LocalDateTime.now()) {
            Thread.sleep(1000L)
        }

        val result = jp.proceed()

        this.lastRequestedAt = LocalDateTime.now()

        return result
    }
}