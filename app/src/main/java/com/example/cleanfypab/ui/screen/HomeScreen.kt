package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes
import com.example.cleanfypab.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    nav: NavHostController,
    vm: HomeViewModel = viewModel()
) {
    val ui by vm.state.collectAsState()
    LaunchedEffect(Unit) { vm.load() }

    /* ===== PALET WARNA ===== */
    val bgGradient = Brush.verticalGradient(listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE)))
    val card = Color.White
    val primaryGreen = Color(0xFF2ECC71)
    val blue = Color(0xFF4DA3FF)
    val orange = Color(0xFFFFA000)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)
    val borderSoft = Color(0xFFE6EAE8)

    val screenPadding = 16.dp
    val sectionSpacing = 16.dp
    val cardRadius = 18.dp

    val laporanMasukCount = ui.recentReports.count { it.status != "DONE" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .statusBarsPadding()
            .padding(horizontal = screenPadding),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(sectionSpacing)
    ) {

        /* ================= HEADER ================= */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Selamat Pagi, Olivia",
                        color = darkText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = (-0.2).sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Semoga harimu produktif",
                        color = grayText,
                        fontSize = 13.sp
                    )
                }

                Surface(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { nav.navigate(Routes.NOTIFICATION) },
                    shape = CircleShape,
                    color = card,
                    tonalElevation = 1.dp,
                    shadowElevation = 1.dp,
                    border = BorderStroke(1.dp, borderSoft)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            tint = darkText,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        /* ================= TUGAS HARI INI ================= */
        item {
            SectionHeader(
                title = "Tugas Hari Ini",
                actionText = "Lihat Semua",
                actionColor = primaryGreen
            ) { nav.navigate(Routes.TASK_TODAY) }
        }

        if (ui.loading) {
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        ui.error?.let { err ->
            item { Text(err, color = Color.Red, fontSize = 13.sp) }
        }

        if (!ui.loading && ui.todayTasks.isEmpty()) {
            item { Text("Belum ada tugas hari ini.", color = grayText, fontSize = 13.sp) }
        } else {
            items(ui.todayTasks.size) { idx ->
                val t = ui.todayTasks[idx]
                val indicator = when (t.status) {
                    "IN_PROGRESS" -> blue
                    "DONE" -> primaryGreen
                    else -> orange
                }
                TaskCard(
                    title = t.roomName,
                    subtitle = when (t.status) {
                        "IN_PROGRESS" -> "Sedang dikerjakan • ${t.timeLabel}"
                        "DONE" -> "Selesai • ${t.timeLabel}"
                        else -> "Menunggu • ${t.timeLabel}"
                    },
                    indicatorColor = indicator,
                    radius = cardRadius,
                    borderColor = borderSoft
                )
            }
        }

        /* ================= PROGRES ================= */
        item {
            val total = (ui.todayTasks.size).coerceAtLeast(1)
            val done = ui.todayTasks.count { it.status == "DONE" }
            val progress = (done.toFloat() / total.toFloat()).coerceIn(0f, 1f)

            ProgressCard(
                doneText = "$done/$total ruangan",
                percentText = "${(progress * 100).toInt()}%",
                progress = progress,
                primary = primaryGreen,
                darkText = darkText,
                grayText = grayText,
                card = card,
                border = borderSoft,
                radius = cardRadius
            )
        }

        /* ================= TOMBOL AKSI ================= */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionCard(
                    modifier = Modifier.weight(1f),
                    background = primaryGreen,
                    border = null,
                    radius = cardRadius,
                    onClick = { nav.navigate(Routes.SCAN) }
                ) {
                    Icon(Icons.Default.QrCodeScanner, null, tint = Color.White, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("Scan Ruangan", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }

                ActionCard(
                    modifier = Modifier.weight(1f),
                    background = card,
                    border = BorderStroke(1.dp, borderSoft),
                    radius = cardRadius,
                    onClick = { nav.navigate(Routes.HISTORY) }
                ) {
                    Icon(Icons.Default.Warning, null, tint = orange, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("Laporan Masuk", color = darkText, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(Modifier.height(2.dp))
                    Text("$laporanMasukCount Perlu Tindakan", color = orange, fontSize = 12.sp)
                }
            }
        }

        /* ================= LAPORAN TERBARU ================= */
        item {
            SectionHeader(
                title = "Laporan Terbaru",
                actionText = "Lihat Semua",
                actionColor = primaryGreen
            ) { nav.navigate(Routes.HISTORY) }
        }

        if (!ui.loading && ui.recentReports.isEmpty()) {
            item { Text("Belum ada laporan terbaru.", color = grayText, fontSize = 13.sp) }
        } else {
            items(ui.recentReports.size) { idx ->
                val r = ui.recentReports[idx]
                val statusColor = when (r.status) {
                    "DONE" -> primaryGreen
                    "IN_PROGRESS" -> orange
                    else -> orange
                }

                RecentReportItem(
                    title = r.roomName,
                    subtitle = when (r.status) {
                        "DONE" -> "Selesai • ${r.timeLabel}"
                        "IN_PROGRESS" -> "Dikerjakan • ${r.timeLabel}"
                        else -> "Menunggu • ${r.timeLabel}"
                    },
                    statusColor = statusColor,
                    radius = 16.dp,
                    borderColor = borderSoft
                ) {
                    nav.navigate("edit_report/${r.id}")
                }
            }
        }
    }
}

/* ================= KOMPONEN ================= */

@Composable
private fun SectionHeader(
    title: String,
    actionText: String,
    actionColor: Color,
    onAction: () -> Unit
) {
    val darkText = Color(0xFF1E2D28)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            color = darkText,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = (-0.1).sp
        )
        Spacer(Modifier.weight(1f))
        Text(
            actionText,
            color = actionColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onAction() }
        )
    }
}

@Composable
private fun ProgressCard(
    doneText: String,
    percentText: String,
    progress: Float,
    primary: Color,
    darkText: Color,
    grayText: Color,
    card: Color,
    border: Color,
    radius: Dp
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(radius),
        color = card,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
        border = BorderStroke(1.dp, border)
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(
                "Progres Hari Ini",
                color = darkText,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                letterSpacing = (-0.1).sp
            )
            Spacer(Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(doneText, color = darkText, fontSize = 13.sp)
                Spacer(Modifier.weight(1f))
                Text(percentText, color = primary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }

            Spacer(Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(10.dp)),
                color = primary,
                trackColor = Color(0xFFE6F3EC)
            )

            Spacer(Modifier.height(8.dp))
            Text("Selesaikan tugas agar progres naik", color = grayText, fontSize = 12.sp)
        }
    }
}

@Composable
private fun ActionCard(
    modifier: Modifier,
    background: Color,
    border: BorderStroke?,
    radius: Dp,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(radius)

    Surface(
        modifier = modifier
            .height(108.dp)
            .clickable { onClick() },
        shape = shape,
        color = background,
        border = border,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = content
        )
    }
}

@Composable
private fun TaskCard(
    title: String,
    subtitle: String,
    indicatorColor: Color,
    radius: Dp,
    borderColor: Color
) {
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(radius),
        color = Color.White,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(44.dp)
                    .background(indicatorColor, RoundedCornerShape(6.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = darkText, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(Modifier.height(2.dp))
                Text(subtitle, color = grayText, fontSize = 12.sp)
            }

            Box(
                modifier = Modifier
                    .size(22.dp)
                    .border(2.dp, Color(0xFFE3E7E5), CircleShape)
            )
        }
    }
}

@Composable
private fun RecentReportItem(
    title: String,
    subtitle: String,
    statusColor: Color,
    radius: Dp,
    borderColor: Color,
    onClick: () -> Unit
) {
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(radius),
        color = Color.White,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(statusColor, CircleShape)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = darkText, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(Modifier.height(2.dp))
                Text(subtitle, color = grayText, fontSize = 12.sp)
            }
            Text("›", color = grayText, fontSize = 22.sp)
        }
    }
}
