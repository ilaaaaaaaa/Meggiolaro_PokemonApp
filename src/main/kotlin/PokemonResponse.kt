data class PokemonResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>
)

data class TypeSlot(
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)