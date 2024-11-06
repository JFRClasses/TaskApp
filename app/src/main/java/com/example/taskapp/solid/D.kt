package com.example.taskapp.solid

// D
// 4. Principio de Inversion de Dependencias

interface IStorage{
    fun write()
    fun read()
}

class SSD : IStorage{
    override fun write() {
        TODO("Not yet implemented")
    }

    override fun read() {
        TODO("Not yet implemented")
    }

}

class HDD : IStorage{
    override fun write() {
        TODO("Not yet implemented")
    }

    override fun read() {
        TODO("Not yet implemented")
    }

}

class computer(storage:IStorage){

}

class DatabaseConnection {

}

fun main(){
    val hdd = HDD()
    val ssd = SSD()
    val computer = computer(ssd)
}