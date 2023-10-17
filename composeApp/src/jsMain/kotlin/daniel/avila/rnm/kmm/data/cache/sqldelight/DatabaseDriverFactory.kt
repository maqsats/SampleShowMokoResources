package daniel.avila.rnm.kmm.data.cache.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.sqljs.initSqlDriver
import daniel.avila.rnm.kmm.data_cache.sqldelight.AppDatabase
import kotlinx.coroutines.await
import org.w3c.dom.Worker

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver = initSqlDriver(AppDatabase.Schema).await()
}