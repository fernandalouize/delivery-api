package br.ufpr.fernanda.delivery

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class OrderStatusTest {
	@Test
	fun `received can transition to confirmed`  () {
			assertTrue(OrderStatus.RECEIVED.canTransitionTo(OrderStatus.CONFIRMED))
	}
	
	@Test
	fun `cancelled cannot advance to another status`(){
		assertTrue(OrderStatus.entries.none { OrderStatus
		.CANCELLED.canTransitionTo(it) })
	}
	
	@Test
	fun `cannot skip one step` (){
			assertFalse(OrderStatus.RECEIVED.canTransitionTo(OrderStatus.IN_PREPARATION))
			assertFalse(OrderStatus.RECEIVED.canTransitionTo(OrderStatus.DELIVERED))
			assertFalse(OrderStatus.CONFIRMED.canTransitionTo(OrderStatus.OUT_FOR_DELIVERY))
	}
}

