package br.ufpr.fernanda.delivery

data class MenuItemResponse(
    val name: String,
    val description: String,
    val priceInCents: Int,
    val available: Boolean,
    val id: Long
)

fun MenuItem.toResponse() : MenuItemResponse = 
    MenuItemResponse(
        name = name,
        description = description,
        priceInCents = priceInCents,
        available = available,
        id = id!!
)