package it.coffee.smartcoffee.domain.model

data class Coffee(val style: CoffeeType, val size: CoffeeSize?, val extra: List<CoffeeExtra>)
