package com.example.navigation

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.navigation.NavigateHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import jdk.javadoc.internal.doclets.formats.html.markup.Navigation

object Navigator {

  fun Activity.requestNavigateContentDetail(@IdRes id: Int, args: Bundle? = null) {
    Navigation.findNavController(this, R.id.content_detail_host_fragment).navigate(id, args)
  }

  fun Fragment.navigateByHelper(): NavigateHelper.Builder {
    return NavigateHelper.Builder(findNavController())
  }

  fun Fragment.requestNavigate(@IdRes id: Int, args: Bundle? = null) {
    findNavController().navigate(id, args)
  }

  fun Activity.requestNavigate(@IdRes id: Int, args: Bundle? = null) {
    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(id, args)
  }

  fun Activity.requestNavigateDeeplink(uri: Uri) {
    val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    navController.safeNavigateToDeepLink(uri)
  }

  private fun NavController.safeNavigateToDeepLink(uri: Uri) {
    if (graph.hasDeepLink(uri)) navigate(uri)
  }

  fun Activity.getCurrentDestinationId(hostId: Int): Int? {
    return Navigation.findNavController(this, hostId).currentDestination?.id
  }

  fun Fragment.getPreviousDestinationId(): Int? {
    return try {
      findNavController().previousBackStackEntry?.destination?.id
    } catch (ex: Exception) {
      null
    }
  }

  fun Fragment.getCurrentDestinationId(): Int? {
    return try {
      findNavController().currentDestination?.id
    } catch (ex: Exception) {
      null
    }
  }

  fun Fragment.popBack() {
    findNavController().popBackStack()
  }

  fun Fragment.popTo(@IdRes id: Int, inclusive: Boolean = false) : Boolean {
    return findNavController().popBackStack(id, inclusive)
  }
}

