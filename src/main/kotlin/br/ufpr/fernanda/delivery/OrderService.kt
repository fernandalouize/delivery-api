package br.ufpr.fernanda.delivery

import org.springframework.stereotype.Service
import kotlin.math.sqrt 
import java.time.Instant

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val restaurantRepository: RestaurantRepository,
    private val menuItemRepository: MenuItemRepository,
){
    private val feePerKmInCents = 200

    fun calculateDeliveryFee(
        restaurant: Restaurant, 
        latitude: Double,
        longitude: Double,
    ): Int {
        val dLat = latitude - restaurant.latitude
        val dLon = longitude - restaurant.longitude

        val distance = sqrt(dLat*dLat + dLon*dLon) * 111

        return restaurant.deliveryBaseFeeInCents + (distance*feePerKmInCents).toInt()
    }

    fun createOrder(request: CreateOrderRequest): Order {
        val restaurant = 
        restaurantRepository.findById(request.restaurantId).orElseThrow{ NotFoundException("Restaurant ${request.restaurantId} not found") }
    
        val orderItems = request.items.map { itemRequest ->
            val menuItem = menuItemRepository.findById(itemRequest.menuItemId)
            .orElseThrow{NotFoundException("Menu item ${itemRequest.menuItemId} not found") }

            if (!menuItem.available) {
                throw InvalidOrderException("Menu item ${menuItem.name} is not available.")
            }
            if (menuItem.restaurant.id != restaurant.id){
                throw InvalidOrderException("Menu item ${menuItem.name} belongs to another restaurant")
            }
    
            OrderItem(
                menuItem = menuItem,
                quantity = itemRequest.quantity,
                unitPriceInCents = menuItem.priceInCents
            )    
        }

        val subtotal = orderItems.sumOf{ it.unitPriceInCents * it.quantity }

        val deliveryFee = calculateDeliveryFee(restaurant, request.latitude, request.longitude)

        val order = Order(
            restaurant = restaurant,
            items = orderItems,
            customerName = request.customerName,
            deliveryAddress = request.deliveryAddress,
            latitude = request.latitude,
            longitude = request.longitude,
            totalInCents = subtotal + deliveryFee,
            deliveryFeeInCents = deliveryFee,
            status = OrderStatus.RECEIVED,
            createdAt = Instant.now(),
        )

        return orderRepository.save(order)
    
    }
}