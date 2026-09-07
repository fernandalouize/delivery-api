package br.ufpr.fernanda.delivery 

import java.time.Instant

data class OrderResponse(
    val restaurantId: Long,
    val customerName: String,
    val deliveryAddress: String,
    val latitude: Double,
    val longitude: Double,
    val totalInCents: Int,
    val deliveryFeeInCents: Int,
    val status: OrderStatus,
    val createdAt: Instant,
    val id: Long,

    val items: List<OrderItemResponse>,
)

data class OrderItemResponse(
    val menuItemName: String,
    val menuItemId: Long,
    val quantity: Int,
    val unitPriceInCents: Int,
)

fun OrderItem.toResponse(): OrderItemResponse = 
    OrderItemResponse(
        menuItemName = menuItem.name,
        menuItemId = menuItem.id!!,
        quantity = quantity,
        unitPriceInCents = unitPriceInCents,
    )

fun Order.toResponse() : OrderResponse =
    OrderResponse(
        restaurantId = restaurant.id!!,
        customerName = customerName,
        deliveryAddress = deliveryAddress,
        latitude = latitude,
        longitude = longitude,
        totalInCents = totalInCents,
        deliveryFeeInCents = deliveryFeeInCents,
        status = status,
        createdAt = createdAt,
        id = id!!,
        items = items.map{ it.toResponse()}
    )