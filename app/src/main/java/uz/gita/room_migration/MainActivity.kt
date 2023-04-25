package uz.gita.room_migration

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.room_migration.data.AppDatabase
import uz.gita.room_migration.data.UserEntity
import uz.gita.room_migration.ui.theme.RoomMigrationTheme

class MainActivity : ComponentActivity() {
    private val database: AppDatabase by lazy(LazyThreadSafetyMode.NONE) {
        Room.databaseBuilder(this, AppDatabase::class.java, "users.db")
            .build()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomMigrationTheme {
                // A surface container using the 'background' color from the theme
                val scope = rememberCoroutineScope()
                var isOpenAddScreen: Boolean by remember { mutableStateOf(false) }
                var users: List<UserEntity> by remember { mutableStateOf(emptyList()) }

                LaunchedEffect(key1 = Unit) {
                    database.getUserDao().getAllUsers().onEach {
                        users = it
                    }.launchIn(this)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
                        if (!isOpenAddScreen) {
                            FloatingActionButton(onClick = { isOpenAddScreen = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add),
                                    contentDescription = "add"
                                )
                            }
                        }
                    }) {
                        if (isOpenAddScreen) {
                            AddUser {
                                scope.launch(Dispatchers.IO) {
                                    database.getUserDao().insertUser(it)
                                    isOpenAddScreen = false
                                }
                            }
                        } else {
                            UsersListScreen(users = users)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UsersListScreen(users: List<UserEntity>) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(items = users) { item: UserEntity ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {

                    Text(
                        text = item.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W700,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "User${item.id}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Gender${item.gender}")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUser(addUser: (UserEntity) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        var userName: String by remember { mutableStateOf("") }
        var age: String by remember { mutableStateOf("") }
        var gender: Int by remember { mutableStateOf(1) }
        Spacer(modifier = Modifier.weight(1f))
        TextField(
            value = userName,
            onValueChange = { userName = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))

        TextField(
            value = age,
            onValueChange = { age = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "Gender")
        Switch(checked = gender == 1, onCheckedChange = {
            gender = if (it) 1 else 0
        })

        Spacer(modifier = Modifier.weight(1f))


        ElevatedButton(
            onClick = { addUser(UserEntity(0, userName,  1)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add")
        }
    }
}