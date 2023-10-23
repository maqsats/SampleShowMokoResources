package daniel.avila.rnm.kmm.data.model.mapper

import daniel.avila.rnm.kmm.data.model.exchange_rate.CurrencyRateApiModel
import daniel.avila.rnm.kmm.data.model.exchange_rate.ExchangeRateApiModel
import daniel.avila.rnm.kmm.data.model.exchange_rate.LocationApiModel
import daniel.avila.rnm.kmm.data.model.exchange_rate.OpenHourApiModel
import daniel.avila.rnm.kmm.domain.model.exchange_rate.CurrencyRate
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.model.exchange_rate.Location
import daniel.avila.rnm.kmm.domain.model.exchange_rate.OpenHour
import daniel.avila.rnm.kmm.domain.model.exchange_rate.Tag
import daniel.avila.rnm.kmm.domain.model.map.Mapper

class ExchangeRateMapper : Mapper<ExchangeRateApiModel, ExchangeRate>() {
    override fun map(model: ExchangeRateApiModel): ExchangeRate {
        return ExchangeRate(
            categoryId = model.category_id,
            currencyRate = getCurrencyRate(model.currency_rate),
            id = model.id,
            location = getLocation(model.location),
            logo = model.logo.orEmpty(),
            name = model.name,
            openHours = getOpenHours(model.open_hours)
        )
    }

    private fun getOpenHours(openHours: List<OpenHourApiModel>): List<OpenHour> {
        return openHours.map { getOpenHour(it) }
    }

    private fun getOpenHour(openHourApiModel: OpenHourApiModel): OpenHour {
        return OpenHour(
            closeTime = openHourApiModel.close_time,
            dayOfWeek = openHourApiModel.day_of_week,
            openTime = openHourApiModel.open_time
        )
    }

    private fun getLocation(location: LocationApiModel): Location {
        return Location(
            address = location.address,
            cityId = location.city_id,
            latitude = location.latitude,
            longitude = location.longitude,
            name = location.name,
            phone = location.phone,
            distance = location.distance,
            id = location.id,
            tags = getTags(location.tags)
        )
    }

    private fun getTags(tags: List<String>): List<Tag> {
        return tags.map { Tag.fromValue(it) }
    }

    private fun getCurrencyRate(from: CurrencyRateApiModel): CurrencyRate {
        return CurrencyRate(
            buy = from.buy,
            sell = from.sell,
            currencyCode = from.currency_code,
            quantity = from.quantity,
            updatedAt = from.updated_at
        )
    }

}