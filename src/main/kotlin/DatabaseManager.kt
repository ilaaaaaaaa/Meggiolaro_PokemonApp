import java.sql.DriverManager

object DatabaseManager {

    private val connection = DriverManager.getConnection("jdbc:sqlite:pokemon.db")

    init {
        val stmt = connection.createStatement()
        stmt.executeUpdate(
            """
            CREATE TABLE IF NOT EXISTS pokemon (
                id INTEGER PRIMARY KEY,
                name TEXT,
                height INTEGER,
                weight INTEGER,
                types TEXT
            )
            """.trimIndent()
        )
        stmt.close()
    }

    fun insertPokemon(id: Int, name: String, height: Int, weight: Int, types: String) {
        val pstmt = connection.prepareStatement(
            "INSERT INTO pokemon (id, name, height, weight, types) VALUES (?, ?, ?, ?, ?)"
        )
        pstmt.setInt(1, id)
        pstmt.setString(2, name)
        pstmt.setInt(3, height)
        pstmt.setInt(4, weight)
        pstmt.setString(5, types)
        pstmt.executeUpdate()
        pstmt.close()
    }

    fun getPokemonByName(name: String): Map<String, Any>? {
        val pstmt = connection.prepareStatement("SELECT * FROM pokemon WHERE name = ?")
        pstmt.setString(1, name)
        val rs = pstmt.executeQuery()
        val result = if (rs.next()) {
            mapOf(
                "id" to rs.getInt("id"),
                "name" to rs.getString("name"),
                "height" to rs.getInt("height"),
                "weight" to rs.getInt("weight"),
                "types" to rs.getString("types")
            )
        } else null
        rs.close()
        pstmt.close()
        return result
    }
}