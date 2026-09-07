package br.ufpr.fernanda.delivery

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class OrderItem(
    @ManyToOne
    val menuItem: MenuItem,

    val quantity: Int,
    val unitPriceInCents: Int,
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    val id: Long? = null 
)