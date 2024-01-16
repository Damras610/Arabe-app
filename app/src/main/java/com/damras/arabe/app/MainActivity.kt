package com.damras.arabe.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.damras.arabe.app.addword.AddWord
import com.damras.arabe.app.main.Main
import com.damras.arabe.app.navigation.Screen
import com.damras.arabe.app.theme.ArabeVocabulaireTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArabeVocabulaireTheme {
                navigation()
            }
        }
    }
}

@Composable
fun navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            Main(navController, koinViewModel())
        }
        composable(Screen.AddWord.route) {
            AddWord(navController, koinViewModel())
        }
    }
}