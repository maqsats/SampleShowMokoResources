package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.greyColor
import com.dna.payments.kmm.presentation.theme.poppinsFontFamily
import com.dna.payments.kmm.presentation.theme.red
import com.dna.payments.kmm.presentation.theme.white
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun DnaTextField(
    textState: TextFieldUiState,
    placeholder: String,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = textState.input.value,
        onValueChange = {
            textState.input.value = it
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        textStyle = DnaTextStyle.Normal16.copy(
            fontFamily = poppinsFontFamily()
        ),
        supportingText = {
            if (!textState.validationResult.value.successful) {
                println(textState.validationResult.value.errorMessage?.getText() ?: "")
                DNAText(
                    textState.validationResult.value.errorMessage?.getText() ?: "",
                    style = DnaTextStyle.Red16
                )
            }
        },
        isError = !textState.validationResult.value.successful,
        placeholder = {
            DNAText(
                placeholder,
                style = DnaTextStyle.WithAlpha16
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = greyColor,
            disabledLabelColor = white,
            focusedContainerColor = white,
            errorTextColor = red
        ),
        singleLine = true,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
    )
}

@Composable
fun DNAEmailTextField(textState: TextFieldUiState) {
    DnaTextField(
        textState = textState,
        placeholder = stringResource(MR.strings.email)
    )
}

@Composable
fun DNAPasswordTextField(textState: TextFieldUiState) {
    var passwordVisibility by remember { mutableStateOf(false) }

    DnaTextField(
        textState = textState,
        placeholder = stringResource(MR.strings.password),
        trailingIcon = {
            Icon(
                painter = painterResource(MR.images.ic_visibility),
                contentDescription = null,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = { passwordVisibility = !passwordVisibility }
                )
            )
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}