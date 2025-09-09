package br.com.fiap.cadastro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val GreenColor = Color(0xFF2E7D32)

// Data classes
data class MealResponse(val meals: List<Meal>?)
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

data class MealByIngredientResponse(val meals: List<MealByIngredient>?)
data class MealByIngredient(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

// Retrofit API
interface MealApi {
    @GET("filter.php")
    suspend fun searchMealsByIngredient(@Query("i") ingrediente: String): MealByIngredientResponse
}

// API Client
object ApiClient {
    val mealApi: MealApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceitasScreen(ingrediente: String) {
    var query by remember { mutableStateOf(ingrediente) }
    var receitas by remember { mutableStateOf<List<Meal>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Buscar Receitas",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = GreenColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Digite um ingrediente ou produto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (query.isNotBlank()) {
                    scope.launch {
                        loading = true
                        try {
                            val response = ApiClient.mealApi.searchMealsByIngredient(query)
                            receitas = response.meals?.map {
                                Meal(it.idMeal, it.strMeal, it.strMealThumb)
                            } ?: emptyList()
                        } catch (e: Exception) {
                            receitas = emptyList()
                        } finally {
                            loading = false
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenColor)
        ) {
            Text("Buscar", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator(color = GreenColor)
        } else if (receitas.isEmpty()) {
            Text(
                text = "Nenhuma receita encontrada",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(receitas) { meal ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = meal.strMealThumb,
                                contentDescription = meal.strMeal,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(end = 12.dp)
                            )
                            Text(
                                text = meal.strMeal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }

    // Busca automática quando vem do ProdutoCard
    LaunchedEffect(ingrediente) {
        if (ingrediente.isNotBlank()) {
            loading = true
            try {
                val response = ApiClient.mealApi.searchMealsByIngredient(ingrediente)
                receitas = response.meals?.map {
                    Meal(it.idMeal, it.strMeal, it.strMealThumb)
                } ?: emptyList()
            } catch (e: Exception) {
                receitas = emptyList()
            } finally {
                loading = false
            }
        }
    }
}
