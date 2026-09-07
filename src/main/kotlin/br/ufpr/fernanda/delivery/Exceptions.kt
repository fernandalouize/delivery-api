package br.ufpr.fernanda.delivery

class NotFoundException(message: String) : RuntimeException(message)

class InvalidOrderException(message: String) : RuntimeException(message)

class InvalidStatusTransitionException(message: String) : RuntimeException(message)

