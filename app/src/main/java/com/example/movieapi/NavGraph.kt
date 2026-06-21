package com.example.movieapi

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.savedState
import okhttp3.Route

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val items  = listOf(
        BottomNav.Home ,
        BottomNav.Search ,
        BottomNav.WatchList
    )

    val vm : MovieViewModel = hiltViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != NavRoutes.Detail.route){
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                    restoreState = true

                                }
                            }, icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null
                                )
                            },
                            label = { Text(item.title) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController ,
            startDestination = BottomNav.Home.route ,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomNav.Home.route){
                MovieUi(
                    vm,
                    onClick = {id->
                        navController.navigate(NavRoutes.Detail.createRouteId(id))
                    }
                )
            }

            composable(BottomNav.Search.route){
                SearchScreen(
                    vm,
                    onClick = { id->
                        navController.navigate(NavRoutes.Detail.createRouteId(id))
                    }
                )
            }

            composable(BottomNav.WatchList.route){
                WatchList(
                    onClick = {id->
                        navController.navigate(
                            NavRoutes.Detail.createRouteId(id)
                        )
                    }
                )
            }

            composable(
                NavRoutes.Detail.route ,
                arguments = listOf(navArgument("id"){type = NavType.StringType})
            ){a->
                val id = a.arguments?.getString("id")
                DetailScreen(
                    onBack = {
                        navController.navigateUp()
                    } ,
                    id = id ?: ""
                )
            }
        }
    }

}