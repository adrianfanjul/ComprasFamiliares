package es.adrianfg.comprasfamiliares.core.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeController
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.impl.FirebaseRealtimeControllerImpl


@InstallIn(SingletonComponent::class)
@Module
abstract class FirebaseRealtimeModule {

    @Binds
    abstract fun bindingFirebaseRealtimeDAO(firebaseRealtimeDAOImpl: FirebaseRealtimeControllerImpl): FirebaseRealtimeController

}