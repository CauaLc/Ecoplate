package br.com.fiap.ecoplate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.fiap.ecoplate.R


data class FoodItem(
    val name: String,
    val price: String,
    val expireDate: String,
    val status: String, // "Fresco", "Amanhã", "Próximo da Val"
    val statusColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodListScreen(navController: NavHostController) {
    val foods = listOf(
        FoodItem("Rúcula", "R$ 10,50", "29/09/2025", "Fresco", Color(0xFF4CAF50)),
        FoodItem("Abóbora", "R$ 5,20", "30/08/2025", "Próximo da Val", Color(0xFF8D6E63)),
        FoodItem("Ovo", "R$ 15,50", "29/09/2025", "Fresco", Color(0xFF4CAF50)),
        FoodItem("Arroz", "R$ 7,50", "28/08/2025", "Amanhã", Color(0xFFF44336)),
    )

    Scaffold(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F3F4)), // <-- COR DE FUNDO DA PÁGINA
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo), // nome do arquivo gerado
                            contentDescription = "Meu ícone",
                            modifier = Modifier.size(36.dp).background(Color(0xFFF8F9FA))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("EcoPlate", fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Lista de Alimentos", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(
                "Controle sua comida e acompanhe a data de validade",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            foods.forEach { food ->
                FoodCard(food)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun FoodCard(food: FoodItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF) // <-- COR DO FUNDO DO CARD
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(food.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Val: ${food.expireDate}", fontSize = 14.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    food.price,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = food.statusColor
                )
                StatusChip(food.status, food.statusColor)
            }
        }
    }
}

@Composable
fun StatusChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.1f), shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}