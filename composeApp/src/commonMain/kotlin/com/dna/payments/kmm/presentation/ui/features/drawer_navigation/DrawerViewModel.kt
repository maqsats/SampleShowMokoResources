package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import cafe.adriel.voyager.core.model.screenModelScope
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
    BaseViewModel<DrawerScreenContract.Event, DrawerScreenContract.State, DrawerScreenContract.Effect>() {

    init {
        getMerchants()
    }

    override fun createInitialState(): DrawerScreenContract.State =
        DrawerScreenContract.State(
            navItems = emptyList(),
            settingsItems = emptyList(),
            merchants = ResourceUiState.Loading,
            merchantChange = ResourceUiState.Idle
        )

    override fun handleEvent(event: DrawerScreenContract.Event) {
        when (event) {
            is DrawerScreenContract.Event.OnMerchantChange -> {
                changeMerchant(event.data.merchantId)
            }
        }
    }

    private fun getMerchants() {
        screenModelScope.launch {
            val result = merchantUseCase()
            setState {
                copy(
                    merchants = when (result) {
                        is Response.Success -> {
                            if (result.data.isNotEmpty()) {
                                setEffect {
                                    DrawerScreenContract.Effect.OnMerchantSelected(result.data.first().name)
                                }
                            }
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
                    navItems = drawerUseCase.getNavItemList(),
                    settingsItems = drawerUseCase.getSettingsItems()
                )
            }
        }
    }

    private fun changeMerchant(merchantId: String) {
        screenModelScope.launch {
            setState { copy(merchantChange = ResourceUiState.Loading) }
            val result = authorizationUseCase.changeMerchant(merchantId)
            setState {
                copy(
                    merchantChange = when (result) {
                        is Response.Success -> {
                            setEffect { DrawerScreenContract.Effect.OnMerchantChange }
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