package io.github.freeism.businessregistrationfinder.annotation

import io.github.freeism.businessregistrationfinder.configuration.BRNFinderConfig
import org.springframework.context.annotation.Import

/**
 * @author freeism
 * @since 2019/11/12
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(BRNFinderConfig::class)
annotation class BRNFinderEnabled