package io.github.freeism.businessregistrationfinder.annotation

import io.github.freeism.businessregistrationfinder.configuration.WebConfig
import org.springframework.context.annotation.Import

/**
 * @author freeism
 * @since 2019/11/12
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(WebConfig::class)
annotation class BRNFinderEnabled