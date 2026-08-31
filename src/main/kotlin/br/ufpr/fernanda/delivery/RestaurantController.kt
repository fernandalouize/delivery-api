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
		fun create(@RequestBody request: CreateRestaurantRequest): RestaurantResponse {
			val restaurant = Restaurant(
					name = request.name,
					address = request.address,
					deliveryBaseFeeInCents = request.deliveryBaseFeeInCents,
					latitude = request.latitude,
					longitude = request.longitude
			)
			
			val saved = repository.save(restaurant)
			
			return saved.toResponse()
		}
		@GetMapping("/restaurants")
		fun list() : List<RestaurantResponse>{ 
			return repository.findAll().map{ it.toResponse() }
			}	
		@GetMapping("/restaurants/{id}")
		fun findById(@PathVariable id: Long): ResponseEntity<RestaurantResponse> {
			val restaurant = repository.findById(id).orElse(null)
			return if (restaurant == null){
				ResponseEntity.notFound().build()
			} else {
				val response = restaurant.toResponse()
				ResponseEntity.ok(response)
			}
		}
}
