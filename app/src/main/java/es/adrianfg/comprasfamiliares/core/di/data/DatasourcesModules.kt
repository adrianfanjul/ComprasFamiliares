package es.adrianfg.comprasfamiliares.core.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adrianfg.comprasfamiliares.data.datasource.LoginDatasource
import es.adrianfg.comprasfamiliares.data.datasource.impl.LoginDatasourceImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class DatasourcesModules {

    @Binds
    abstract fun bindingLoginDataSource(loginDatasourceImpl: LoginDatasourceImpl): LoginDatasource

}