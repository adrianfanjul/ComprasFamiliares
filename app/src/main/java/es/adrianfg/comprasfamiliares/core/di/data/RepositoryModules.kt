package es.adrianfg.comprasfamiliares.core.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adrianfg.comprasfamiliares.data.repository.GroupsRepositoryImpl
import es.adrianfg.comprasfamiliares.data.repository.LoginRepositoryImpl
import es.adrianfg.comprasfamiliares.domain.repository.GroupsRepository
import es.adrianfg.comprasfamiliares.domain.repository.LoginRepository

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModules {
    @Binds
    abstract fun bindingLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindingGroupsRepository(groupsRepositoryImpl: GroupsRepositoryImpl): GroupsRepository

}