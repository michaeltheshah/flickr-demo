package com.cvshealth.flickrdemo.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cvshealth.flickrdemo.ui.screens.FlickrSearchScreen
import com.cvshealth.flickrdemo.ui.screens.FlickrResultDetailsScreen
import com.cvshealth.flickrdemo.ui.viewmodel.FlickrViewModel

@Composable
fun NavGraph(
    contentPadding: PaddingValues,
    navController: NavHostController
) {
    val viewModel = hiltViewModel<FlickrViewModel>()
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            FlickrSearchScreen(viewModel, contentPadding) { link ->
                navController.navigate(FlickrResultDetail(link))
            }
        }

        composable<FlickrResultDetail> { backStackEntry ->
            val flickrResultDetail = backStackEntry.toRoute<FlickrResultDetail>()
            FlickrResultDetailsScreen(viewModel, link = flickrResultDetail.link, Modifier.padding(contentPadding))
        }
    }
}