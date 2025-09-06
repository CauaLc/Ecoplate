package br.com.fiap.ecoplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecoplate.screens.CadastroUsuarioScreen
import br.com.fiap.ecoplate.screens.LoginUsuarioScreen
import br.com.fiap.ecoplate.ui.theme.EcoPlateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoPlateTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    // Tela de Login
                    composable("login") {
                        LoginUsuarioScreen(
                            cadrastoUsuario = { navController.navigate("cadastro") },
                            loginUsuario = { navController.navigate("login") },
                            entrarConvidado = { navController.navigate("login") }
                        )
                    }
                    // Tela de Cadastro
                    composable("cadastro") {
                        CadastroUsuarioScreen(
                            registro = { navController.navigate("login") }
                        )
                    }
                    
                    // Tela Lista de alimentos
                    composable("foodlist") {
                    FoodListScreen()
                }
                }
            }
        }
    }
}


