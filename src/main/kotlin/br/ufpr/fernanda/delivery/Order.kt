package br.ufpr.fernanda.delivery

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.CascadeType
import java.time.Instant

@Entity
@Table(name = "orders")
class Order(
    @ManyToOne val restaurant: Restaurant,
    @OneToMany(cascade = [CascadeType.ALL]) val items: List<OrderItem>,
    val customerName: String,
    val deliveryAddress: String,
    val latitude: Double,
    val longitude: Double,
    val totalInCents: Int,
    val deliveryFeeInCents: Int,
    var status: OrderStatus,
    val createdAt: Instant,
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)