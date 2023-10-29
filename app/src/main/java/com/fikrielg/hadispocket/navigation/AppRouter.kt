package com.fikrielg.hadispocket.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fikrielg.hadispocket.ui.screen.SplashScreen
import com.fikrielg.hadispocket.ui.screen.hadistfrombook.HadisFromBookScreen
import com.fikrielg.hadispocket.ui.screen.hadistfrombook.HadisFromBookViewModel
import com.fikrielg.hadispocket.ui.screen.home.HomeScreen
import com.fikrielg.hadispocket.ui.screen.home.HomeViewModel


class AppRouter {
    @Composable
    fun RouterDelegate(
        homeViewModel: HomeViewModel,
        hadisFromBookViewModel: HadisFromBookViewModel
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
            composable(Screen.SplashScreen.route) {
                SplashScreen(navController)
            }
            composable(Screen.HomeScreen.route) {
                HomeScreen(
                    homeViewModel, navController
                )
            }
            composable(
                Screen.HadisFromBookScreen.route, arguments = listOf(
                    navArgument("id") { type = NavType.StringType })
            ) {
                HadisFromBookScreen(
                    hadisFromBookViewModel,
                    navController,
                    it.arguments?.getString("id") ?: ""
                )
            }
        }

    }

    companion object {
        fun push(navController: NavHostController, screen: String) {
            navController.navigate(screen)
        }

        fun pushAndReplace(navController: NavHostController, screen: String) {
            navController.navigate(screen) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }

        fun pop(navController: NavHostController) {
            navController.popBackStack()
        }
    }

}


sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object HomeScreen : Screen("home_screen")
    object HadisFromBookScreen : Screen("hadis_from_book_screen/{id}") {
        fun createRoute(id: String) = "hadis_from_book_screen/$id"
    }
}