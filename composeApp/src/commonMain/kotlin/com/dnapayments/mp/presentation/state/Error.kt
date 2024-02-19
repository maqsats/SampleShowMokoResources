package com.dnapayments.mp.presentation.state

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Error(
    modifier: Modifier = Modifier,
    msg: String,
    onTryAgain: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = msg,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = onTryAgain
                ),
                text = "Try Again",
                style = MaterialTheme.typography.h4,
            )
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}