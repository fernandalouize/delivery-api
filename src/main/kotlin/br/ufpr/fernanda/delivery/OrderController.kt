package br.ufpr.fernanda.delivery

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@RestController
class OrderController(
    private val orderService: OrderService,
){
    @PostMapping("/orders")
    fun create(@RequestBody request: CreateOrderRequest):
    OrderResponse = orderService.createOrder(request).toResponse()

    @GetMapping("/orders/{id}")
    fun get(@PathVariable id: Long): OrderResponse = 
        orderService.getOrder(id).toResponse()
    
    @GetMapping("/orders")
    fun list(@RequestParam(required = false) status: OrderStatus?): List<OrderResponse> =
    orderService.listOrders(status).map { it.toResponse() }

    @PostMapping("/orders/{id}/status")
    fun advanceStatus(@PathVariable id: Long, @RequestBody request: AdvanceStatusRequest)
    : OrderResponse = orderService.advanceStatus(id, request.status).toResponse()

    @PostMapping("/orders/{id}/cancel")
    fun cancel(@PathVariable id: Long): OrderResponse = 
        orderService.cancelOrder(id).toResponse()
}