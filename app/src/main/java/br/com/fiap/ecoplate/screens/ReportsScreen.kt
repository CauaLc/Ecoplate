package br.com.fiap.ecoplate.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodlist.StatusChip

// Cores definidas no DashboardScreen para consistência
val Green = Color(0xFF4CAF50)
val Yellow = Color(0xFFFBC02D)
val Red = Color(0xFFE53935)
val LightRed = Color(0xFFEF5350)
val LightGray = Color(0xFFFAFAFB)
val Background = Color(0xFFFAFAFB)
val Black = Color(0xFF000000)

data class RelatorioAlimento(
    val nome: String,
    val dataVencimento: String,
    val categoria: String,
    val status: String,
    val statusColor: Color
)

data class RelatorioFinanceiro(
    val month: String,
    val receitas: Double,
    val despesas: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(navController: NavHostController) {
    // Dados de exemplo que replicam os do DashboardScreen e adicionam mais itens
    val relatorioAlimentos = listOf(
        RelatorioAlimento("Tomate Cereja", "Vence em 2 dias", "Hortifruti", "Perfeito", Green),
        RelatorioAlimento("Leite UHT Itegral", "Vence Hoje", "Laticínios", "Ruim", Red),
        RelatorioAlimento("Pão de Forma", "Vence Amanhã", "Padaria", "Atenção", Yellow),
        RelatorioAlimento("Alface", "Vence em 4 dias", "Hortifruti", "Perfeito", Green),
        RelatorioAlimento("Iogurte Natural", "Vence em 7 dias", "Laticínios", "Perfeito", Green),
        RelatorioAlimento("Queijo Minas", "Vence em 3 dias", "Laticínios", "Atenção", Yellow)
    )

    val relatorioFinanceiro = listOf(
        RelatorioFinanceiro("Agosto", 850.00, 350.00),
        RelatorioFinanceiro("Julho", 600.00, 210.00),
        RelatorioFinanceiro("Junho", 750.00, 250.00),
        RelatorioFinanceiro("Maio", 900.00, 400.00)
    )

    // Estado para o filtro de tipo de relatório
    var filtroTipo by remember { mutableStateOf("Todos") }
    var expandedTipo by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = { Text("Relatório Geral", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Filtro de Tipo de Relatório
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Filtrar por:", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(16.dp))
                Box {
                    OutlinedButton(
                        onClick = { expandedTipo = true },
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(filtroTipo)
                    }
                    DropdownMenu(
                        expanded = expandedTipo,
                        onDismissRequest = { expandedTipo = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Todos") },
                            onClick = {
                                filtroTipo = "Todos"
                                expandedTipo = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Alimentos") },
                            onClick = {
                                filtroTipo = "Alimentos"
                                expandedTipo = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Financeiro") },
                            onClick = {
                                filtroTipo = "Financeiro"
                                expandedTipo = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Relatório de Alimentos (visível com base no filtro de tipo)
            if (filtroTipo == "Todos" || filtroTipo == "Alimentos") {
                RelatorioAlimentosCard(relatorioAlimentos)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Relatório Financeiro (visível com base no filtro de tipo)
            if (filtroTipo == "Todos" || filtroTipo == "Financeiro") {
                RelatorioFinanceiroCard(relatorioFinanceiro)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatorioAlimentosCard(alimentos: List<RelatorioAlimento>) {
    var filtroCategoria by remember { mutableStateOf("Todas as Categorias") }
    var expandedCategoria by remember { mutableStateOf(false) }
    var filtroVencimento by remember { mutableStateOf("Todos os Vencimentos") }
    var expandedVencimento by remember { mutableStateOf(false) }

    val alimentosFiltrados = alimentos.filter {
        (filtroCategoria == "Todas as Categorias" || it.categoria == filtroCategoria) &&
                (filtroVencimento == "Todos os Vencimentos" || it.dataVencimento == filtroVencimento)
    }

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
                text = "Relatório de Alimentos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Filtros de Categoria e Vencimento (dentro do cartão)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    OutlinedButton(
                        onClick = { expandedCategoria = true },
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(filtroCategoria)
                    }
                    DropdownMenu(
                        expanded = expandedCategoria,
                        onDismissRequest = { expandedCategoria = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Todas as Categorias") },
                            onClick = {
                                filtroCategoria = "Todas as Categorias"
                                expandedCategoria = false
                            }
                        )
                        val categorias = alimentos.map { it.categoria }.distinct()
                        categorias.forEach { categoria ->
                            DropdownMenuItem(
                                text = { Text(categoria) },
                                onClick = {
                                    filtroCategoria = categoria
                                    expandedCategoria = false
                                }
                            )
                        }
                    }
                }

                Box {
                    OutlinedButton(
                        onClick = { expandedVencimento = true },
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(filtroVencimento)
                    }
                    DropdownMenu(
                        expanded = expandedVencimento,
                        onDismissRequest = { expandedVencimento = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Todos os Vencimentos") },
                            onClick = {
                                filtroVencimento = "Todos os Vencimentos"
                                expandedVencimento = false
                            }
                        )
                        val vencimentos = alimentos.map { it.dataVencimento }.distinct()
                        vencimentos.forEach { vencimento ->
                            DropdownMenuItem(
                                text = { Text(vencimento) },
                                onClick = {
                                    filtroVencimento = vencimento
                                    expandedVencimento = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            alimentosFiltrados.forEach { alimento ->
                RelatorioAlimentoRow(alimento)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun RelatorioAlimentoRow(alimento: RelatorioAlimento) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = alimento.nome, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(text = alimento.dataVencimento, fontSize = 13.sp, color = Color.Gray)
        }
        StatusChip(text = alimento.status, color = alimento.statusColor)
    }
}

@Composable
fun Color.StatusChip(text: String) {
    Box(
        modifier = Modifier
            .background(copy(alpha = 0.1f), shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = this@StatusChip, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatorioFinanceiroCard(financas: List<RelatorioFinanceiro>) {
    var filtroMes by remember { mutableStateOf("Todos os Meses") }
    var expandedMes by remember { mutableStateOf(false) }

    val financasFiltradas = if (filtroMes == "Todos os Meses") {
        financas
    } else {
        financas.filter { it.month == filtroMes }
    }

    val totalReceitas = financasFiltradas.sumOf { it.receitas }
    val totalDespesas = financasFiltradas.sumOf { it.despesas }
    val economiaEstimada = totalReceitas - totalDespesas

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
                text = "Relatório Financeiro",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Filtro de Mês (dentro do cartão)
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { expandedMes = true },
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(filtroMes)
                }
                DropdownMenu(
                    expanded = expandedMes,
                    onDismissRequest = { expandedMes = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Todos os Meses") },
                        onClick = {
                            filtroMes = "Todos os Meses"
                            expandedMes = false
                        }
                    )
                    val meses = financas.map { it.month }.distinct()
                    meses.forEach { mes ->
                        DropdownMenuItem(
                            text = { Text(mes) },
                            onClick = {
                                filtroMes = mes
                                expandedMes = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            financasFiltradas.forEach { item ->
                RelatorioFinanceiroItemRow(label = item.month, receitas = "R$ ${String.format("%.2f", item.receitas)}", despesas = "R$ ${String.format("%.2f", item.despesas)}")
            }
            Spacer(modifier = Modifier.height(20.dp))
            RelatorioFinanceiroTotalRow(label = "Total de Receitas", valor = "R$ ${String.format("%.2f", totalReceitas)}", color = Green)
            RelatorioFinanceiroTotalRow(label = "Total de Despesas", valor = "R$ ${String.format("%.2f", totalDespesas)}", color = LightRed)
            RelatorioFinanceiroTotalRow(label = "Economia Estimada", valor = "R$ ${String.format("%.2f", economiaEstimada)}", color = Green)
        }
    }
}

@Composable
fun RelatorioFinanceiroTotalRow(label: String, valor: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Text(text = valor, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun RelatorioFinanceiroItemRow(label: String, receitas: String, despesas: String) {
    Surface(
        color = Background,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, LightGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Receitas", color = Color.Gray, fontSize = 14.sp)
                Text(text = receitas, color = Green, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Despesas", color = Color.Gray, fontSize = 14.sp)
                Text(text = despesas, color = LightRed, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
        }
    }
}
