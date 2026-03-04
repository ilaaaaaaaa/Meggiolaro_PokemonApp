fun main() {
    // Chiedo all'utente di inserire il nome del Pokemon
    print("Inserisci nome Pokemon: ")
    val name = readLine()?.lowercase() ?: return  // lowercase per uniformare il nome

    // Provo a leggere il Pokemon dal database locale
    var pokemon = DatabaseManager.getPokemonByName(name)

    if (pokemon == null) {
        // Se non è nel database, lo prendo dall'API
        println("Non trovato nel database. Chiamo API...")

        try {
            // Chiamata sincrona all'API
            val response = RetrofitInstance.api.getPokemonSync(name).execute()

            if (response.isSuccessful) {
                val body = response.body()!! // prendo il corpo della risposta
                // Converto i tipi in una stringa separata da virgole
                val types = body.types.joinToString(",") { it.type.name }

                // Inserisco i dati nel database
                DatabaseManager.insertPokemon(
                    body.id,
                    body.name,
                    body.height,
                    body.weight,
                    types
                )

                // Rileggo dal database per avere lo stesso formato dei dati
                pokemon = DatabaseManager.getPokemonByName(name)
            } else {
                println("Errore API: ${response.code()}") // codice errore HTTP
                return
            }
        } catch (e: Exception) {
            println("Errore API: ${e.message}") // problemi di rete o altro
            return
        }
    }

    // Stampo i dati del Pokemon
    println("\n=== DATI POKEMON ===")
    println("ID: ${pokemon?.get("id")}")
    println("Nome: ${pokemon?.get("name")}")
    println("Altezza: ${pokemon?.get("height")}")
    println("Peso: ${pokemon?.get("weight")}")
    println("Tipi: ${pokemon?.get("types")}")
}