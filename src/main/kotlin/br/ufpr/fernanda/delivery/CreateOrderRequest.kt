package br.ufpr.fernanda.delivery

data class CreateOrderRequest(
    val restaurantId: Long,
    val customerName: String,
    val deliveryAddress: String,
    val latitude: Double,
    val longitude: Double,
    val items: List<CreateOrderItemRequest>,
)

data class CreateOrderItemRequest(
    val menuItemId: Long,
    val quantity: Int,
)