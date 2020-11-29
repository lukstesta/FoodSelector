package br.com.estudiofalkor.foodselector.di

import br.com.estudiofalkor.business.businessModule
import br.com.estudiofalkor.foodselector.errorHandling.DefaultActivityLifecycleObserver
import br.com.estudiofalkor.provider.api.apiModule
import br.com.estudiofalkor.provider.api.configuration.endPointModule
import org.greenrobot.eventbus.EventBus
import org.koin.dsl.module
import provider.providerModule

val internalAppModule = module {
    single { EventBus.getDefault() }
}

val defaultLifecycleObserverFactoryModule = module {
    factory { DefaultActivityLifecycleObserver.Factory(get()) }
}

val appModule = listOf(
    internalAppModule,
    defaultLifecycleObserverFactoryModule,
    errorHandlingModule,
    viewModelModule,
    endPointModule,
    businessModule,
    providerModule,
    apiModule
)