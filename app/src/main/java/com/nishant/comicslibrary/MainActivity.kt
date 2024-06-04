package com.nishant.comicslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nishant.comicslibrary.ui.theme.ComicsLibraryTheme
import com.nishant.comicslibrary.view.CharacterBottomNav
import com.nishant.comicslibrary.view.CollectionScreen
import com.nishant.comicslibrary.view.LibraryScreen
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(
    val route: String
) {
    object Library : Destination("library")
    object Collection : Destination("collection")
    object CharacterDetail : Destination("character/{characterId}") {
        fun createRoute(characterId: Int) = "character/$characterId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComicsLibraryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    CharactersScaffold(navController = navController)
                }
            }
        }
    }
}

@Composable
fun CharactersScaffold(navController: NavHostController) {
    //val ScaffoldState = rememberScaffoldState()
    Scaffold(
        //scaffoldState = ScaffoldState,
        bottomBar = { CharacterBottomNav(navController = navController)}
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destination.Library.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Destination.Library.route) {
                LibraryScreen(/*navController*/)
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetail.route) {navBackStackEntery ->

            }
        }
    }
}