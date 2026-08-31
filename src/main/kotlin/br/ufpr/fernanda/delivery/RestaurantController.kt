package br.ufpr.fernanda.delivery

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.ResponseEntity

@RestController
class RestaurantController(
		private val repository: RestaurantRepository
) {
		@PostMapping("/restaurants")
		fun create(@RequestBody restaurant: Restaurant): Restaurant = repository.save(restaurant)
		@GetMapping("/restaurants")
		fun list() : List<Restaurant> = repository.findAll()
		@GetMapping("/restaurants/{id}")
		fun findById(@PathVariable id: Long): ResponseEntity<Restaurant> {
			val restaurant = repository.findById(id).orElse(null)
			return if (restaurant == null){
				ResponseEntity.notFound().build()
			} else {
				ResponseEntity.ok(restaurant)
			}
		}
}
