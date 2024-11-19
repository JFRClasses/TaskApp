package com.example.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taskapp.datasource.services.TaskService
import com.example.taskapp.domain.models.Task
import com.example.taskapp.domain.use_cases.SharedPref
import com.example.taskapp.presentation.components.TaskBox
import com.example.taskapp.presentation.ui.theme.TaskAppTheme
import com.example.taskapp.presentation.ui.theme.yellow
import com.example.taskapp.presentation.utils.Logout
import com.example.taskapp.utils.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    val sharedPref = SharedPref(LocalContext.current)
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var tasks by remember {
        mutableStateOf(emptyList<Task>())
    }
    var completedTasks by remember {
        mutableStateOf(0)
    }
    var pendingTasks by remember {
        mutableStateOf(0)
    }
    var expiredTasks by remember {
        mutableStateOf(0)
    }
    val scope = rememberCoroutineScope()
    val userId = sharedPref.getUserIdSharedPref()
    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.IO) {
            try{
                val taskService = Retrofit.Builder()
                    .baseUrl("https://taskapi.juanfrausto.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TaskService::class.java)
                val response = taskService.getTasks(userId = userId)
                Log.i("HomeScreen",response.toString())
                if(response.code() == 200){
                    withContext(Dispatchers.Main){
                        tasks = response.body()?.tasks ?: emptyList()
                        completedTasks = response.body()?.completedTasks ?: 0
                        pendingTasks = response.body()?.pendingTasks ?: 0
                        expiredTasks = response.body()?.expiredTasks ?: 0  
                    }
                }
            }
            catch (e:Exception){
                Log.e("HomeScreenError",e.toString())
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Tareas",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Hola",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            IconButton(onClick = {
                sharedPref.removeUserSharedPref()
                navController.navigate(Screens.Login.route){
                    popUpTo(Screens.Login.route) { inclusive = true }
                }
            }) {
                Icon(
                    imageVector = Logout,
                    contentDescription = "logout",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TaskBox(value = completedTasks, title = "Completadas", color = MaterialTheme.colorScheme.secondary)
            TaskBox(value = pendingTasks, title = "Pendientes", color = yellow)
            TaskBox(value = expiredTasks, title = "Vencidas", color = Color.Red)

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lista de tareas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { showBottomSheet = true }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "add",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(tasks){
                Text(text = it.title)
            }
        }
    }
    
    // Bottom Modal
    if(showBottomSheet){
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
            Text(text = "Modal")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    TaskAppTheme {
        HomeScreen(innerPadding = PaddingValues(0.dp), navController = rememberNavController())
    }
}