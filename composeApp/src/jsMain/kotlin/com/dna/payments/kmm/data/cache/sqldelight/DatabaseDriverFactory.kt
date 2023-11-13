package com.dna.payments.kmm.data.cache.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.sqljs.initSqlDriver
import com.dna.payments.kmm.data_cache.sqldelight.AppDatabase
import kotlinx.coroutines.await

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver = initSqlDriver(AppDatabase.Schema).await()
}