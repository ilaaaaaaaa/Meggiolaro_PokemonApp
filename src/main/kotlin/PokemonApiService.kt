import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    // Funzione sincrona per ottenere i dati di un Pokemon
    @GET("pokemon/{name}")
    fun getPokemonSync(@Path("name") name: String): Call<PokemonResponse>
}