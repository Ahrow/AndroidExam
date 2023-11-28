package exam.storeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import exam.storeapp.screens.landing_page.LandingPageScreen
import exam.storeapp.screens.product_details.ProductDetailsScreen
import exam.storeapp.screens.product_details.ProductDetailsViewModel
import exam.storeapp.screens.product_overview.ProductListScreen
import exam.storeapp.screens.product_overview.ProductListViewModel
import exam.storeapp.ui.theme.StoreAppTheme


class MainActivity : ComponentActivity() {
    private val _productListViewModel: ProductListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StoreAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "landingPageScreen"
                ) {


                    composable(
                        route = "landingPageScreen"
                    ) {
                        LandingPageScreen(navController)
                    }
                    composable(
                        route = "productListScreen"
                    ) {
                        ProductListScreen(
                            viewModel = _productListViewModel,
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            }
                        )
                    }

                    composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(
                            navArgument(name = "productId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                        LaunchedEffect(productId) {
                            _productDetailsViewModel.setSelectedProduct(productId)
                        }

                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

