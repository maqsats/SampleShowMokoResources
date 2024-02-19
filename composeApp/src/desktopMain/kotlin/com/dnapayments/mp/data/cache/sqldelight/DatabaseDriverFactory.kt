package com.dnapayments.mp.data.cache.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.dnapayments.mp.data_cache.sqldelight.AppDatabase

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            AppDatabase.Schema.create(this)
        }
    }
}