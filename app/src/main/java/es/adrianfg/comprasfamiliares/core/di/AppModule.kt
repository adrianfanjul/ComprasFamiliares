package es.adrianfg.comprasfamiliares.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.StorageImages
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.impl.StorageImagesImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Binds
    abstract fun bindingStorageImages(storageImagesImpl: StorageImagesImpl): StorageImages

}