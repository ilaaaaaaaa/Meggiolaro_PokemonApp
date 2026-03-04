fun main() {
    print("Inserisci nome Pokemon: ")
    val name = readLine()?.lowercase() ?: return

    var pokemon = DatabaseManager.getPokemonByName(name)

    if (pokemon == null) {
        println("Non trovato nel database. Chiamo API...")

        try {
            val response = RetrofitInstance.api.getPokemonSync(name).execute()
            if (response.isSuccessful) {
                val body = response.body()!!
                val types = body.types.joinToString(",") { it.type.name }

                DatabaseManager.insertPokemon(
                    body.id,
                    body.name,
                    body.height,
                    body.weight,
                    types
                )
                pokemon = DatabaseManager.getPokemonByName(name)
            } else {
                println("Errore API: ${response.code()}")
                return
            }
        } catch (e: Exception) {
            println("Errore API: ${e.message}")
            return
        }
    }

    println("\n=== DATI POKEMON ===")
    println("ID: ${pokemon?.get("id")}")
    println("Nome: ${pokemon?.get("name")}")
    println("Altezza: ${pokemon?.get("height")}")
    println("Peso: ${pokemon?.get("weight")}")
    println("Tipi: ${pokemon?.get("types")}")
}