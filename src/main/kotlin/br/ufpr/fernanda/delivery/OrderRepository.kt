package br.ufpr.fernanda.delivery

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByStatus(status : OrderStatus): List<Order>
}