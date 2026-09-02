package br.ufpr.fernanda.delivery

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.ResponseEntity

@RestController
class MenuItemController(
    private val menuItemRepository: MenuItemRepository,
    private val restaurantRepository: RestaurantRepository,
){
    @PostMapping("/restaurants/{restaurantId}/menu-items")
    fun create(
        @PathVariable restaurantId: Long,
        @RequestBody request: CreateMenuItemRequest,
    ): ResponseEntity<MenuItemResponse>{
        val restaurant = restaurantRepository.findById(restaurantId).orElse(null)
		return if (restaurant == null){
			ResponseEntity.notFound().build()
		} else {
            val item = MenuItem (
                restaurant = restaurant,
                name = request.name,
                description = request.description,
                priceInCents = request.priceInCents,
                available = request.available,
            )

            val saved = menuItemRepository.save(item)
            ResponseEntity.ok(saved.toResponse())
		}
    }

}