package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.coroutineScope
import com.dna.payments.kmm.domain.interactors.use_cases.authorization.AuthorizationUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.drawer.DrawerUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.profile.MerchantUseCase
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.UiText
import kotlinx.coroutines.launch

class DrawerViewModel(
    private val merchantUseCase: MerchantUseCase,
    private val authorizationUseCase: AuthorizationUseCase,
    private val drawerUseCase: DrawerUseCase
) :
    BaseViewModel<DrawerViewContract.Event, DrawerViewContract.State, DrawerViewContract.Effect>() {

    init {
        getMerchants()
    }

    override fun createInitialState(): DrawerViewContract.State =
        DrawerViewContract.State(
            navItems = mutableStateOf(emptyList()),
            merchants = ResourceUiState.Loading,
            merchantChange = ResourceUiState.Idle
        )

    override fun handleEvent(event: DrawerViewContract.Event) {
        when (event) {
            is DrawerViewContract.Event.OnMerchantChange -> {
                changeMerchant(event.data.merchantId)
            }
        }
    }

    private fun getMerchants() {
        coroutineScope.launch {
            val result = merchantUseCase()
            setState {
                copy(
                    merchants = when (result) {
                        is Response.Success -> {
                            println("Response.Success")
                            ResourceUiState.Success(result.data)
                        }
                        is Response.Error -> {
                            ResourceUiState.Error(result.error)
                        }
                        is Response.NetworkError -> {
                            ResourceUiState.Error(UiText.DynamicString("Network error"))
                        }
                        is Response.TokenExpire -> {
                            ResourceUiState.Error(UiText.DynamicString("Token expired"))
                        }
                    },
                    navItems = mutableStateOf(drawerUseCase.getNavItemList())
                )
            }
        }
    }

    private fun changeMerchant(merchantId: String) {
        coroutineScope.launch {
            setState { copy(merchantChange = ResourceUiState.Loading) }
            val result = authorizationUseCase.changeMerchant(merchantId)
            setState {
                copy(
                    merchantChange = when (result) {
                        is Response.Success -> {
                            setEffect { DrawerViewContract.Effect.OnMerchantChange }
                            getMerchants()
                            ResourceUiState.Success(Unit)
                        }
                        is Response.Error -> {
                            ResourceUiState.Error(result.error)
                        }
                        is Response.NetworkError -> {
                            ResourceUiState.Error(UiText.DynamicString("Network error"))
                        }
                        is Response.TokenExpire -> {
                            ResourceUiState.Error(UiText.DynamicString("Token expired"))
                        }
                    }
                )
            }
        }
    }
}