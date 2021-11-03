package company.vk.education.lection07.modules

import company.vk.education.lection07.businesslayer.providers.IPlaceProvider
import company.vk.education.lection07.businesslayer.providers.TestPlaceProvider
import org.koin.dsl.module

val baseModule = module {
//    factory<IPlaceProvider> { TestPlaceProvider() }
}