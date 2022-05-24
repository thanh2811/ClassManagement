package com.example.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import ott.vtvlive.vn.models.ContentType
import ott.vtvlive.vn.models.content.ContentDetail
import ott.vtvlive.vn.models.page.CollectionItem
import ott.vtvlive.vn.models.profile.WatchedContent

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