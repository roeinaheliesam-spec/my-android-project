package com.example.androidclasshub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Lesson(
    val id: Int,
    val title: String,
    val subtitle: String,
    val topics: List<String>
)

private val lessons = listOf(
    Lesson(1, "آشنایی با Android و Compose", "شروع توسعه اپلیکیشن اندروید", listOf(
        "Android چیست؟", "Android Studio", "Android SDK", "Emulator",
        "Kotlin", "Jetpack Compose", "ساخت اولین پروژه Compose"
    )),
    Lesson(2, "Kotlin مقدماتی", "پایه‌های زبان Kotlin برای Android", listOf(
        "ساختار Kotlin", "متغیرها", "انواع داده‌ها", "شرط‌ها", "توابع",
        "کلاس‌ها", "Data Class", "Collectionها", "Null Safety", "Lambda"
    )),
    Lesson(3, "رابط کاربری با Jetpack Compose", "ساخت اولین صفحه واقعی", listOf(
        "Declarative UI", "Composable Function", "Text", "Button",
        "Column / Row / Box", "Modifier", "dp و sp", "Theme", "Preview"
    )),
    Lesson(4, "State و Event", "ساخت UI تعاملی", listOf(
        "State", "mutableStateOf", "remember", "Recomposition",
        "Event Handling", "onClick", "onValueChange", "TextField State",
        "State Hoisting", "Unidirectional Data Flow", "Event → State → UI"
    )),
    Lesson(5, "Navigation", "ساخت چند صفحه در Compose", listOf(
        "NavHost", "NavController", "جابجایی بین صفحات", "ارسال اطلاعات"
    )),
    Lesson(6, "لیست داده‌ها", "نمایش داده‌های متعدد", listOf(
        "LazyColumn", "List", "Card", "Item UI"
    )),
    Lesson(7, "ذخیره داده", "نگهداری اطلاعات اپ", listOf(
        "DataStore", "Preferences", "ذخیره تنظیمات"
    )),
    Lesson(8, "Room DB", "کار با دیتابیس محلی", listOf(
        "Entity", "DAO", "Database", "CRUD"
    )),
    Lesson(9, "MVVM", "ساختاردهی حرفه‌ای پروژه", listOf(
        "ViewModel", "Repository", "UI State"
    )),
    Lesson(10, "پروژه نهایی", "جمع‌بندی دوره", listOf(
        "ترکیب مباحث", "ساخت اپ چندصفحه‌ای", "ذخیره داده", "ارائه پروژه"
    ))
)

private val Bg = Color(0xFF0E1016)
private val Card = Color(0xFF171B26)
private val Card2 = Color(0xFF1D2230)
private val Purple = Color(0xFF7F52FF)
private val Green = Color(0xFF3DDC84)
private val Muted = Color(0xFF93A0B8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AndroidClassHubApp() }
    }
}

@Composable
fun AndroidClassHubApp() {
    val nav = rememberNavController()
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Bg,
            surface = Card,
            primary = Purple,
            secondary = Green
        )
    ) {
        NavHost(navController = nav, startDestination = "home") {
            composable("home") { HomeScreen { nav.navigate("lesson/$it") } }
            composable("lesson/{id}") { entry ->
                val id = entry.arguments?.getString("id")?.toIntOrNull() ?: 1
                LessonScreen(lessons.first { it.id == id }, onBack = { nav.popBackStack() })
            }
        }
    }
}

@Composable
fun HomeScreen(onLessonClick: (Int) -> Unit) {
    Scaffold(containerColor = Bg) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text("🤖 Android Class Hub", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                    Text("مرجع شخصی دوره Android Fundamentals", color = Muted, fontSize = 15.sp)
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Card2),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("مسیر یادگیری", color = Green, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("از Android و Kotlin تا Compose، State، Navigation و پروژه نهایی.")
                        Spacer(Modifier.height(14.dp))
                        LinearProgressIndicator(
                            progress = { 0.4f },
                            modifier = Modifier.fillMaxWidth(),
                            color = Green,
                            trackColor = Bg
                        )
                        Spacer(Modifier.height(6.dp))
                        Text("۴ جلسه از ۱۰ جلسه آماده شده", color = Muted, fontSize = 12.sp)
                    }
                }
            }

            item {
                Text("جلسات", fontSize = 21.sp, fontWeight = FontWeight.Bold)
            }

            items(lessons) { lesson ->
                LessonCard(lesson, onLessonClick)
            }
        }
    }
}

@Composable
fun LessonCard(lesson: Lesson, onLessonClick: (Int) -> Unit) {
    val available = lesson.id <= 4
    Card(
        modifier = Modifier.fillMaxWidth().clickable(enabled = available) { onLessonClick(lesson.id) },
        colors = CardDefaults.cardColors(containerColor = Card),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(48.dp).background(
                    if (available) Purple else Card2,
                    RoundedCornerShape(14.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    if (available) lesson.id.toString() else "🔒",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(lesson.title, fontWeight = FontWeight.Bold)
                Text(lesson.subtitle, color = Muted, fontSize = 13.sp)
            }
            Text(if (available) "›" else "بعداً", color = Muted)
        }
    }
}

@Composable
fun LessonScreen(lesson: Lesson, onBack: () -> Unit) {
    Scaffold(
        containerColor = Bg,
        topBar = {
            TopAppBar(
                title = { Text("جلسه ${lesson.id}") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("بازگشت") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Bg)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Card2),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(Modifier.padding(22.dp)) {
                        Text("Android Fundamentals", color = Green, fontSize = 13.sp)
                        Spacer(Modifier.height(6.dp))
                        Text(lesson.title, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                        Spacer(Modifier.height(6.dp))
                        Text(lesson.subtitle, color = Muted)
                    }
                }
            }

            item { Text("مباحث این جلسه", fontSize = 20.sp, fontWeight = FontWeight.Bold) }

            items(lesson.topics) { topic ->
                Row(
                    Modifier.fillMaxWidth().background(Card, RoundedCornerShape(12.dp)).padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("✓", color = Green, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(12.dp))
                    Text(topic)
                }
            }

            if (lesson.id == 4) {
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = Card2)) {
                        Column(Modifier.padding(18.dp)) {
                            Text("فرمول مهم جلسه", color = Purple, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            Text("Event → State → UI", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(5.dp))
                            Text("این جلسه روی ساخت UI تعاملی و مدیریت State و Event تمرکز دارد.", color = Muted)
                        }
                    }
                }
            }
        }
    }
}
