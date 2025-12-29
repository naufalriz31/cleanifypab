package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminUserScreen(
    onBack: () -> Unit = {}
) {

    val bgColor = Color(0xFF0F2A1D)
    val cardColor = Color(0xFF163828)
    val green = Color(0xFF2DFF8F)
    val gray = Color(0xFF9E9E9E)

    var selectedTab by remember { mutableStateOf("ALL") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            /* ================= HEADER ================= */
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.clickable { onBack() }
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "Manajemen Pengguna",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    FloatingActionButton(
                        onClick = {},
                        containerColor = green,
                        shape = CircleShape,
                        modifier = Modifier.size(42.dp)
                    ) {
                        Icon(Icons.Default.Add, null, tint = Color.Black)
                    }
                }
            }

            /* ================= SEARCH ================= */
            item {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Cari pengguna...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = cardColor,
                        unfocusedContainerColor = cardColor,
                        focusedBorderColor = green,
                        unfocusedBorderColor = cardColor,
                        cursorColor = green
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }

            /* ================= FILTER ================= */
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    UserFilter("ALL", "Semua", selectedTab, green) { selectedTab = "ALL" }
                    UserFilter("ADMIN", "Admin", selectedTab, green) { selectedTab = "ADMIN" }
                    UserFilter("STAFF", "Staf", selectedTab, green) { selectedTab = "STAFF" }
                    UserFilter("MAINTENANCE", "Maintenance", selectedTab, green) {
                        selectedTab = "MAINTENANCE"
                    }
                }
            }

            /* ================= TITLE ================= */
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "SEMUA PENGGUNA (24)",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "URUTKAN",
                        color = green,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            /* ================= USER LIST ================= */
            item {
                UserCard(
                    name = "Jane Doe",
                    email = "jane@cleaning.com",
                    role = "Maintenance",
                    roleColor = Color(0xFF4DA6FF),
                    active = true,
                    cardColor = cardColor
                )
            }

            item {
                UserCard(
                    name = "John Smith",
                    email = "john@admin.com",
                    role = "Super Admin",
                    roleColor = Color(0xFFB983FF),
                    active = true,
                    cardColor = cardColor
                )
            }

            item {
                UserCard(
                    name = "Robert Fox",
                    email = "robert@cleaning.com",
                    role = "Staf",
                    roleColor = Color(0xFFFFC107),
                    active = false,
                    cardColor = cardColor
                )
            }

            item {
                UserCard(
                    name = "Sarah Connor",
                    email = "sarah@security.com",
                    role = "Keamanan",
                    roleColor = Color(0xFFFF6B6B),
                    active = true,
                    cardColor = cardColor,
                    initials = "SC"
                )
            }

            item {
                UserCard(
                    name = "Lindsay Walton",
                    email = "lindsay@admin.com",
                    role = "Admin",
                    roleColor = Color(0xFF9EFFA0),
                    active = true,
                    cardColor = cardColor,
                    swipeDelete = true
                )
            }

            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

/* ================= COMPONENT ================= */

@Composable
fun UserFilter(
    key: String,
    text: String,
    selected: String,
    accent: Color,
    onClick: () -> Unit
) {
    Surface(
        color = if (selected == key) accent else Color.Transparent,
        shape = RoundedCornerShape(50),
        border = if (selected != key) BorderStroke(1.dp, accent) else null,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected == key) Color.Black else accent,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun UserCard(
    name: String,
    email: String,
    role: String,
    roleColor: Color,
    active: Boolean,
    cardColor: Color,
    initials: String? = null,
    swipeDelete: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(20.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(roleColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (initials != null) {
                    Text(initials, color = Color.White, fontWeight = FontWeight.Bold)
                } else {
                    Icon(Icons.Default.Person, null, tint = Color.White)
                }
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Text(name, color = Color.White, fontWeight = FontWeight.Bold)
                Text(email, color = Color.Gray, fontSize = 12.sp)

                Spacer(Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = roleColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            role,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            color = roleColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (!active) {
                        Spacer(Modifier.width(8.dp))
                        Text("Tidak Aktif", color = Color.Gray, fontSize = 11.sp)
                    }
                }
            }
        }

        Icon(Icons.Default.MoreVert, null, tint = Color.Gray)
    }
}
