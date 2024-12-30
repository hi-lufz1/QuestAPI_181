package com.example.remotedatabase.ui.navigasi

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.remotedatabase.ui.view.DestinasiDetail
import com.example.remotedatabase.ui.view.DestinasiEntry
import com.example.remotedatabase.ui.view.DestinasiHome
import com.example.remotedatabase.ui.view.DetailScreen
import com.example.remotedatabase.ui.view.EntryMhsScreen
import com.example.remotedatabase.ui.view.HomeScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }
        composable(DestinasiEntry.route) {
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = "${DestinasiDetail.route}/{nim}") { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: ""
            DetailScreen(
                nim = nim,
                navigateBack = { navController.popBackStack() }
            )
        }

    }
}
