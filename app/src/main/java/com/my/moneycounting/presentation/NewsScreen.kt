package com.my.moneycounting.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.my.moneycounting.R
import com.my.moneycounting.data.NewsArticle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onReportClick: () -> Unit,
    onBankClick: () -> Unit
) {
    val newsArticles by viewModel.newsArticles.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ) {
        // News list content
        LazyColumn(
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.fillMaxSize()
                .padding(top = 16.dp)
        ) {
            items(newsArticles) { article ->
                NewsItem(article = article)
            }
        }

        // Status Bar at the top
        StatusBar(
            info = "Finance News",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(black),
            onBackClick = onBackClick
        )

        // Bottom Navigation Bar at the bottom
        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            selectedItem = "Notifications",
            onSettingsClick = onSettingsClick,
            onReportClick = onReportClick,
            onBankClick = onBankClick,
            onNotificationClick = { }
        )
    }
}




@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsItem(article: NewsArticle) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val date = Date(article.datetime * 1000)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF1C1C1E), shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = rememberImagePainter(article.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Date Badge
        Text(
            text = dateFormat.format(date),
            color = black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier
                .padding(start = 16.dp)
                .background(Color(0xFFA7F3D0), shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Headline
        Text(
            text = article.headline,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Read more button
        IconButton(
            modifier = Modifier
                .align(Alignment.Start)  // Align the button to the left
                .padding(16.dp)
                .width(123.dp)
                .height(40.dp),
            onClick = {
                // Open the article URL in the browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context.startActivity(intent)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_read_more_button),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}