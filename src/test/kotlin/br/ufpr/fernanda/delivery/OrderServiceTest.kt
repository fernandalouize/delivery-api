package br.ufpr.fernanda.delivery

import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.Mockito.mock

class OrderServiceTest {
		private val service = OrderService(
				mock(OrderRepository::class.java),
				mock(RestaurantRepository::class.java),
				mock(MenuItemRepository::class.java),
		)
		
		@Test
		fun `changes only the base fee when delivering at the restaurant`() {
			val restaurant = Restaurant("Cantina", "Centro", 500, -25.45, -49.23)
			
			val fee = service.calculateDeliveryFee(restaurant, -25.45, -49.23)
			
			assertEquals(500, fee)
		
		}
		
		@Test
		fun `adds distance cost on top of the base fee` () {
			val restaurant = Restaurant("Cantina", "Centro", 500, -25.45, -49.23)
			
			val fee = service.calculateDeliveryFee(restaurant, -25.43, -49.27)
			
			assertEquals(1492, fee)
		}
}
