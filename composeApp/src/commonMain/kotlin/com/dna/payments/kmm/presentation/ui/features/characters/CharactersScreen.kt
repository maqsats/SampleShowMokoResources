package com.dna.payments.kmm.presentation.ui.features.characters

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.presentation.ui.common.ActionBarIcon
import com.dna.payments.kmm.presentation.ui.common.CharactersList
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.ui.features.character_detail.CharacterDetailScreen
import com.dna.payments.kmm.presentation.ui.features.characters_favorites.CharactersFavoritesScreen
import kotlinx.coroutines.flow.collectLatest

class CharactersScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val charactersViewModel = getScreenModel<CharactersViewModel>()

        val state by charactersViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            charactersViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is CharactersContract.Effect.NavigateToDetailCharacter ->
                        navigator.push(CharacterDetailScreen(effect.idCharacter))

                    CharactersContract.Effect.NavigateToFavorites ->
                        navigator.push(CharactersFavoritesScreen())
                }
            }
        }

        Scaffold(
            topBar = { ActionAppBar { charactersViewModel.setEvent(CharactersContract.Event.OnFavoritesClick) } }
        ) { padding ->
            ManagementResourceUiState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                resourceUiState = state.characters,
                successView = { characters ->
                    CharactersList(
                        characters = characters,
                        onCharacterClick = { idCharacter ->
                            charactersViewModel.setEvent(
                                CharactersContract.Event.OnCharacterClick(
                                    idCharacter
                                )
                            )
                        }
                    )
                },
                onTryAgain = { charactersViewModel.setEvent(CharactersContract.Event.OnTryCheckAgainClick) },
                onCheckAgain = { charactersViewModel.setEvent(CharactersContract.Event.OnTryCheckAgainClick) },
            )
        }
    }
}

@Composable
fun ActionAppBar(
    onClickFavorite: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "DNA Payments KMP") },
        actions = {
            ActionBarIcon(
                onClick = onClickFavorite,
                icon = Icons.Filled.Favorite
            )
        }
    )
}


