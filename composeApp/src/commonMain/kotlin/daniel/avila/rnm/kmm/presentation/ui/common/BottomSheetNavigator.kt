package daniel.avila.rnm.kmm.presentation.ui.common

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.Stack
import daniel.avila.rnm.kmm.utils.navigation.CurrentScreen
import daniel.avila.rnm.kmm.utils.navigation.Navigator
import daniel.avila.rnm.kmm.utils.navigation.compositionUniqueId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

public typealias BottomSheetNavigatorContent = @Composable (bottomSheetNavigator: BottomSheetNavigator) -> Unit

public val LocalBottomSheetNavigator: ProvidableCompositionLocal<BottomSheetNavigator> =
    staticCompositionLocalOf { error("BottomSheetNavigator not initialized") }

@OptIn(InternalVoyagerApi::class)
@ExperimentalMaterialApi
@Composable
public fun BottomSheetNavigator(
    modifier: Modifier = Modifier,
    hideOnBackPress: Boolean = true,
    scrimColor: Color = ModalBottomSheetDefaults.scrimColor,
    sheetShape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetElevation: Dp = 26.dp,
    sheetBackgroundColor: Color = Color.White,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    sheetGesturesEnabled: Boolean = true,
    skipHalfExpanded: Boolean = true,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    key: String = compositionUniqueId(),
    sheetContent: BottomSheetNavigatorContent = { CurrentScreen() },
    content: BottomSheetNavigatorContent
) {
    var showElevation by remember { mutableStateOf(true) }
    var hideBottomSheet: (() -> Unit)? = null
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { state ->
            showElevation = state != ModalBottomSheetValue.Hidden
            if (state == ModalBottomSheetValue.Hidden) hideBottomSheet?.invoke()
            true
        },
        skipHalfExpanded = skipHalfExpanded,
        animationSpec = animationSpec
    )

    Navigator(HiddenBottomSheetScreen, onBackPressed = null, key = key) { navigator ->
        val bottomSheetNavigator = remember(navigator, sheetState, coroutineScope) {
            BottomSheetNavigator(navigator, sheetState, coroutineScope)
        }

        hideBottomSheet = bottomSheetNavigator::hide

        CompositionLocalProvider(LocalBottomSheetNavigator provides bottomSheetNavigator) {
            ModalBottomSheetLayout(
                modifier = modifier,
                sheetState = sheetState,
                sheetShape = sheetShape,
                scrimColor = scrimColor,
                sheetElevation = if (showElevation) sheetElevation else 0.dp,
                sheetBackgroundColor = sheetBackgroundColor,
                sheetContentColor = sheetContentColor,
                sheetGesturesEnabled = sheetGesturesEnabled,
                sheetContent = {
                    BottomSheetNavigatorBackHandler(
                        bottomSheetNavigator,
                        sheetState,
                        hideOnBackPress
                    )
                    sheetContent(bottomSheetNavigator)
                },
                content = {
                    content(bottomSheetNavigator)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
public class BottomSheetNavigator internal constructor(
    private val navigator: Navigator,
    private val sheetState: ModalBottomSheetState,
    private val coroutineScope: CoroutineScope
) : Stack<Screen> by navigator {

    public val isVisible: Boolean
        get() = sheetState.isVisible

    public fun show(screen: Screen) {
        coroutineScope.launch {
            replaceAll(screen)
            sheetState.show()
        }
    }

    public fun hide() {
        coroutineScope.launch {
            if (isVisible) {
                sheetState.hide()
                replaceAll(HiddenBottomSheetScreen)
            }
        }
    }

    @Composable
    public fun saveableState(
        key: String,
        content: @Composable () -> Unit
    ) {
        navigator.saveableState(key, content = content)
    }
}

private object HiddenBottomSheetScreen : Screen {

    @Composable
    override fun Content() {
        Spacer(modifier = Modifier.height(0.dp))
    }
}