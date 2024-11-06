package com.example.taskapp.utils

sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object Login : Screens("login")
    data object Register : Screens("register")
}