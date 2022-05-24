package com.example.dclassmanagement.navigation

import androidx.navigation.NavController

class NavigateHelper private constructor(
    private val navController: NavController,
    private val from: Any?
) {

    class Builder(private val navController: NavController) {

        private var from: Any? = null

        fun from(any: Any?): Builder {
            from = any
            return this
        }

        fun navigate() {
//      NavigateHelper(navController, from).navigate()
        }
    }


}