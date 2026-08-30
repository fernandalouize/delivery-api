package br.ufpr.fernanda.delivery

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Restaurant(
		val name: String,
		val address: String,
		
		val deliveryBaseFeeInCents: Int,
		
		val latitude: Double,
		val longitude: Double,
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY) 
		val id: Long? = null
)
