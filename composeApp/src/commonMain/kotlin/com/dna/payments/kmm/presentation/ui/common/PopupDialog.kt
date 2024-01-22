package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        title = { DNAText(text = stringResource(MR.strings.settings_logout_dialog_title)) },
        text = { DNAText(text = stringResource(MR.strings.settings_logout_dialog_body)) },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                DNAText(text = stringResource(MR.strings.settings_logout_dialog_dismiss))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                DNAText(text = stringResource(MR.strings.settings_logout_dialog_confirm))
            }
        },
    )
}


@Composable
fun UnregisterDomainDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        title = { DNAText(text = stringResource(MR.strings.delete_domain)) },
        text = { DNAText(text = stringResource(MR.strings.delete_domain_description)) },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                DNAText(text = stringResource(MR.strings.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                DNAText(text = stringResource(MR.strings.delete))
            }
        },
    )
}