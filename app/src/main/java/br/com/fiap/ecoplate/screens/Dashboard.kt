package br.com.fiap.ecoplate.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DonutSmall
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// 1. ESTRUTURA DOS ITENS DE NAVEGAÇÃO
data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val navigationItems = listOf(
    BottomNavigationItem("Início", Icons.Default.Home, "home"),
    BottomNavigationItem("Lista", Icons.Default.List, "list"),
    BottomNavigationItem("Receitas", Icons.Default.RestaurantMenu, "recipes"),
    BottomNavigationItem("Gastos", Icons.Default.PieChart, "expenses"),
    BottomNavigationItem("Perfil", Icons.Default.Person, "profile")
)

// 2. TELA PRINCIPAL DO DASHBOARD
@Composable
fun DashboardScreen(mainNavController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor,
        topBar = { DashboardHeader() },
        bottomBar = { BottomAppBar(navController = bottomNavController) }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                DashboardContent(bottomNavController = bottomNavController)
            }

            composable("list") {
                FoodScreen(navController = mainNavController)
            }

            composable("expenses") {
                ReportsScreen(navController = mainNavController)
            }

            composable("recipes") { RecipesScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}

@Composable
fun FoodScreen(navController: NavHostController) {
    TODO("Not yet implemented")
}

// 3. O CONTEÚDO DA TELA INICIAL ("home")
@Composable
fun DashboardContent(bottomNavController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AlimentosCard(bottomNavController = bottomNavController)
        Spacer(modifier = Modifier.height(24.dp))
        ResumoFinanceiroCard(bottomNavController = bottomNavController)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

// 4. A BARRA DE NAVEGAÇÃO
@Composable
fun BottomAppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        contentColor = GreenColor
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, fontSize = 12.sp) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GreenColor,
                    selectedTextColor = GreenColor,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.White.copy(alpha = 0.0f)
                )
            )
        }
    }
}
@Composable
fun RecipesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Tela de Receitas", fontSize = 22.sp)
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Tela de Perfil", fontSize = 22.sp)
    }
}

@Composable
fun DashboardHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        color = Color.White,
        shadowElevation = 0.dp,
        border = BorderStroke(1.dp, LightGrayColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            Text(
                text = "Dashboard",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Sino de notificações",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun AlimentosCard(bottomNavController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
            Text(text = "Alimentos Prestes a Vencer", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "Monitore os itens que precisam ser consumidos em breve.", fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            FoodItemRow(icon = Icons.Outlined.DonutSmall, name = "Tomate Cereja", expiry = "Vence em 2 dias", expiryColor = GreenColor)
            Spacer(modifier = Modifier.height(12.dp))
            FoodItemRow(icon = Icons.Outlined.FoodBank, name = "Leite UHT Itegral", expiry = "Vence Hoje", expiryColor = RedColor)
            Spacer(modifier = Modifier.height(12.dp))
            FoodItemRow(icon = Icons.Outlined.Fastfood, name = "Pão de Forma", expiry = "Vence Amanhã", expiryColor = YellowColor)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { /* TODO: Navegar para tela de adicionar alimento */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar", tint = Color.White)
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Adicionar Alimento", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { bottomNavController.navigate("list") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, BlackColor)
            ) {
                Text(text = "Ver lista completa", color = Color.Black, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun ResumoFinanceiroCard(bottomNavController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
            Text(text = "Resumo Financeiro", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "Visão geral de suas despesas e receitas.", fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            MonthSummary(month = "Agosto", receitas = "R$ 850,00", despesas = "R$ 350,00")
            Spacer(modifier = Modifier.height(12.dp))
            MonthSummary(month = "Julho", receitas = "R$ 600,00", despesas = "R$ 210,00")
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                onClick = { bottomNavController.navigate("expenses") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, color = Color.Gray)
            ) {
                Icon(imageVector = Icons.Default.PieChart, contentDescription = "Estatísticas", tint = Color.Black)
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Estatísticas", color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
        }
    }
}

val GreenColor = Color(0xFF4CAF50)
val YellowColor = Color(0xFFFBC02D)
val RedColor = Color(0xFFE53935)
val LightRedColor = Color(0xFFEF5350)
val LightGrayColor = Color(0xFFFAFAFB)
val BackgroundColor = Color(0xFFFAFAFB)
val BlackColor = Color(0xFF000000)

@Composable
fun FoodItemRow(icon: ImageVector, name: String, expiry: String, expiryColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = name, modifier = Modifier.size(24.dp))
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(text = expiry, fontSize = 12.sp, color = expiryColor, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun MonthSummary(month: String, receitas: String, despesas: String) {
    Surface(
        color = BackgroundColor,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, LightGrayColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = month, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Receitas", color = Color.Gray, fontSize = 14.sp)
                Text(text = receitas, color = GreenColor, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Despesas", color = Color.Gray, fontSize = 14.sp)
                Text(text = despesas, color = LightRedColor, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
        }
    }
}