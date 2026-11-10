package com.android_study_25th

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android_study_25th.ui.theme.Android_study_25thTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_study_25thTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FeedScreen(
                        post = samplePost,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
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

val samplePost = PostData(
    userName = "gks_gpdls_",
    location = "우리집 거실",
    content = "귀여운 뚱냥이",
    imageRes = R.drawable.sample_cat,
    likeCount = 256,
    commentCount = 31,
    time = "1시간 전"
)

@Composable
fun FeedScreen(post: PostData, modifier: Modifier = Modifier) {
    var liked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
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

        // 게시물 이미지
        Image(
            painter = painterResource(id = post.imageRes),
            contentDescription = "게시물 사진",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

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
                    tint = if (liked) Color.Red else Color.Black
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

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    Android_study_25thTheme {
        FeedScreen(post = samplePost)
    }
}
