package com.example.movieapi

sealed class NavRoutes(val route : String) {

    object List : NavRoutes("list")

    object Detail : NavRoutes("detail/{id}"){
        fun createRouteId(id : String) : String{
            return "detail/${id}"
        }
    }
}