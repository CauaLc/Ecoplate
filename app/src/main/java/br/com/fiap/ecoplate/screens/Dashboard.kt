package br.com.fiap.ecoplate.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.navigation.NavHostController
import br.com.fiap.ecoplate.ui.theme.EcoPlateTheme

val GreenColor = Color(0xFF4CAF50)
val YellowColor = Color(0xFFFBC02D)
val RedColor = Color(0xFFE53935)
val LightRedColor = Color(0xFFEF5350)
val LightGrayColor = Color(0xFFFAFAFB)
val BackgroundColor = Color(0xFFFAFAFB)
val BlackColor = Color(0xFF000000)

@Composable
fun DashboardScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor,
        topBar = { DashboardHeader() },
        bottomBar = { BottomAppBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            AlimentosCard()
            Spacer(modifier = Modifier.height(24.dp))
            ResumoFinanceiroCard(navController = navController)
            Spacer(modifier = Modifier.height(24.dp))
        }
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
fun AlimentosCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Alimentos Prestes a Vencer",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Monitore os itens que precisam ser consumidos em breve.",
                fontSize = 13.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            FoodItemRow(icon = Icons.Outlined.DonutSmall, name = "Tomate Cereja", expiry = "Vence em 2 dias", expiryColor = GreenColor)
            Spacer(modifier = Modifier.height(12.dp))
            FoodItemRow(icon = Icons.Outlined.FoodBank, name = "Leite UHT Itegral", expiry = "Vence Hoje", expiryColor = RedColor)
            Spacer(modifier = Modifier.height(12.dp))
            FoodItemRow(icon = Icons.Outlined.Fastfood, name = "Pão de Forma", expiry = "Vence Amanhã", expiryColor = YellowColor)
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* TODO */ },
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
                onClick = { /* TODO */ },
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
fun ResumoFinanceiroCard(navController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Resumo Financeiro",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Visão geral de suas despesas e receitas.",
                fontSize = 13.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            MonthSummary(month = "Agosto", receitas = "R$ 850,00", despesas = "R$ 350,00")
            Spacer(modifier = Modifier.height(12.dp))
            MonthSummary(month = "Julho", receitas = "R$ 600,00", despesas = "R$ 210,00")
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = { navController.navigate("reports_screen") },
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

@Composable
fun BottomAppBar() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Início", "Lista", "Receitas", "Gastos", "Perfil")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.List,
        Icons.Default.RestaurantMenu,
        Icons.Default.PieChart,
        Icons.Default.Person
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = GreenColor
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = item) },
                label = { Text(item, fontSize = 12.sp) },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
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
