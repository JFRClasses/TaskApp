package com.example.taskapp.solid

// L
// 3. Principio de Sustitucion de Liskov

open class Figura{
    open fun calcularArea() : Double = 0.0
}

class Circulo(val radio : Double) : Figura(){
    override fun calcularArea(): Double {
        return Math.PI*radio*radio
    }
}

class Triangulo(val base : Double, val altura : Double) : Figura(){
    override fun calcularArea(): Double {
        return base * altura / 2
    }
}