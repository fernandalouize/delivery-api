package br.ufpr.fernanda.delivery
import org.springframework.data.jpa.repository.JpaRepository

interface MenuItemRepository : JpaRepository<MenuItem, Long> {
    fun findByRestaurantId(restaurantId : Long): List<MenuItem>

}