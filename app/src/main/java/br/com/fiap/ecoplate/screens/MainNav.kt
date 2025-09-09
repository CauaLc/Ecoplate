package br.com.fiap.cadastro

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import br.com.fiap.cadastro.screens.CadastroProdutoScreen
import br.com.fiap.cadastro.screens.ReceitasScreen
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun MainNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "cadastro"
    ) {
        // Tela de cadastro de produtos
        composable("cadastro") {
            CadastroProdutoScreen(
                onProdutoSelecionado = { produtoNome ->
                    // navega para a tela de receitas com o nome do produto
                    val encoded = java.net.URLEncoder.encode(produtoNome, "UTF-8")
                    navController.navigate("receitas/$encoded")
                }
            )

        }

        // Tela de receitas com ingrediente específico
        composable(
            route = "receitas/{ingrediente}",
            arguments = listOf(navArgument("ingrediente") { type = NavType.StringType })
        ) { entry ->
            val ingrediente = entry.arguments?.getString("ingrediente")?.let {
                URLDecoder.decode(it, "UTF-8")
            } ?: ""
            ReceitasScreen(ingrediente)
        }

        // Tela de receitas sem ingrediente (busca manual)
        composable("receitas") {
            ReceitasScreen("")
        }
    }
}
