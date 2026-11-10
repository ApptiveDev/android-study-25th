package com.android_study_25th

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android_study_25th.ui.theme.Android_study_25thTheme

data class PostData(
    val userName: String,
    val petName: String,
    val petAge: String,
    val petType: String,
    val postedAt: String,
    val content: String,
    val snackInfo: String,
    val tags: List<String>,
    @DrawableRes val imageRes: Int? = null,
    @DrawableRes val profileImageRes: Int? = null,
    val likes: Int,
    val comments: Int
)

data class CommentData(
    val postUserName: String,
    val userName: String,
    val content: String
)

data class SnackData(
    val name: String,
    val petType: String,
    val review: String,
    val rating: String,
    @DrawableRes val imageRes: Int? = null
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Android_study_25thTheme {
                PetFeedScreen()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetFeedScreen() {
    // ===== 여기에서 앱 이름과 메인 색상 수정 =====
    val appName = "momo."
    val oatmeal = Color(0xFFF5F1E6)
    val pistachio = Color(0xFFBAC7A6)
    val tomato = Color(0xFFD56D53)
    val olive = Color(0xFF41463C)
    val muted = Color(0xFF73776C)
    val softGreen = Color(0xFFE8ECDD)

    // ===== 여기에서 게시물 정보 수정 =====
    // 목록의 첫 카드가 화면 맨 위에 표시됩니다.
    // 이름, 나이, 본문, 반응 수는 임시 값이며 여기에서 바꿀 수 있습니다.
    val posts = listOf(
        PostData(
            userName = "spam",
            petName = "스팸",
            petAge = "나이 미입력",
            petType = "햄스터",
            postedAt = "방금 전",
            content = "오늘의 일상을 공유해요.",
            snackInfo = "",
            tags = listOf("햄스터", "일상"),
            imageRes = R.drawable.animal_hamster_spam,
            profileImageRes = null,
            likes = 13,
            comments = 0
        ),
        PostData(
            userName = "bada",
            petName = "바다",
            petAge = "나이 미입력",
            petType = "강아지",
            postedAt = "방금 전",
            content = "오늘의 일상을 공유해요.",
            snackInfo = "",
            tags = listOf("강아지", "일상"),
            imageRes = R.drawable.animal_dog_bada,
            profileImageRes = null,
            likes = 2,
            comments = 0
        ),
        PostData(
            userName = "nana",
            petName = "nana",
            petAge = "나이 미입력",
            petType = "강아지",
            postedAt = "방금 전",
            content = "오늘의 일상을 공유해요.",
            snackInfo = "",
            tags = listOf("강아지", "일상"),
            imageRes = R.drawable.animal_dog_nana,
            profileImageRes = null,
            likes = 20,
            comments = 0
        ),
        PostData(
            userName = "turtle",
            petName = "turtle",
            petAge = "나이 미입력",
            petType = "거북이",
            postedAt = "방금 전",
            content = "오늘의 일상을 공유해요.",
            snackInfo = "",
            tags = listOf("거북이", "일상"),
            imageRes = R.drawable.animal_turtle_turtle,
            profileImageRes = null,
            likes = 9,
            comments = 0
        ),
        PostData(
            userName = "creamheroes",
            petName = "creamheroes",
            petAge = "나이 미입력",
            petType = "고양이",
            postedAt = "방금 전",
            content = "오늘의 일상을 공유해요.",
            snackInfo = "",
            tags = listOf("고양이", "일상"),
            imageRes = R.drawable.animal_cat_ruru,
            profileImageRes = null,
            likes = 7,
            comments = 0
        ),
        PostData(
            userName = "april",
            petName = "april",
            petAge = "나이 미입력",
            petType = "강아지",
            postedAt = "방금 전",
            content = "오늘의 일상을 공유해요.",
            snackInfo = "",
            tags = listOf("강아지", "일상"),
            imageRes = R.drawable.animal_dog_april,
            profileImageRes = null,
            likes = 7,
            comments = 0
        ),
        PostData(
            userName = "creamheros",
            petName = "creamheros",
            petAge = "나이 미입력",
            petType = "고양이",
            postedAt = "방금 전",
            content = "오늘의 일상을 공유해요.",
            snackInfo = "",
            tags = listOf("고양이", "일상"),
            imageRes = R.drawable.animal_cat_chuchu,
            profileImageRes = null,
            likes = 2,
            comments = 0
        ),
        PostData(
            userName = "torry",
            petName = "torry",
            petAge = "나이 미입력",
            petType = "강아지",
            postedAt = "방금 전",
            content = "오늘의 일상을 공유해요.",
            snackInfo = "",
            tags = listOf("강아지", "일상"),
            imageRes = R.drawable.animal_dog_torry,
            profileImageRes = null,
            likes = 0,
            comments = 0
        )
    )

    val categories = listOf(
        "전체" to "🐾",
        "강아지" to "🐶",
        "고양이" to "🐱",
        "토끼" to "🐰",
        "햄스터" to "🐹",
        "새" to "🐦",
        "파충류" to "🦎",
        "기타" to "🌿"
    )

    // ===== 여기에서 간식 커뮤니티의 샘플 정보 수정 =====
    val snacks = listOf(
        SnackData(
            name = "말한입칩",
            petType = "강아지",
            review = "후기 미등록",
            rating = "평가 전",
            imageRes = R.drawable.food_horse1
        ),
        SnackData(
            name = "명태깡",
            petType = "강아지",
            review = "후기 미등록",
            rating = "평가 전",
            imageRes = R.drawable.food_myeongtae1
        ),
        SnackData(
            name = "스마일독",
            petType = "강아지",
            // UI용 예시 후기와 임시 별점입니다. 실제 리뷰에 맞게 수정하세요.
            review = "매일 먹는 사료로 골라봤어요. 꾸준히 급여하며 반응을 살펴보려고 해요.",
            rating = "4.5",
            imageRes = R.drawable.food_food2
        ),
        SnackData(
            name = "잘먹잘싸",
            petType = "강아지",
            // UI용 예시 후기와 임시 별점입니다. 실제 리뷰에 맞게 수정하세요.
            review = "새로운 사료로 바꿔봤어요. 기존 사료와 조금씩 섞어 적응 중이에요.",
            rating = "4.0",
            imageRes = R.drawable.food_food1
        ),
        SnackData("바삭한 연어 큐브", "강아지", "산책 후 한 입 보상으로 좋아요", "4.8"),
        SnackData("촉촉한 참치 스틱", "고양이", "입맛 까다로운 나비도 잘 먹어요", "4.9"),
        SnackData("고소한 닭가슴살", "강아지", "작게 잘라 훈련 간식으로 줘요", "4.7"),
        SnackData("동결건조 북어", "고양이", "바삭한 식감이 마음에 드나 봐요", "4.8"),
        SnackData("달콤한 고구마 말랭이", "강아지", "천천히 씹어 먹는 오후 간식", "4.6")
    )
    // 실행 중 등록한 댓글입니다. 홈과 커뮤니티를 오가도 유지됩니다.
    val addedComments = remember { mutableStateListOf<CommentData>() }

    var selectedMenu by remember { mutableStateOf("홈") }
    var snackCategory by remember { mutableStateOf("전체") }
    var snackSearch by remember { mutableStateOf("") }

    // 홈은 전체 피드만 표시합니다. 동물별 분류는 아직 사용하지 않습니다.
    val selectedCategory = "전체"

    // core에 없는 아이콘은 같은 파일 안에서 벡터로 정의합니다.
    // 추가 이미지 파일이나 아이콘 라이브러리가 필요 없습니다.
    val paw = remember {
        ImageVector.Builder("Paw", 24.dp, 24.dp, 24f, 24f).apply {
            addPath(
                PathParser().parsePathString(
                    "M12,10 C9,10 8,14 5,16 C2,20 6,23 9,21 C11,20 13,20 15,21 C18,23 22,20 19,16 C16,14 15,10 12,10 Z " +
                            "M5,5 C2,5 2,11 5,11 C8,11 8,5 5,5 Z M10,1 C7,1 7,8 10,8 C13,8 13,1 10,1 Z " +
                            "M16,2 C13,2 13,9 16,9 C19,9 19,2 16,2 Z M21,7 C18,7 18,13 21,13 C24,13 24,7 21,7 Z"
                ).toNodes(),
                fill = SolidColor(Color.Black)
            )
        }.build()
    }

    val comment = remember {
        ImageVector.Builder("Comment", 24.dp, 24.dp, 24f, 24f).apply {
            addPath(
                PathParser().parsePathString(
                    "M20,11 C20,6 17,3 12,3 C7,3 3,6 3,11 C3,16 7,19 12,19 L16,22 L16,18 C19,17 20,14 20,11 Z"
                ).toNodes(),
                fill = null,
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.6f
            )
        }.build()
    }

    val bookmark = remember {
        ImageVector.Builder("Bookmark", 24.dp, 24.dp, 24f, 24f).apply {
            addPath(
                PathParser().parsePathString(
                    "M6,3 L18,3 L18,21 L12,17 L6,21 Z"
                ).toNodes(),
                fill = null,
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.6f
            )
        }.build()
    }

    val compass = remember {
        ImageVector.Builder("Explore", 24.dp, 24.dp, 24f, 24f).apply {
            addPath(
                PathParser().parsePathString(
                    "M22,12 A10,10 0,1 1,2 12 A10,10 0,1 1,22 12 Z M16,8 L13,13 L8,16 L11,11 Z"
                ).toNodes(),
                fill = null,
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.5f
            )
        }.build()
    }

    // 시스템 테마와 관계없이 이번 과제의 색상 팔레트를 사용합니다.
    CompositionLocalProvider(LocalContentColor provides olive) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = oatmeal,
            contentColor = olive,

            // ===== 하단 네비게이션 =====
            bottomBar = {
                Surface(
                    color = oatmeal,
                    shadowElevation = 3.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val menu = listOf(
                            "홈" to Icons.Default.Home,
                            "탐색" to compass,
                            "작성" to Icons.Default.Add,
                            "커뮤니티" to paw,
                            "마이" to Icons.Default.Person
                        )

                        menu.forEach { (label, icon) ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // 홈과 커뮤니티만 같은 함수 안에서 화면을 전환합니다.
                                IconButton(
                                    onClick = {
                                        if (label == "홈" || label == "커뮤니티") {
                                            selectedMenu = label
                                        }
                                    },
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(
                                            color = if (label == "작성") {
                                                tomato
                                            } else {
                                                Color.Transparent
                                            },
                                            shape = RoundedCornerShape(18.dp)
                                        )
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        tint = if (label == "작성") {
                                            Color.White
                                        } else if (label == selectedMenu) {
                                            olive
                                        } else {
                                            muted
                                        },
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    color = if (label == selectedMenu) olive else muted,
                                    fontWeight = if (label == selectedMenu) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    }
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 20.dp)
            ) {
                if (selectedMenu == "커뮤니티") {
                    // ===== 간식 커뮤니티 =====
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "간식 커뮤니티",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "우리 아이가 좋아하는 간식을 나눠요",
                            fontSize = 13.sp,
                            color = muted
                        )
                        OutlinedTextField(
                            value = snackSearch,
                            onValueChange = { snackSearch = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("어떤 간식을 찾고 있나요?", fontSize = 13.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = null)
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(28.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = pistachio,
                                unfocusedBorderColor = pistachio,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = olive,
                                unfocusedTextColor = olive,
                                focusedLeadingIconColor = olive,
                                unfocusedLeadingIconColor = muted,
                                focusedPlaceholderColor = muted,
                                unfocusedPlaceholderColor = muted,
                                cursorColor = tomato
                            )
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("전체", "강아지", "고양이").forEach { category ->
                                FilterChip(
                                    selected = snackCategory == category,
                                    onClick = { snackCategory = category },
                                    label = { Text(category) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = softGreen,
                                        labelColor = olive,
                                        selectedContainerColor = tomato,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                        val visibleSnacks = snacks.filter { snack ->
                            (snackCategory == "전체" || snack.petType == snackCategory) &&
                                (snack.name.contains(snackSearch, ignoreCase = true) ||
                                    snack.review.contains(snackSearch, ignoreCase = true))
                        }
                        if (visibleSnacks.isEmpty()) {
                            Text("검색한 간식이 없어요.", color = muted, fontSize = 14.sp)
                        }
                        visibleSnacks.forEach { snack ->
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(18.dp),
                                color = Color.White,
                                contentColor = olive
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    val snackImage = snack.imageRes
                                    if (snackImage != null) {
                                        Image(
                                            painter = painterResource(snackImage),
                                            contentDescription = snack.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.size(64.dp)
                                                .clip(RoundedCornerShape(14.dp))
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier.size(64.dp)
                                                .background(pistachio, RoundedCornerShape(14.dp)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(paw, contentDescription = "간식 사진 없음",
                                                modifier = Modifier.size(30.dp), tint = olive)
                                        }
                                    }
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(snack.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Star, contentDescription = "평점",
                                                tint = tomato, modifier = Modifier.size(15.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("${snack.rating} · ${snack.petType}",
                                                fontSize = 11.sp, color = muted)
                                        }
                                        Text(
                                            text = snack.review,
                                            fontSize = 11.sp,
                                            color = muted,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    // 상세 화면은 이번 과제에서 구현하지 않습니다.
                                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null,
                                        modifier = Modifier.size(20.dp), tint = olive)
                                }
                            }
                        }
                    }
                } else {
                    // ===== 상단 헤더 =====
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 22.dp,
                                end = 12.dp,
                                top = 12.dp,
                                bottom = 12.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = paw,
                            contentDescription = null,
                            modifier = Modifier.size(27.dp)
                        )

                        Spacer(Modifier.width(7.dp))

                        Text(
                            text = appName,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-1).sp
                        )

                        Spacer(Modifier.weight(1f))

                        Row(
                            modifier = Modifier.background(
                                softGreen,
                                RoundedCornerShape(24.dp)
                            )
                        ) {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "검색"
                                )
                            }

                            IconButton(onClick = {}) {
                                Box {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = "알림"
                                    )

                                    Box(
                                        modifier = Modifier
                                            .size(5.dp)
                                            .align(Alignment.TopEnd)
                                            .background(tomato, CircleShape)
                                    )
                                }
                            }
                        }
                    }

                    // ===== 가로 스크롤 카테고리 =====
                    // 이 Row만 가로로 움직입니다.
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        categories.forEach { (name, emoji) ->
                            val selected = selectedCategory == name

                            Column(
                                modifier = Modifier.width(62.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                FilledIconButton(
                                    onClick = {}, // 동물별 필터링 없이 전체 피드를 유지합니다.
                                    modifier = Modifier.size(58.dp),
                                    shape = CircleShape,
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = if (selected) tomato else softGreen,
                                        contentColor = if (selected) Color.White else olive
                                    )
                                ) {
                                    if (name == "전체") {
                                        Icon(
                                            imageVector = paw,
                                            contentDescription = "전체",
                                            modifier = Modifier.size(26.dp)
                                        )
                                    } else {
                                        Text(
                                            text = emoji,
                                            fontSize = 25.sp
                                        )
                                    }
                                }

                                Spacer(Modifier.height(6.dp))

                                Text(
                                    text = name,
                                    fontSize = 12.sp,
                                    color = if (selected) tomato else olive,
                                    fontWeight = if (selected) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Medium
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    // 같은 카드 UI를 반복하며, 게시물마다 쓰담쓰담 상태를 따로 기억합니다.
                    posts.forEach { post ->
                        var isPetted by remember { mutableStateOf(false) }
                        var showComments by remember { mutableStateOf(false) }
                        var commentInput by remember { mutableStateOf("") }
                        val postComments = addedComments.filter {
                            it.postUserName == post.userName
                        }

                        if (showComments) {
                            AlertDialog(
                                onDismissRequest = { showComments = false },
                                containerColor = oatmeal,
                                titleContentColor = olive,
                                textContentColor = olive,
                                title = { Text("${post.petName}에게 댓글 남기기") },
                                text = {
                                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                        Column(
                                            modifier = Modifier.fillMaxWidth()
                                                .heightIn(max = 180.dp)
                                                .verticalScroll(rememberScrollState()),
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            if (postComments.isEmpty()) {
                                                Text("첫 댓글을 남겨 주세요!", color = muted)
                                            }
                                            postComments.forEach { item ->
                                                Text("${item.userName}: ${item.content}")
                                            }
                                        }
                                        OutlinedTextField(
                                            value = commentInput,
                                            onValueChange = { commentInput = it },
                                            modifier = Modifier.fillMaxWidth(),
                                            label = { Text("댓글 입력") },
                                            minLines = 2,
                                            maxLines = 4,
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedTextColor = olive,
                                                unfocusedTextColor = olive,
                                                focusedBorderColor = tomato,
                                                unfocusedBorderColor = muted,
                                                focusedLabelColor = olive,
                                                unfocusedLabelColor = muted,
                                                cursorColor = tomato
                                            )
                                        )
                                    }
                                },
                                confirmButton = {
                                    TextButton(
                                        enabled = commentInput.isNotBlank(),
                                        onClick = {
                                            addedComments.add(CommentData(
                                                postUserName = post.userName,
                                                userName = "나",
                                                content = commentInput.trim()
                                            ))
                                            commentInput = ""
                                        },
                                        colors = ButtonDefaults.textButtonColors(contentColor = tomato)
                                    ) { Text("등록") }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showComments = false }) {
                                        Text("닫기", color = olive)
                                    }
                                }
                            )
                        }
                        // ===== 게시물 카드 =====
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            contentColor = olive
                        ) {
                            Column(Modifier.padding(14.dp)) {
                                // 프로필과 게시물 정보
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val profileImage = post.profileImageRes

                                    if (profileImage != null) {
                                        Image(
                                            painter = painterResource(profileImage),
                                            contentDescription = "${post.petName} 프로필 사진",
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(44.dp)
                                                .background(pistachio, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = paw,
                                                contentDescription = "프로필 사진 없음",
                                                modifier = Modifier.size(23.dp)
                                            )
                                        }
                                    }

                                    Spacer(Modifier.width(10.dp))

                                    Column(Modifier.weight(1f)) {
                                        Text(
                                            text = post.userName,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            text = "${post.petName} · ${post.petAge} · ${post.petType}",
                                            fontSize = 11.sp,
                                            color = muted
                                        )

                                        Text(
                                            text = post.postedAt,
                                            fontSize = 10.sp,
                                            color = muted
                                        )
                                    }

                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Default.MoreVert,
                                            contentDescription = "게시물 더보기"
                                        )
                                    }
                                }

                                Spacer(Modifier.height(12.dp))

                                // 게시물 사진 또는 플레이스홀더
                                val photo = post.imageRes

                                if (photo != null) {
                                    Image(
                                        painter = painterResource(photo),
                                        contentDescription = "${post.petName}의 게시물 사진",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1.15f)
                                            .clip(RoundedCornerShape(18.dp))
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1.15f)
                                            .background(
                                                Color(0xFFF0F0E9),
                                                RoundedCornerShape(18.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(72.dp)
                                                    .background(
                                                        pistachio.copy(alpha = 0.5f),
                                                        CircleShape
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = paw,
                                                    contentDescription = null,
                                                    tint = muted,
                                                    modifier = Modifier.size(34.dp)
                                                )
                                            }

                                            Spacer(Modifier.height(12.dp))

                                            Text(
                                                text = "반려동물 사진",
                                                fontSize = 14.sp,
                                                color = muted
                                            )
                                        }
                                    }
                                }

                                // 쓰담쓰담, 댓글, 공유, 저장
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconToggleButton(
                                        checked = isPetted,
                                        onCheckedChange = { isPetted = it }
                                    ) {
                                        Icon(
                                            imageVector = paw,
                                            contentDescription = if (isPetted) "쓰담쓰담 취소" else "쓰담쓰담",
                                            tint = if (isPetted) tomato else muted
                                        )
                                    }

                                    IconButton(onClick = { showComments = true }) {
                                        Icon(
                                            imageVector = comment,
                                            contentDescription = "댓글"
                                        )
                                    }

                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.Send,
                                            contentDescription = "공유"
                                        )
                                    }

                                    Spacer(Modifier.weight(1f))

                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = bookmark,
                                            contentDescription = "저장"
                                        )
                                    }
                                }

                                Text(
                                    text = "쓰담쓰담 ${post.likes + if (isPetted) 1 else 0}회",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(Modifier.height(5.dp))

                                // 게시물 본문
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            SpanStyle(fontWeight = FontWeight.Bold)
                                        ) {
                                            append(post.userName)
                                        }
                                        append("  ")
                                        append(post.content)
                                    },
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp
                                )

                                Spacer(Modifier.height(12.dp))

                                // 간식 정보와 해시태그
                                // 화면이 좁으면 다음 줄로 넘어갑니다.
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    if (post.snackInfo.isNotBlank()) {
                                        Row(
                                            modifier = Modifier
                                                .background(
                                                    softGreen,
                                                    RoundedCornerShape(20.dp)
                                                )
                                                .padding(
                                                    horizontal = 10.dp,
                                                    vertical = 6.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = paw,
                                                contentDescription = null,
                                                modifier = Modifier.size(12.dp)
                                            )

                                            Spacer(Modifier.width(4.dp))

                                            Text(
                                                text = post.snackInfo,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }

                                    post.tags.forEachIndexed { index, tag ->
                                        Text(
                                            text = "#$tag",
                                            fontSize = 11.sp,
                                            color = if (index == 0) {
                                                Color(0xFF934A38)
                                            } else {
                                                olive
                                            },
                                            modifier = Modifier
                                                .background(
                                                    color = if (index == 0) {
                                                        Color(0xFFF7E3DC)
                                                    } else {
                                                        softGreen
                                                    },
                                                    shape = RoundedCornerShape(20.dp)
                                                )
                                                .padding(
                                                    horizontal = 10.dp,
                                                    vertical = 6.dp
                                                )
                                        )
                                    }
                                }

                                Spacer(Modifier.height(12.dp))

                                Text(
                                    text = "댓글 ${post.comments + postComments.size}개 모두 보기",
                                    modifier = Modifier.clickable { showComments = true }
                                        .padding(vertical = 8.dp),
                                    fontSize = 11.sp,
                                    color = muted
                                )

                                // 등록한 댓글은 해당 게시물 카드에도 바로 표시합니다.
                                postComments.forEach { item ->
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append(item.userName)
                                            }
                                            append("  ")
                                            append(item.content)
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        fontSize = 12.sp,
                                        lineHeight = 18.sp,
                                        color = olive
                                    )
                                }

                                Spacer(Modifier.height(4.dp))
                            }
                        }

                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun PetFeedPreview() {
    Android_study_25thTheme {
        PetFeedScreen()
    }
}