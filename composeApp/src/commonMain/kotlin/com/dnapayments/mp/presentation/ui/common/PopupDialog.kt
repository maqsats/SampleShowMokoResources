package com.dnapayments.mp.presentation.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.dnapayments.mp.MR
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

@Composable
fun ChangeTerminalSettingDialog(
    isEnabled: Boolean = false,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        title = null,
        text = {
            DNAText(
                text = stringResource(
                    if (isEnabled) MR.strings.disable_to_payment_method
                    else MR.strings.enable_to_payment_method
                )
            )
        },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                DNAText(text = stringResource(MR.strings.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                DNAText(text = stringResource(MR.strings.yes))
            }
        },
    )
}