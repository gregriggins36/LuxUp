import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

data class Article(val name: String, val image: String, val price: Float)

object Articles : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 200)
    val image: Column<String> = varchar("image", 500)
    val price: Column<Float> = float("price")
}