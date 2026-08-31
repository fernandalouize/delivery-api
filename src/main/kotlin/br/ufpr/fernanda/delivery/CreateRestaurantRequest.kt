package br.ufpr.fernanda.delivery

data class CreateRestaurantRequest(
		val name: String,
		val address: String,
		val deliveryBaseFeeInCents: Int,
		val latitude: Double,
		val longitude: Double
)
