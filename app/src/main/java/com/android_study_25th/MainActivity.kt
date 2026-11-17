package com.android_study_25th

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android_study_25th.ui.theme.Android_study_25thTheme
import kotlinx.coroutines.delay

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

data class PostData(
    val userName: String,
    val location: String,
    val content: String,
    val imageRes: Int,
    val likeCount: Int,
    val commentCount: Int,
    val time: String
)

val postList = listOf(
    PostData("gks_gpdls_", "우리집 거실", "귀여운 뚱냥이", R.drawable.sample_cat, 256, 31, "1시간 전"),
    PostData("gks_gpdls_", "광안리 해수욕장", "노을 보러 광안리 왔다🌊", R.drawable.sample_sea, 98, 7, "3일 전"),
    PostData("gks_gpdls_", "우리집 거실", "밥 먹고 또 잠..", R.drawable.sample_cat, 187, 12, "5일 전"),
    PostData("gks_gpdls_", "광안리 해수욕장", "바다는 언제 봐도 좋다", R.drawable.sample_sea, 64, 3, "1주 전"),
    PostData("gks_gpdls_", "동물병원", "다이어트 시작한 날", R.drawable.sample_cat, 301, 45, "2주 전"),
    PostData("gks_gpdls_", "민락수변공원", "시험 끝나고 바다 산책", R.drawable.sample_sea, 77, 5, "3주 전")
)

// 하단바 탭
enum class BottomTab(val route: String, val label: String, val icon: ImageVector) {
    HOME("home", "홈", Icons.Default.Home),
    SEARCH("search", "탐색", Icons.Default.Search),
    PROFILE("profile", "마이", Icons.Default.Person)
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                BottomTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomTab.HOME.route,
            modifier = Modifier.padding(innerPadding),
            // 탭 이동할 때 살짝 밀리면서 나타나게
            enterTransition = { fadeIn(tween(300)) + slideInHorizontally(tween(300)) { it / 5 } },
            exitTransition = { fadeOut(tween(200)) },
            popEnterTransition = { fadeIn(tween(300)) },
            popExitTransition = { fadeOut(tween(200)) }
        ) {
            composable(BottomTab.HOME.route) {
                HomeScreen()
            }
            composable(BottomTab.SEARCH.route) {
                SearchScreen(onPostClick = { index -> navController.navigate("detail/$index") })
            }
            composable(BottomTab.PROFILE.route) {
                ProfileScreen(onPostClick = { index -> navController.navigate("detail/$index") })
            }
            composable(
                route = "detail/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType }),
                // 상세화면은 오른쪽에서 들어오고 뒤로가기하면 오른쪽으로 나감
                enterTransition = { slideInHorizontally(tween(300)) { it } },
                popExitTransition = { slideOutHorizontally(tween(300)) { it } }
            ) { entry ->
                val index = entry.arguments?.getInt("index") ?: 0
                DetailScreen(post = postList[index], onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 상단 바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MyFeed",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Outlined.AddCircle, contentDescription = "새 게시물", modifier = Modifier.size(26.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Outlined.Notifications, contentDescription = "알림", modifier = Modifier.size(26.dp))
        }

        HorizontalDivider(color = Color(0xFFEEEEEE))

        LazyColumn {
            items(postList) { post ->
                PostItem(post = post)
            }
        }
    }
}

@Composable
fun PostItem(post: PostData) {
    var liked by remember { mutableStateOf(false) }
    var showBigHeart by remember { mutableStateOf(false) }

    // 하트 누르면 통통 튀게
    val heartScale by animateFloatAsState(
        targetValue = if (liked) 1.2f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy),
        label = "heartScale"
    )

    // 큰 하트는 잠깐 보였다가 사라짐
    LaunchedEffect(showBigHeart) {
        if (showBigHeart) {
            delay(800)
            showBigHeart = false
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // 작성자 프로필
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFB4A2)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = post.userName.first().uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = post.userName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = post.location, fontSize = 12.sp, color = Color.Gray)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, contentDescription = "더보기")
            }
        }

        // 게시물 이미지 (두번 탭하면 좋아요)
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = post.imageRes),
                contentDescription = "게시물 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(onDoubleTap = {
                            liked = true
                            showBigHeart = true
                        })
                    }
            )
            // Column 안이라 ColumnScope 버전이 잡혀서 풀네임으로 호출
            androidx.compose.animation.AnimatedVisibility(
                visible = showBigHeart,
                enter = scaleIn(spring(dampingRatio = Spring.DampingRatioMediumBouncy)) + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        // 좋아요, 댓글, 공유 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { liked = !liked }) {
                Icon(
                    imageVector = if (liked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "좋아요",
                    tint = if (liked) Color.Red else Color.Black,
                    modifier = Modifier.scale(heartScale)
                )
            }
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.Send, contentDescription = "보내기")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.Share, contentDescription = "공유")
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.Star, contentDescription = "저장")
            }
        }

        // 본문
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "좋아요 ${if (liked) post.likeCount + 1 else post.likeCount}개",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Row {
                Text(text = post.userName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = post.content, fontSize = 14.sp)
            }
            Text(text = "댓글 ${post.commentCount}개 모두 보기", fontSize = 13.sp, color = Color.Gray)
            Text(text = post.time, fontSize = 11.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SearchScreen(onPostClick: (Int) -> Unit) {
    var keyword by remember { mutableStateOf("") }
    val result = postList.filter { it.content.contains(keyword) || it.location.contains(keyword) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            placeholder = { Text("검색") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        if (result.isEmpty()) {
            Text(
                text = "검색 결과가 없어요",
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                textAlign = TextAlign.Center
            )
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                itemsIndexed(result) { _, post ->
                    GridImage(post = post, onClick = { onPostClick(postList.indexOf(post)) })
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(onPostClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 프로필 정보는 한 줄 전체 차지
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFB4A2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("G", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileCount(number = postList.size, label = "게시물")
                        ProfileCount(number = 152, label = "팔로워")
                        ProfileCount(number = 140, label = "팔로잉")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("gks_gpdls_", fontWeight = FontWeight.Bold)
                Text("뚱냥이 집사 🐱 | 부산", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFEFEF),
                        contentColor = Color.Black
                    )
                ) {
                    Text("프로필 편집")
                }
            }
        }

        itemsIndexed(postList) { index, post ->
            GridImage(post = post, onClick = { onPostClick(index) })
        }
    }
}

@Composable
fun ProfileCount(number: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$number", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = label, fontSize = 13.sp)
    }
}

@Composable
fun GridImage(post: PostData, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = post.imageRes),
        contentDescription = post.content,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .clickable { onClick() }
    )
}

@Composable
fun DetailScreen(post: PostData, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
            }
            Text("게시물", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        PostItem(post = post)
    }
}

// 오른쪽 미리보기에서 화면별로 확인용
@Preview(name = "전체 (하단바)", showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    Android_study_25thTheme {
        MainScreen()
    }
}

@Preview(name = "홈", showBackground = true, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    Android_study_25thTheme {
        HomeScreen()
    }
}

@Preview(name = "탐색", showBackground = true, heightDp = 800)
@Composable
fun SearchScreenPreview() {
    Android_study_25thTheme {
        SearchScreen(onPostClick = { })
    }
}

@Preview(name = "마이", showBackground = true, heightDp = 800)
@Composable
fun ProfileScreenPreview() {
    Android_study_25thTheme {
        ProfileScreen(onPostClick = { })
    }
}

@Preview(name = "게시물 상세", showBackground = true, heightDp = 800)
@Composable
fun DetailScreenPreview() {
    Android_study_25thTheme {
        DetailScreen(post = postList[0], onBack = { })
    }
}
