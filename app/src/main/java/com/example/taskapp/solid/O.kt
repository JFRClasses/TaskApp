package com.example.taskapp.solid

// O
// 2. Principio de abierto/cerrado

open class Calculadora{
    fun sumar(a:Int,b:Int) : Int = a + b
    fun restar(a:Int,b:Int) : Int = a - b
}

class CalculadoraCientifica : Calculadora(){
    fun raizCuadrada() {

    }
}
fun main(){
    val ci = CalculadoraCientifica()
}