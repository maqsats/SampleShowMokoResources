package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.greenButtonNotFilled
import com.dna.payments.kmm.presentation.theme.greyColor
import com.dna.payments.kmm.presentation.theme.white
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun DnaTextField(
    textState: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().height(50.dp),
        value = textState,
        shape = RoundedCornerShape(8.dp),
        textStyle = DnaTextStyle.Normal16,
        placeholder = {
            Text(
                placeholder,
                style = DnaTextStyle.WithAlpha16
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = white,
            cursorColor = greyColor,
            disabledLabelColor = white,
            focusedIndicatorColor = greenButtonNotFilled,
            unfocusedIndicatorColor = greyColor
        ),
        onValueChange = onValueChange,
        singleLine = true,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
    )
}

@Composable
fun DNAEmailTextField() {
    var textState by remember { mutableStateOf("") }

    DnaTextField(
        textState = textState,
        onValueChange = { textState = it },
        placeholder = stringResource(MR.strings.email)
    )
}

@Composable
fun DNAPasswordTextField() {
    var textState by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    DnaTextField(
        textState = textState,
        onValueChange = { textState = it },
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