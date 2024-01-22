package com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.first_step

import androidx.compose.runtime.mutableStateOf
import com.dna.payments.kmm.domain.interactors.validation.ValidateDomain
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

class AddDomainFirstStepViewModel(
    private val validateDomain: ValidateDomain,
) : BaseViewModel<AddDomainFirstStepContract.Event, AddDomainFirstStepContract.State, AddDomainFirstStepContract.Effect>() {

    override fun createInitialState(): AddDomainFirstStepContract.State =
        AddDomainFirstStepContract.State(
            domain = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.DOMAIN,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(AddDomainFirstStepContract.Event.OnDomainFieldChanged) }
            ),
            isNextEnabled = mutableStateOf(false)
        )

    override fun handleEvent(event: AddDomainFirstStepContract.Event) {
        when(event) {
            AddDomainFirstStepContract.Event.OnDomainFieldChanged -> {
                with(currentState) {
                    val validateDomainResult = validateDomain(domain.input.value, domain.textInput)
                    domain.validationResult.value = validateDomainResult
                    isNextEnabled.value = validateDomainResult.successful
                }
            }
        }
    }
}
