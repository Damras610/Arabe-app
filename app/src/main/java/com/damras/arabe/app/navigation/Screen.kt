package com.damras.arabe.app.navigation

sealed class Screen(val route: String) {
    data object Main: Screen(route = "main")
    data object AddWord: Screen(route = "addWord")
}