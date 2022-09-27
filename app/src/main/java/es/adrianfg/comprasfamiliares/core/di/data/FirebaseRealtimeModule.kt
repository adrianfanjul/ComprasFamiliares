package es.adrianfg.comprasfamiliares.core.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerGroup
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerProduct
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerUser
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.impl.FirebaseRealtimeControllerGroupImpl
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.impl.FirebaseRealtimeControllerProductImpl
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.impl.FirebaseRealtimeControllerUserImpl


@InstallIn(SingletonComponent::class)
@Module
abstract class FirebaseRealtimeModule {

    @Binds
    abstract fun bindingFirebaseRealtimeControllerUser(firebaseRealtimeControllerUserImpl: FirebaseRealtimeControllerUserImpl): FirebaseRealtimeControllerUser

    @Binds
    abstract fun bindingFirebaseRealtimeControllerGroupImpl(firebaseRealtimeControllerGroupImpl: FirebaseRealtimeControllerGroupImpl): FirebaseRealtimeControllerGroup

    @Binds
    abstract fun bindingFirebaseRealtimeControllerProduct(firebaseRealtimeControllerProductImpl: FirebaseRealtimeControllerProductImpl): FirebaseRealtimeControllerProduct
}