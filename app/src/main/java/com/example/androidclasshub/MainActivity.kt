package com.example.androidclasshub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
private val CardColor = Color(0xFF171B26)
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
            surface = CardColor,
            primary = Purple,
            secondary = Green
        )
    ) {
        NavHost(navController = nav, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    onLessonClick = { nav.navigate("lesson/$it") },
                    onAssignmentClick = { nav.navigate("assignment") },
                    onWirelessClick = { nav.navigate("wireless") }
                )
            }
            composable("lesson/{id}") { entry ->
                val id = entry.arguments?.getString("id")?.toIntOrNull() ?: 1
                val lesson = lessons.firstOrNull { it.id == id }
                if (lesson != null) {
                    LessonScreen(lesson = lesson, onBack = { nav.popBackStack() })
                }
            }
            composable("assignment") {
                ControlsAssignmentScreen(onBack = { nav.popBackStack() })
            }
            composable("wireless") {
                WirelessDebuggingScreen(onBack = { nav.popBackStack() })
            }
        }
    }
}

@Composable
fun HomeScreen(
    onLessonClick: (Int) -> Unit,
    onAssignmentClick: () -> Unit,
    onWirelessClick: () -> Unit
) {
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
                Card(colors = CardDefaults.cardColors(containerColor = Card2), shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(20.dp)) {
                        Text("تکالیف اجباری", color = Green, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("دو تکلیف اجباری دوره را از اینجا اجرا و مرور کن.")
                        Spacer(Modifier.height(14.dp))
                        Button(onClick = onAssignmentClick, modifier = Modifier.fillMaxWidth()) {
                            Text("تکلیف ۱: CheckBox + RadioButton + Switch")
                        }
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = onWirelessClick, modifier = Modifier.fillMaxWidth()) {
                            Text("تکلیف ۲: Wireless Debugging")
                        }
                    }
                }
            }

            item {
                Card(colors = CardDefaults.cardColors(containerColor = Card2), shape = RoundedCornerShape(20.dp)) {
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

            item { Text("جلسات", fontSize = 21.sp, fontWeight = FontWeight.Bold) }
            items(lessons) { lesson -> LessonCard(lesson, onLessonClick) }
        }
    }
}

@Composable
fun LessonCard(lesson: Lesson, onLessonClick: (Int) -> Unit) {
    val available = lesson.id <= 4
    Card(
        modifier = Modifier.fillMaxWidth().clickable(enabled = available) { onLessonClick(lesson.id) },
        colors = CardDefaults.cardColors(containerColor = CardColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(48.dp).background(if (available) Purple else Card2, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(if (available) lesson.id.toString() else "🔒", fontWeight = FontWeight.ExtraBold, color = Color.White)
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
                navigationIcon = { TextButton(onClick = onBack) { Text("بازگشت") } },
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
                Card(colors = CardDefaults.cardColors(containerColor = Card2), shape = RoundedCornerShape(20.dp)) {
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
                    Modifier.fillMaxWidth().background(CardColor, RoundedCornerShape(12.dp)).padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("✓", color = Green, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(12.dp))
                    Text(topic)
                }
            }
        }
    }
}

@Composable
fun ControlsAssignmentScreen(onBack: () -> Unit) {
    var checkBoxChecked by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("گزینه ۱") }
    var switchEnabled by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Bg,
        topBar = {
            TopAppBar(
                title = { Text("تکلیف ۱") },
                navigationIcon = { TextButton(onClick = onBack) { Text("بازگشت") } },
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
                Card(colors = CardDefaults.cardColors(containerColor = Card2), shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(20.dp)) {
                        Text("سه کنترل تعاملی", color = Green, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("CheckBox، RadioButton و Switch به‌صورت واقعی با State در Kotlin/Compose پیاده‌سازی شده‌اند.")
                    }
                }
            }

            item {
                ControlCard(title = "۱) CheckBox") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = checkBoxChecked, onCheckedChange = { checkBoxChecked = it })
                        Text(if (checkBoxChecked) "فعال است" else "غیرفعال است")
                    }
                }
            }

            item {
                ControlCard(title = "۲) RadioButton") {
                    Column {
                        listOf("گزینه ۱", "گزینه ۲", "گزینه ۳").forEach { option ->
                            Row(
                                modifier = Modifier.fillMaxWidth().clickable { selectedOption = option }.padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedOption == option,
                                    onClick = { selectedOption = option }
                                )
                                Text(option)
                            }
                        }
                        Text("انتخاب فعلی: $selectedOption", color = Green, fontWeight = FontWeight.Bold)
                    }
                }
            }

            item {
                ControlCard(title = "۳) Switch") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (switchEnabled) "روشن" else "خاموش")
                        Switch(checked = switchEnabled, onCheckedChange = { switchEnabled = it })
                    }
                }
            }

            item {
                Card(colors = CardDefaults.cardColors(containerColor = Card2)) {
                    Column(Modifier.padding(18.dp)) {
                        Text("وضعیت نهایی", color = Purple, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("CheckBox: ${if (checkBoxChecked) "فعال" else "غیرفعال"}")
                        Text("RadioButton: $selectedOption")
                        Text("Switch: ${if (switchEnabled) "روشن" else "خاموش"}")
                    }
                }
            }
        }
    }
}

@Composable
fun ControlCard(title: String, content: @Composable () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(18.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun WirelessDebuggingScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = Bg,
        topBar = {
            TopAppBar(
                title = { Text("تکلیف ۲") },
                navigationIcon = { TextButton(onClick = onBack) { Text("بازگشت") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Bg)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Card2), shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(20.dp)) {
                        Text("Wireless Debugging", color = Green, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(Modifier.height(10.dp))
                        Text("این تکلیف باید روی گوشی و Android Studio انجام شود؛ کد Kotlin خاصی برای فعال‌کردن آن داخل برنامه وجود ندارد.")
                    }
                }
            }
            item { StepCard(1, "گوشی و کامپیوتر را به یک Wi‑Fi وصل کن.") }
            item { StepCard(2, "در گوشی Developer options را فعال کن.") }
            item { StepCard(3, "داخل Developer options، گزینه Wireless debugging را روشن کن.") }
            item { StepCard(4, "در Android Studio پنجره Device Manager را باز کن و Pair using Wi‑Fi را انتخاب کن.") }
            item { StepCard(5, "کد Pairing نمایش‌داده‌شده روی گوشی را در Android Studio وارد کن.") }
            item { StepCard(6, "بعد از Pair شدن، گوشی در فهرست دستگاه‌های اجرای Android Studio نمایش داده می‌شود؛ برنامه را Run کن.") }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = CardColor)) {
                    Column(Modifier.padding(18.dp)) {
                        Text("نکته", color = Purple, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        Text("اگر دستگاه در Android Studio دیده نشد، Wi‑Fi، روشن بودن Wireless debugging و یکسان بودن شبکه را بررسی کن.", color = Muted)
                    }
                }
            }
        }
    }
}

@Composable
fun StepCard(number: Int, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth().background(CardColor, RoundedCornerShape(14.dp)).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.size(38.dp).background(Purple, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(number.toString(), color = Color.White, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(12.dp))
        Text(text, modifier = Modifier.weight(1f))
    }
}
