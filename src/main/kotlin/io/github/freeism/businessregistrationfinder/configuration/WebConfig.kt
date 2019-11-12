package io.github.freeism.businessregistrationfinder.configuration

import io.github.freeism.businessregistrationfinder.BusinessRegistrationFinderApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

/**
 * @author freeism
 * @since 25/10/2019
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = [ BusinessRegistrationFinderApplication::class ])
class WebConfig