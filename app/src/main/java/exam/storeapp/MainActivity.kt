package exam.storeapp

import BottomNavBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import exam.storeapp.data.NavBarItem
import exam.storeapp.data.OrderRepository
import exam.storeapp.data.ProductRepository
import exam.storeapp.screens.landing_page.LandingPageScreen
import exam.storeapp.screens.order_history.OrderDetailsScreen
import exam.storeapp.screens.order_history.OrderViewModel
import exam.storeapp.screens.order_history.OrdersListScreen
import exam.storeapp.screens.product_details.ProductDetailsScreen
import exam.storeapp.screens.product_details.ProductDetailsViewModel
import exam.storeapp.screens.product_overview.ProductListScreen
import exam.storeapp.screens.product_overview.ProductListViewModel
import exam.storeapp.screens.shopping_cart.ShoppingCartScreen
import exam.storeapp.screens.shopping_cart.ShoppingCartViewModel
import exam.storeapp.ui.theme.StoreAppTheme

class MainActivity : ComponentActivity() {
    private val _productListViewModel: ProductListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _shoppingCartViewModel: ShoppingCartViewModel by viewModels()
    private val _orderViewModel: OrderViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProductRepository.initializeDatabase(applicationContext)
        OrderRepository.initializeDatabase(applicationContext)

        setContent {
            StoreAppTheme {
                val navController = rememberNavController()

                // Routing for navbar
                val navBarItems = listOf(
                    NavBarItem("landingPageScreen", "Home", Icons.Filled.Home),
                    NavBarItem("productListScreen", "Products", Icons.Filled.List),
                    NavBarItem("shoppingCartScreen", "Cart", Icons.Filled.ShoppingCart),
                    NavBarItem("ordersListScreen", "Orders", Icons.Filled.Info))

                // https://developer.android.com/jetpack/compose/components/scaffold
                Scaffold(
                    bottomBar = { BottomNavBar(navController, navBarItems) }
                ) {innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "landingPageScreen"
                    ) {
                        composable("landingPageScreen") {
                            LandingPageScreen()
                        }
                        composable(
                            "productListScreen"
                        ) {
                            ProductListScreen(
                                viewModel = _productListViewModel,
                                onProductClick = { productId ->
                                    navController.navigate("productDetailsScreen/$productId")
                                },
                                navController = navController
                            )
                        }
                        composable(
                            "productDetailsScreen/{productId}",
                            arguments = listOf(
                                navArgument("productId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                            ProductDetailsScreen(
                                viewModel = _productDetailsViewModel,
                                onBackButtonClick = { navController.popBackStack() },
                                shoppingCartViewModel = _shoppingCartViewModel,
                                productId = productId
                            )
                        }
                        composable("shoppingCartScreen") {
                            ShoppingCartScreen(viewModel = _shoppingCartViewModel)
                        }
                        composable("ordersListScreen") {
                            OrdersListScreen(
                                viewModel = _orderViewModel,
                                onOrderSelected = { orderId ->
                                    navController.navigate("orderDetailsScreen/$orderId")
                                }
                            )
                        }
                        composable(
                            "orderDetailsScreen/{orderId}",
                            arguments = listOf(
                                navArgument("orderId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val orderId = backStackEntry.arguments?.getInt("orderId") ?: -1
                            OrderDetailsScreen(
                                viewModel = _orderViewModel,
                                orderId = orderId
                            )
                        }
                    }
                }
            }
        }
    }
}
