package com.android_study_25th

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android_study_25th.ui.theme.Android_study_25thTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_study_25thTheme {
                MainScreen()
            }
        }
    }
}

data class User(val id: Int, val name: String, val age: Int)

@Composable
fun MainScreen() {
    // TODO 1
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("홈") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },                 // TODO 2
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    label = { Text("검색") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },                 // TODO 3
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("마이") }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {                                   // TODO 4
                0 -> HomeFlowScreen()
                1 -> SearchScreen()
                2 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun HomeFlowScreen() {
    // TODO 5
    var selectedUser by remember { mutableStateOf<User?>(null) }

    // TODO 6
    selectedUser?.let { user ->
        UserDetailScreen(user = user, onBack = { selectedUser = null })
    } ?: UserListScreen(onClick = { selectedUser = it })
}

@Composable
fun UserListScreen(onClick: (User) -> Unit) {
    val users = listOf(User(1, "준이", 23), User(2, "준삼", 30), User(3, "준사", 20))

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("홈 화면", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {                                               // TODO 7
            items(items = users, key = { it.id }) { user ->        // TODO 8
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            onClick(user)                          // TODO 9
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = user.name)
                        Text(text = "${user.age}세")
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetailScreen(user: User, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("상세 화면", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("이름: ${user.name}")                                 // TODO 10
        Text("나이: ${user.age}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("뒤로가기") }              // TODO 11
    }
}

@Composable
fun SearchScreen() {
    // TODO 12
    var keyword by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("검색 화면", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = keyword,                                       // TODO 13
            onValueChange = { keyword = it },                      // TODO 14
            label = { Text("검색어 입력") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("입력한 검색어: $keyword")
    }
}

@Composable
fun ProfileScreen() {
    var liked by remember { mutableStateOf(false) }               // TODO 15
    var count by remember { mutableStateOf(0) }                   // TODO 16

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("마이페이지", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            liked = !liked                                         // TODO 17
        }) {
            Text(if (liked) "좋아요 취소" else "좋아요")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            count++                                                // TODO 18
        }) {
            Text("Count: $count")
        }
    }
}