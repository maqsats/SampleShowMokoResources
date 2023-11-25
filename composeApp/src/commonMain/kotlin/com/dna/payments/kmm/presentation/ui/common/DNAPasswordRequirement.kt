package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.new_password.PasswordRequirement
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.greenButtonNotFilled
import com.dna.payments.kmm.presentation.theme.red
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun DNAPasswordRequirement(
    modifier: Modifier = Modifier,
    passwordRequirement: PasswordRequirement
) {
    Row(
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(if (passwordRequirement.isValid) MR.images.ic_verified else MR.images.ic_not_verified),
            contentDescription = null,
            tint = if (passwordRequirement.isValid) greenButtonNotFilled else red
        )
        Text(
            text = stringResource(passwordRequirement.name),
            style = DnaTextStyle.WithAlpha14,
            modifier = Modifier.padding(vertical = 4.dp).padding(start = 4.dp)
        )
    }
}