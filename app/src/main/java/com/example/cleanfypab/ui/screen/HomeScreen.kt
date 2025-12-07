package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// =========================
// COLOR PALETTE
// =========================
val HomeDarkGreen = Color(0xFF071A12)
val HomeNeonBlue = Color(0xFF2979FF)
val HomeCardDark = Color(0xFF0F2A1D)
val HomeTextGray = Color(0xFF9BA5A0)

@Composable
fun HomeScreen(
    nav: NavHostController,
    modifier: Modifier = Modifier   // ← WAJIB agar Scaffold padding bekerja
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HomeDarkGreen)
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        // =========================
        // HEADER
        // =========================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Good Morning, Olivia",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .clickable { nav.navigate("profile") }
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        // =========================
        // QR SCAN CARD
        // =========================
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = HomeCardDark),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color(0xFF1B2F23), RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        contentDescription = "Scan QR",
                        tint = HomeNeonBlue,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    "Scan Room QR Code",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    "Start a new room check by scanning the QR code.",
                    color = HomeTextGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 6.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { nav.navigate("scan") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HomeNeonBlue)
                ) {
                    Text("Scan Now", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================
        // QUICK STATS
        // =========================
        Text(
            "Quick Stats",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            StatCard(title = "Rooms Cleaned", value = "12")
            StatCard(title = "Open Reports", value = "3")
        }

        Spacer(modifier = Modifier.height(28.dp))

        // =========================
        // RECENT REPORTS
        // =========================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Recent Reports",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                "View All",
                color = HomeNeonBlue,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    nav.navigate("history")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        RecentReportItem(
            room = "Room 301",
            status = "Completed",
            time = "10:45 AM",
            statusColor = Color(0xFF4CAF50)
        )

        RecentReportItem(
            room = "Room 215",
            status = "In Progress",
            time = "09:30 AM",
            statusColor = Color(0xFFFFA726)
        )

        RecentReportItem(
            room = "Lobby Restroom",
            status = "Completed",
            time = "08:12 AM",
            statusColor = Color(0xFF4CAF50)
        )
    }
}



// =================================================
// STAT CARD COMPONENT
// =================================================

@Composable
fun StatCard(title: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = HomeCardDark),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(165.dp)
            .height(90.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(title, color = HomeTextGray, fontSize = 14.sp)
            Text(value, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }
    }
}



// =================================================
// RECENT REPORT ITEM
// =================================================

@Composable
fun RecentReportItem(room: String, status: String, time: String, statusColor: Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = HomeCardDark),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0xFF1B2F23), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.QrCodeScanner,
                    contentDescription = "QR",
                    tint = HomeNeonBlue,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(room, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text("$status • $time", color = HomeTextGray, fontSize = 14.sp)
            }

            Surface(
                color = statusColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    status,
                    color = statusColor,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}
