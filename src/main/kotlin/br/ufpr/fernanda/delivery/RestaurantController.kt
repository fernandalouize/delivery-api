package br.ufpr.fernanda.delivery

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController



@RestController
class RestaurantController(
		private val repository: RestaurantRepository
) {
		@PostMapping("/restaurants")
		fun create(@RequestBody restaurant: Restaurant): Restaurant = repository.save(restaurant)
}
