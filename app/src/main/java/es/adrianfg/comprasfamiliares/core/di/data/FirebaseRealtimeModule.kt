package es.adrianfg.comprasfamiliares.core.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeDAO.FirebaseRealtimeDAO
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeDAO.impl.FirebaseRealtimeDAOImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class FirebaseRealtimeModule {

    @Binds
    abstract fun bindingFirebaseRealtimeDAO(firebaseRealtimeDAOImpl: FirebaseRealtimeDAOImpl): FirebaseRealtimeDAO

}