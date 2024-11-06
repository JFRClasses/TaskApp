package com.example.taskapp.solid

// I
// 4. Principio de Segregacion de Interfaz

interface IAve{
    fun comer()
   // fun volar()
}

interface IAveVoladora{
    fun volar()
}

class Pinguino : IAve{
    override fun comer() {
        TODO("Not yet implemented")
    }
//
//    override fun volar() {
//        TODO("Not yet implemented")
//    }

}

class Aguila : IAve,IAveVoladora{
    override fun comer() {
        TODO("Not yet implemented")
    }

    override fun volar() {
        TODO("Not yet implemented")
    }

//    override fun volar() {
//        TODO("Not yet implemented")
//    }

}