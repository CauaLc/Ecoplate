package br.com.fiap.ecoplate.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.withStyle


@Composable
fun CadastroUsuarioScreen(
    registro: () -> Unit = {}
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    // Estados de erro
    var nomeError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var senhaError by remember { mutableStateOf(false) }
    var confirmarSenhaError by remember { mutableStateOf(false) }
    var senhaMatchError by remember { mutableStateOf(false) }

    // Estados de visibilidade da senha
    var senhaVisivel by remember { mutableStateOf(false) }
    var confirmarSenhaVisivel by remember { mutableStateOf(false) }

    // Checkboxes
    var aceitarTermos by remember { mutableStateOf(false) }
    var enviarDicas by remember { mutableStateOf(false) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Cadastrar Conta", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Junte-se ao EcoPlate e comece a economizar comida hoje.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    nomeError = false
                },
                placeholder = { Text("Nome Completo") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                isError = nomeError,
                modifier = Modifier.fillMaxWidth()
            )
            if (nomeError) {
                Text("nome é obrigatório!", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                placeholder = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError,
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError) {
                Text("email é obrigatório!", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Senha
            OutlinedTextField(
                value = senha,
                onValueChange = {
                    senha = it
                    senhaError = false
                },
                placeholder = { Text("Senha") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                        Icon(
                            imageVector = if (senhaVisivel) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (senhaVisivel) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                isError = senhaError,
                modifier = Modifier.fillMaxWidth()
            )
            if (senhaError) {
                Text("senha é obrigatória!", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Confirmar Senmha
            OutlinedTextField(
                value = confirmarSenha,
                onValueChange = {
                    confirmarSenha = it
                    confirmarSenhaError = false
                    senhaMatchError = false
                },
                placeholder = { Text("Confirme sua senha") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { confirmarSenhaVisivel = !confirmarSenhaVisivel }) {
                        Icon(
                            imageVector = if (confirmarSenhaVisivel) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (confirmarSenhaVisivel) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (confirmarSenhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                isError = confirmarSenhaError || senhaMatchError,
                modifier = Modifier.fillMaxWidth()
            )
            if (confirmarSenhaError) {
                Text("confirmar senha é obrigatório!", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }
            if (senhaMatchError) {
                Text("As senhas não coincidem!", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox Termos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = aceitarTermos, onCheckedChange = { aceitarTermos = it })
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    buildAnnotatedString {
                        append("Eu concordo com os ")
                        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                            append("Termos de Serviço")
                        }
                        append(" e ")
                        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                            append("Política de Privacidade")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            // Checkbox - dicas
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = enviarDicas, onCheckedChange = { enviarDicas = it })
                Spacer(modifier = Modifier.width(8.dp))
                Text("Me envie dicas e atualizações sobre a redução do desperdício de comida")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de Ctriar
            Button(
                onClick = {

                    nomeError = nome.isBlank()
                    emailError = email.isBlank()
                    senhaError = senha.isBlank()
                    confirmarSenhaError = confirmarSenha.isBlank()

                    if (!nomeError && !emailError && !senhaError && !confirmarSenhaError) {
                        if (senha != confirmarSenha) {
                            senhaMatchError = true
                        } else if (aceitarTermos) {
                            registro()
                        }
                    }
                },
                enabled = aceitarTermos,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Criar Conta", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text("  ou  ")
                Divider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { /* Google */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Google")
                }
                OutlinedButton(
                    onClick = { /* Apple */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("Apple")
                }
            }


        }
    }
}




