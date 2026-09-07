package br.ufpr.fernanda.delivery

enum class OrderStatus{
    RECEIVED,
    CONFIRMED,
    IN_PREPARATION,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;

    fun canTransitionTo(next: OrderStatus): Boolean = when(this)
    {
        RECEIVED -> next == CONFIRMED || next == CANCELLED
        CONFIRMED -> next == IN_PREPARATION || next == CANCELLED
        IN_PREPARATION -> next == OUT_FOR_DELIVERY || next == CANCELLED
        OUT_FOR_DELIVERY -> next == DELIVERED
        DELIVERED -> false
        CANCELLED -> false
    }
}