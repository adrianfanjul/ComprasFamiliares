package es.adrianfg.comprasfamiliares.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.ProjectStorageReference
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.StorageImages
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.impl.StorageImagesImpl
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.impl.ProjectStorageReferenceImpl


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindingStorageImages(storageImagesImpl: StorageImagesImpl): StorageImages

    @Binds
    abstract fun bindingStorageReference(storageReferenceImpl: ProjectStorageReferenceImpl): ProjectStorageReference

}