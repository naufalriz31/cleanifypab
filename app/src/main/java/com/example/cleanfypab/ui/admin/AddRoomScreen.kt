package com.example.cleanfypab.ui.admin

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.print.PrintHelper
import com.example.cleanfypab.viewmodel.AddRoomViewModel
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.google.zxing.BarcodeFormat

@Composable
fun AddRoomScreen(
    onBack: () -> Unit,
    vm: AddRoomViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by vm.uiState.collectAsState()

    /* ===== PALET CLEANIFY ===== */
    val bgGradient = Brush.verticalGradient(listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE)))
    val card = Color.White
    val borderSoft = Color(0xFFE0E0E0)
    val cardSoft = Color(0xFFF2F7F4)

    val primaryGreen = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    // QR bitmap preview
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var qrPreviewError by remember { mutableStateOf<String?>(null) }

    // generate preview setiap qrValue berubah
    LaunchedEffect(state.qrValue) {
        if (state.qrValue.isBlank()) {
            qrBitmap = null
            qrPreviewError = null
            return@LaunchedEffect
        }

        runCatching {
            qrBitmap = generateQrBitmap(state.qrValue.trim(), 900)
            qrPreviewError = null
        }.onFailure {
            qrBitmap = null
            qrPreviewError = it.localizedMessage ?: it.toString()
        }
    }

    // sukses simpan -> dialog
    if (state.success) {
        AlertDialog(
            onDismissRequest = { onBack() },
            confirmButton = {
                TextButton(onClick = { onBack() }) { Text("OK") }
            },
            title = { Text("Berhasil") },
            text = { Text("Ruangan berhasil disimpan.") }
        )
    }

    // error simpan dari VM
    state.error?.let { err ->
        AlertDialog(
            onDismissRequest = { /* biar aman, cukup generate QR baru atau edit field */ vm.setQrValue(state.qrValue) },
            confirmButton = {
                TextButton(onClick = { vm.setQrValue(state.qrValue) }) { Text("OK") }
            },
            title = { Text("Gagal") },
            text = { Text(err) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        /* ===== HEADER ===== */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = darkText
                )
            }
            Text(
                text = "Tambah Ruangan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        }

        Spacer(Modifier.height(14.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = card,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, borderSoft),
            tonalElevation = 1.dp,
            shadowElevation = 1.dp
        ) {
            Column(Modifier.padding(16.dp)) {

                /* ===== FORM RUANGAN ===== */
                Text("Informasi Ruangan", fontWeight = FontWeight.SemiBold, color = darkText)
                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = state.name,
                    onValueChange = { vm.setName(it) },
                    label = { Text("Nama Ruangan") },
                    placeholder = { Text("Contoh: Ruang 101", color = grayText) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    enabled = !state.loading
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.type,
                    onValueChange = { vm.setType(it) },
                    label = { Text("Tipe Ruangan") },
                    placeholder = { Text("Contoh: Ruang Kelas / Lab / Toilet", color = grayText) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    enabled = !state.loading
                )

                Spacer(Modifier.height(12.dp))

                Text("Status", color = grayText, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatusChip(
                        value = "AVAILABLE",
                        label = "Tersedia",
                        selected = state.status,
                        accent = primaryGreen,
                        enabled = !state.loading
                    ) { vm.setStatus("AVAILABLE") }

                    StatusChip(
                        value = "OCCUPIED",
                        label = "Terpakai",
                        selected = state.status,
                        accent = primaryGreen,
                        enabled = !state.loading
                    ) { vm.setStatus("OCCUPIED") }

                    StatusChip(
                        value = "MAINT",
                        label = "Perawatan",
                        selected = state.status,
                        accent = primaryGreen,
                        enabled = !state.loading
                    ) { vm.setStatus("MAINT") }
                }

                Spacer(Modifier.height(18.dp))
                Divider(color = borderSoft)
                Spacer(Modifier.height(18.dp))

                /* ===== QR SECTION ===== */
                Text("QR Ruangan", fontWeight = FontWeight.SemiBold, color = darkText)
                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = state.qrValue,
                    onValueChange = { vm.setQrValue(it) },
                    label = { Text("QR Value") },
                    placeholder = { Text("Contoh: ROOM-101-A", color = grayText) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    enabled = !state.loading
                )

                Spacer(Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = { vm.generateQr() },
                        shape = RoundedCornerShape(14.dp),
                        enabled = !state.loading
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Generate Baru")
                    }

                    Button(
                        onClick = {
                            val bmp = qrBitmap ?: return@Button
                            PrintHelper(context).apply {
                                scaleMode = PrintHelper.SCALE_MODE_FIT
                                printBitmap(
                                    "QR-${state.name.ifBlank { "Room" }}",
                                    bmp
                                )
                            }
                        },
                        enabled = qrBitmap != null && !state.loading,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
                    ) {
                        Icon(Icons.Default.Print, contentDescription = null, tint = Color.White)
                        Spacer(Modifier.width(8.dp))
                        Text("Cetak QR", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(Modifier.height(14.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 260.dp),
                    color = cardSoft,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, borderSoft)
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        when {
                            qrPreviewError != null ->
                                Text(qrPreviewError!!, color = Color.Red, fontSize = 13.sp)

                            state.qrValue.isBlank() ->
                                Text("QR Preview akan muncul di sini", color = grayText, fontSize = 13.sp)

                            qrBitmap == null ->
                                CircularProgressIndicator()

                            else -> Image(
                                bitmap = qrBitmap!!.asImageBitmap(),
                                contentDescription = "QR Preview",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, borderSoft, RoundedCornerShape(12.dp))
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { vm.saveRoom() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryGreen),
                    enabled = state.canSave
                ) {
                    if (state.loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(10.dp))
                        Text("Menyimpan...", color = Color.White, fontWeight = FontWeight.Bold)
                    } else {
                        Text("Simpan Ruangan", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun StatusChip(
    value: String,
    label: String,
    selected: String,
    accent: Color,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val isSelected = selected == value

    Surface(
        color = if (isSelected) accent.copy(alpha = 0.12f) else Color.Transparent,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, if (isSelected) accent else Color(0xFFE0E0E0)),
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .then(if (enabled) Modifier.clickable { onClick() } else Modifier)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            color = if (isSelected) accent else Color(0xFF1E2D28),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}

private fun generateQrBitmap(content: String, sizePx: Int): Bitmap {
    val encoder = BarcodeEncoder()
    return encoder.encodeBitmap(content, BarcodeFormat.QR_CODE, sizePx, sizePx)
}
