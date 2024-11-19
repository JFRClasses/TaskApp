package com.example.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taskapp.R
import com.example.taskapp.datasource.services.AuthService
import com.example.taskapp.domain.dtos.Auth
import com.example.taskapp.domain.use_cases.SharedPref
import com.example.taskapp.presentation.ui.theme.TaskAppTheme
import com.example.taskapp.presentation.utils.Lock
import com.example.taskapp.presentation.utils.Visibility
import com.example.taskapp.presentation.utils.Visibility_off
import com.example.taskapp.utils.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun LoginScreen(innerPadding: PaddingValues, navController: NavController) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val sharedPref = SharedPref(LocalContext.current)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Taskly",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text(text = "Correo electronico") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "Contraseña") },
            visualTransformation = if (!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray
            ),
            leadingIcon = {
                Icon(imageVector = Lock, contentDescription = "email")
            },
            trailingIcon = {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    val icon = if(!isPasswordVisible) Visibility else Visibility_off
                    Icon(imageVector = icon, contentDescription = "visibility")
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val authService = Retrofit.Builder()
                        .baseUrl("https://taskapi.juanfrausto.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(AuthService::class.java)
                    val auth = Auth(email = email, password = password)
                    val response = authService.login(auth)
                    Log.i("LoginScreen", response.toString())
                    if (response.body()?.isLogged == true) {
                        withContext(Dispatchers.Main) {
                            sharedPref.saveUserSharedPref(
                                userId = response.body()?.userId ?: 0,
                                isLogged = true
                            )
                            navController.navigate(Screens.Home.route){
                                popUpTo(Screens.Home.route) { inclusive = true }
                            }
                        }
                    }
                }

            }) {
            Text("Iniciar sesion")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¿No tienes una cuenta? Crea una",
            color = Color.Gray,
            modifier = Modifier.clickable {
                Log.i("Login", "Navegar")
                navController.navigate(Screens.Register.route)
            })
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
    TaskAppTheme {
        LoginScreen(innerPadding = PaddingValues(0.dp), navController = rememberNavController())
    }
}