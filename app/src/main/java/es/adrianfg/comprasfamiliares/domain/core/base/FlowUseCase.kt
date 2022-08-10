package es.adrianfg.comprasfamiliares.domain.core.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


abstract class FlowUseCase<in P, R>(private val dispacher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<R> {
        return execute(parameters).flowOn(dispacher)
    }

    abstract fun execute(params: P): Flow<R>
}