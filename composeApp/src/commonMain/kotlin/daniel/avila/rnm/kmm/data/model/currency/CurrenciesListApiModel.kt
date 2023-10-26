package daniel.avila.rnm.kmm.data.model.currency

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesListApiModel(
    val currencies: List<CurrencyApiModel>
)