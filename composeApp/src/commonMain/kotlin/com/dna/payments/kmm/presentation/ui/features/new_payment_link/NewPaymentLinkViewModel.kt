package com.dna.payments.kmm.presentation.ui.features.new_payment_link

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.data.model.create_new_link.CreateNewLinkRequest
import com.dna.payments.kmm.data.model.create_new_link.Periodic
import com.dna.payments.kmm.domain.interactors.use_cases.currency.CurrencyUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.ecom_stores.GetEcomStoresUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.stores.StoresUseCase
import com.dna.payments.kmm.domain.interactors.validation.ValidateField
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.online_payments.TransactionType
import com.dna.payments.kmm.domain.model.stores.CardSettings
import com.dna.payments.kmm.domain.network.Response.Companion.onSuccess
import com.dna.payments.kmm.domain.network.toResourceUiState
import com.dna.payments.kmm.domain.repository.CreateNewLinkRepository
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.constants.Constants.GBP
import com.dna.payments.kmm.utils.constants.Constants.UCOF
import com.dna.payments.kmm.utils.extension.convertToServerFormat
import com.dna.payments.kmm.utils.extension.generateRandomOrderNumber
import com.dna.payments.kmm.utils.extension.getDefaultDateRange
import kotlinx.coroutines.launch

class NewPaymentLinkViewModel(
    private val getStoresUseCase: GetEcomStoresUseCase,
    private val createNewLinkRepository: CreateNewLinkRepository,
    private val currencyUseCase: CurrencyUseCase,
    private val storesUseCase: StoresUseCase,
    private val validateField: ValidateField
) :
    BaseViewModel<NewPaymentLinkContract.Event, NewPaymentLinkContract.State, NewPaymentLinkContract.Effect>() {

    init {
        getStores()
    }

    private var cardSettings: CardSettings = CardSettings(
        allowRecurring = false,
        recurringByDefault = false,
        transactionTypeSelection = false
    )

    override fun createInitialState() = NewPaymentLinkContract.State(
        createNewLinkState = ResourceUiState.Idle,
        transactionType = TransactionType.getCreateNewLinkValues().first(),
        transactionTypeList = TransactionType.getCreateNewLinkValues(),
        storeList = ResourceUiState.Loading,
        currencyList = emptyList(),
        selectedStore = null,
        dateSelection = getDefaultDateRange().second,
        selectedCurrency = Currency(GBP),
        amount = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.AMOUNT,
            validationResult = mutableStateOf(ValidationResult(successful = true))
        ),
        customerName = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.CUSTOMER_NAME,
            validationResult = mutableStateOf(ValidationResult(successful = true))
        ),
        description = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.DESCRIPTION,
            validationResult = mutableStateOf(ValidationResult(successful = true))
        ),
        orderNumber = TextFieldUiState(
            input = mutableStateOf(generateRandomOrderNumber()),
            textInput = TextInput.ORDER_NUMBER,
            validationResult = mutableStateOf(ValidationResult(successful = true))
        ),
        store = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.STORES,
            validationResult = mutableStateOf(ValidationResult(successful = true))
        ),
        expiredDate = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.EXPIRED_DATE,
            validationResult = mutableStateOf(ValidationResult(successful = true))
        )
    )

    override fun handleEvent(event: NewPaymentLinkContract.Event) {
        when (event) {
            NewPaymentLinkContract.Event.OnCreateNewLinkClicked -> {
                validateAllFields()
            }
            is NewPaymentLinkContract.Event.OnCurrencyChange -> {
                setState {
                    copy(selectedCurrency = event.selectedCurrency)
                }
            }
            is NewPaymentLinkContract.Event.OnDateSelection -> {
                setState {
                    copy(
                        dateSelection = event.dateSelection
                    )
                }
            }
            NewPaymentLinkContract.Event.OnGenerateNewRandomNumberClick -> {
                currentState.orderNumber.input.value = generateRandomOrderNumber()
            }
            is NewPaymentLinkContract.Event.OnStoreItemChanged -> {
                cardSettings = storesUseCase.getCardSettingsPL(
                    storesUseCase.findPrioritizedTerminal(
                        event.storesItem.terminals
                    )
                )
                val currencyList = currencyUseCase.getCurrencyListFromStoreItem(
                    event.storesItem
                )
                setState {
                    copy(
                        selectedStore = event.storesItem,
                        selectedCurrency = currencyList.first(),
                        currencyList = currencyList
                    )

                }
            }
            is NewPaymentLinkContract.Event.OnTransactionTypeChanged -> {
                setState {
                    copy(transactionType = event.transactionType)
                }
            }
        }
    }

    private fun validateAllFields() {
        val amountValidation = validateField(currentState.amount.input.value, TextInput.AMOUNT)
        val customerNameValidation =
            validateField(currentState.customerName.input.value, TextInput.CUSTOMER_NAME)
        val descriptionValidation =
            validateField(currentState.description.input.value, TextInput.DESCRIPTION)
        val orderNumberValidation =
            validateField(currentState.orderNumber.input.value, TextInput.ORDER_NUMBER)
        val selectedDateValidation =
            validateField(
                currentState.dateSelection.endDate.convertToServerFormat(),
                TextInput.EXPIRED_DATE
            )


        val validations = listOf(
            amountValidation,
            customerNameValidation,
            descriptionValidation,
            orderNumberValidation,
            selectedDateValidation
        )

        val allValid = validations.all { it.successful }

        if (allValid) {
            createNewLink()
            return
        }
        val errorTextInputFields = validations.filter { !it.successful }
        errorTextInputFields.forEach {
            when (it.textInput) {
                TextInput.AMOUNT -> {
                    currentState.amount.validationResult.value = it
                }
                TextInput.CUSTOMER_NAME -> {
                    currentState.customerName.validationResult.value = it
                }
                TextInput.DESCRIPTION -> {
                    currentState.description.validationResult.value = it
                }
                TextInput.ORDER_NUMBER -> {
                    currentState.orderNumber.validationResult.value = it
                }
                TextInput.EXPIRED_DATE -> {
                    currentState.expiredDate.validationResult.value = it
                }
                else -> {}
            }
        }
    }

    private fun getStores() {
        screenModelScope.launch {
            val result = getStoresUseCase()
            setState {
                copy(
                    storeList = result.toResourceUiState()
                )
            }
        }
    }


    private fun createNewLink() {
        val createNewLink = CreateNewLinkRequest(
            amount = currentState.amount.input.value.toDouble(),
            currency = currentState.selectedCurrency.name,
            customerName = currentState.customerName.input.value,
            description = currentState.description.input.value,
            expirationDate = currentState.dateSelection.endDate.convertToServerFormat(),
            invoiceId = currentState.orderNumber.input.value,
            terminalId = currentState.selectedStore?.terminals?.first()?.id.toString(),
            periodic = if (true) Periodic(UCOF) else null,
            transactionType = if (cardSettings.transactionTypeSelection) currentState.transactionType.key else null
        )
        screenModelScope.launch {
            val result = createNewLinkRepository.createNewLink(createNewLink)
            setState {
                copy(
                    createNewLinkState = result.onSuccess {
                        setEffect {
                            NewPaymentLinkContract.Effect.OnSuccessCreateNewLink(it)
                        }
                    }.toResourceUiState()
                )
            }
        }
    }
}