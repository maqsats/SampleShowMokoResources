package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import androidx.compose.runtime.mutableStateOf
import com.dna.payments.kmm.domain.interactors.use_cases.drawer.DrawerUseCase
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

class DrawerNavigationViewModel(drawerUseCase: DrawerUseCase) :
    BaseViewModel<DrawerNavigationContract.Event, DrawerNavigationContract.State, DrawerNavigationContract.Effect>() {

    init {
        currentState.navItems.value = drawerUseCase.getNavItemList()
    }

    override fun createInitialState(): DrawerNavigationContract.State =
        DrawerNavigationContract.State(
            navItems = mutableStateOf(emptyList())
        )


    override fun handleEvent(event: DrawerNavigationContract.Event) {

    }
}