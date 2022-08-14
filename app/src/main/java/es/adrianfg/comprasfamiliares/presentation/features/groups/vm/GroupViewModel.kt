package es.adrianfg.comprasfamiliares.presentation.features.groups.vm

import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel

import es.adrianfg.comprasfamiliares.domain.usecase.GetLogInUseCase
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getLogInUseCase: GetLogInUseCase,
) : BaseViewModel() {



}