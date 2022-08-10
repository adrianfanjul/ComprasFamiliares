package es.adrianfg.comprasfamiliares.core.di.presentation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adrianfg.comprasfamiliares.core.delegate.LoadingDelegate
import es.adrianfg.comprasfamiliares.core.delegate.LoadingDelegateViewModel
import es.adrianfg.comprasfamiliares.core.di.domain.di.IoDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PresentationModule {

    @Provides
    @Singleton
    fun provideEventsApp(@IoDispatcher workerScope: CoroutineScope): LoadingDelegateViewModel {
        return LoadingDelegate(workerScope)
    }
}
