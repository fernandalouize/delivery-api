package br.ufpr.fernanda.delivery

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class MenuItem(
    @ManyToOne
    val restaurant: Restaurant,

    val name: String,
    val description: String,
    val priceInCents: Int,
    val available: Boolean,
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null 
)