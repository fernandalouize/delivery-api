package br.ufpr.fernanda.delivery

data class RestaurantResponse(
          val name: String,
          val address: String,
          val deliveryBaseFeeInCents: Int,
          val latitude: Double,
          val longitude: Double,
          val id: Long
  )
  
fun Restaurant.toResponse() : RestaurantResponse = 
		RestaurantResponse(
 			name = name,
  			address = address,
  			deliveryBaseFeeInCents = deliveryBaseFeeInCents,
  			latitude = latitude,
  			longitude = longitude,
  			id = id!!,
  		)
