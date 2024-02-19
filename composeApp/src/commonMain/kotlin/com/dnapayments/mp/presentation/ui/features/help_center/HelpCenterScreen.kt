package com.dnapayments.mp.presentation.ui.features.help_center

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.dnaGreenLight
import com.dnapayments.mp.presentation.ui.common.DNAGreenBackButton
import com.dnapayments.mp.utils.constants.Constants.HELP_CENTER_URL
import com.dnapayments.mp.utils.navigation.LocalNavigator
import com.dnapayments.mp.utils.navigation.currentOrThrow
import com.dnapayments.mp.utils.webview.web.LoadingState
import com.dnapayments.mp.utils.webview.web.WebView
import com.dnapayments.mp.utils.webview.web.rememberWebViewNavigator
import com.dnapayments.mp.utils.webview.web.rememberWebViewState
import dev.icerock.moko.resources.compose.stringResource

class HelpCenterScreen : Screen {

    @Composable
    override fun Content() {

        val state = rememberWebViewState(
            url = HELP_CENTER_URL
        )

        val webViewNavigator = rememberWebViewNavigator()
        val navigator = LocalNavigator.currentOrThrow
        val loadingState = state.loadingState

        state.webSettings.apply {
            isJavaScriptEnabled = true
            customUserAgentString =
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_1) AppleWebKit/625.20 (KHTML, like Gecko) Version/14.3.43 Safari/625.20"
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(Paddings.medium))
            DNAGreenBackButton(
                text = stringResource(MR.strings.back),
                onClick = { navigator.pop() },
                modifier = Modifier.padding(horizontal = Paddings.small)
            )

            if (loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    color = dnaGreenLight,
                    progress = loadingState.progress,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            WebView(
                state = state,
                modifier = Modifier
                    .weight(1f).fillMaxWidth(),
                captureBackPresses = true,
                navigator = webViewNavigator,
            )
        }
    }
}
