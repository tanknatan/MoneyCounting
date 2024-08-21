package com.my.moneycounting.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import java.util.*

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
            .background(Color.Black)
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
        ) {
            items(newsArticles) { article ->
                NewsItem(article = article)
            }
        }

        // Status Bar at the top
        StatusBar3(
            modifier = Modifier
                .align(Alignment.TopCenter),
            onBackClick = onBackClick
        )

        // Bottom Navigation Bar at the bottom
        BottomNavigationBar3(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onItemSelected = { },
            onSettingsClick = onSettingsClick,
            onReportClick = onReportClick,
            onBankClick = onBankClick
        )
    }
}

@Composable
fun StatusBar3(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Button",
            modifier = Modifier
                .size(40.dp)
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Title
        Text(
            text = "Finance News",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 68.dp)
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
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
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
            fontWeight = FontWeight.Bold,
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



@Composable
fun BottomNavigationBar3(
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onReportClick: () -> Unit,
    onBankClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp) // Add padding from the bottom of the screen
            .padding(horizontal = 60.dp)
            .height(64.dp) // Set the height of the navigation bar
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(50.dp) // Rounded corners
            ),
        horizontalArrangement = Arrangement.SpaceEvenly, // Distribute items evenly
        verticalAlignment = Alignment.CenterVertically
    ) {
        val items = listOf(
            Pair(R.drawable.ic_report, "Report"),
            Pair(R.drawable.ic_bank, "Bank"),
            Pair(R.drawable.ic_notification_act, "Notifications"),
            Pair(R.drawable.ic_settings, "Settings")
        )

        items.forEach { (imageRes, label) ->
            val isSelected = label == "Notifications"

            IconButton(
                modifier = Modifier
                    .size(45.dp)
                    .background(
                        if (isSelected) Color(0xFFFCF485) else Color.Transparent,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                onClick = {
                        when (label) {
                            "Report" -> onReportClick()
                            "Bank" -> onBankClick()
                            "Settings" -> onSettingsClick()
                            else -> onItemSelected(label)
                        }
                    },
            ) {
                Icon(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    modifier = Modifier.size(43.dp), // Adjust the size to fit within the background,
                    tint = Color.Unspecified
                )
            }
        }
    }
}