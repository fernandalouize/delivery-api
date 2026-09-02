package br.ufpr.fernanda.delivery

data class CreateMenuItemRequest(
    val name: String,
    val description: String,
    val priceInCents: Int,
    val available: Boolean,
)