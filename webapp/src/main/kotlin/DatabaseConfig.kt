import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

@UseExperimental(ObsoleteCoroutinesApi::class)
class DatabaseConfig {
    private val dispatcher: CoroutineContext = newFixedThreadPoolContext(5, "database-pool")

    init {
        DbSettings.db
    }

    private suspend fun <T> dbQuery(block: () -> T): T =
            withContext(dispatcher) {
                transaction {
                    addLogger(StdOutSqlLogger)
                    block().also { commit() }
                }
            }

    suspend fun addArticle(article: Article) =
            article(dbQuery {
                Articles.insert {
                    it[name] = article.name
                    it[image] = article.image
                    it[price] = article.price
                }[Articles.id]
            }!!)

    private suspend fun article(id: Int) = dbQuery {
        Articles.select {
            Articles.id eq id
        }.mapNotNull { toArticle(it) }
                .single()
    }

    suspend fun articles() = dbQuery {
        Articles.selectAll()
                .map { toArticle(it) }
    }

    private fun toArticle(row: ResultRow): Article =
            Article(
                    id = row[Articles.id],
                    name = row[Articles.name],
                    price = row[Articles.price],
                    image = row[Articles.image]
            )
}

object DbSettings {
    val db by lazy {
        Database.connect(hikari())
        transaction {
            create(Articles)
        }
    }
}

private fun hikari(): HikariDataSource {
    val config = HikariConfig()
    config.driverClassName = "org.h2.Driver"
    config.jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false"
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()
    return HikariDataSource(config)
}