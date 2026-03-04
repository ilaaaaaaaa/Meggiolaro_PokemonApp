import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    @GET("pokemon/{name}")
    fun getPokemonSync(@Path("name") name: String): Call<PokemonResponse>
}