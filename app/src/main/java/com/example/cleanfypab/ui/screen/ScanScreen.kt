@file:OptIn(androidx.camera.core.ExperimentalGetImage::class)

package com.example.cleanfypab.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.cleanfypab.data.model.CleaningReportDoc
import com.example.cleanfypab.data.remote.FirebaseProvider
import com.example.cleanfypab.data.repository.CleaningReportFirebaseRepository
import com.example.cleanfypab.data.repository.RoomFirebaseRepository
import com.example.cleanfypab.ui.navigation.Routes
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch

@Composable
fun ScanScreen(nav: NavHostController) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    // Repo langsung, supaya AppNavHost tidak perlu diubah
    val roomRepo = remember { RoomFirebaseRepository() }
    val reportRepo = remember { CleaningReportFirebaseRepository() }

    var hasScanned by remember { mutableStateOf(false) }
    val previewView = remember { PreviewView(context) }
    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }

    // Stop kamera saat keluar screen
    DisposableEffect(Unit) {
        onDispose { cameraProvider?.unbindAll() }
    }

    /* ================= PERMISSION ================= */
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (context as ComponentActivity),
                arrayOf(Manifest.permission.CAMERA),
                0
            )
        }
    }

    /* ================= CAMERA SETUP ================= */
    LaunchedEffect(Unit) {

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val scannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(com.google.mlkit.vision.barcode.common.Barcode.FORMAT_QR_CODE)
            .build()

        val scanner = BarcodeScanning.getClient(scannerOptions)

        val analysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        analysis.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->

            val mediaImage = imageProxy.image ?: run {
                imageProxy.close()
                return@setAnalyzer
            }

            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (hasScanned) return@addOnSuccessListener

                    for (barcode in barcodes) {
                        val raw = barcode.rawValue ?: continue
                        val roomId = raw.toIntOrNull()

                        if (roomId == null) {
                            Toast.makeText(
                                context,
                                "QR tidak valid. QR harus berisi angka ID ruangan.",
                                Toast.LENGTH_SHORT
                            ).show()
                            continue
                        }

                        // cegah double scan
                        hasScanned = true
                        cameraProvider?.unbindAll()

                        scope.launch {
                            try {
                                // getRoomById() di project kamu return Result<RoomDoc?>
                                val room = roomRepo.getRoomById(roomId).getOrNull()
                                val roomName = room?.name ?: "Ruang #$roomId"

                                val uid = FirebaseProvider.auth.currentUser?.uid
                                val email = FirebaseProvider.auth.currentUser?.email

                                // ✅ Buat riwayat otomatis
                                val report = CleaningReportDoc(
                                    roomId = roomId,
                                    roomName = roomName,
                                    status = "Selesai",
                                    note = "Update otomatis via QR Scan",
                                    cleanerName = email,
                                    updatedByUid = uid,
                                    createdAt = System.currentTimeMillis()
                                )

                                // ✅ Batch: add report + update room status
                                reportRepo.addReportAndUpdateRoom(report, "Selesai")

                                Toast.makeText(
                                    context,
                                    "$roomName → Selesai (masuk Riwayat)",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // ✅ Setelah scan langsung ke HISTORY (bukan detail)
                                nav.navigate(Routes.HISTORY) {
                                    launchSingleTop = true
                                    popUpTo(Routes.SCAN) { inclusive = true }
                                }

                            } catch (e: Exception) {
                                hasScanned = false
                                Toast.makeText(
                                    context,
                                    "Gagal update: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        break
                    }
                }
                .addOnCompleteListener { imageProxy.close() }
        }

        val future = ProcessCameraProvider.getInstance(context)
        future.addListener(
            {
                val provider = future.get()
                cameraProvider = provider

                provider.unbindAll()
                provider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    analysis
                )
            },
            ContextCompat.getMainExecutor(context)
        )
    }

    /* ================= UI ================= */
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(Modifier.width(10.dp))
            Text("Scan QR Code", color = Color.White, fontSize = 20.sp)
        }

        // Frame scan
        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.Center)
                .border(3.dp, Color(0xFF00E676), RoundedCornerShape(20.dp))
        )

        Text(
            "Arahkan QR Code ke dalam kotak\nAuto selesai + masuk Riwayat",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
        )
    }
}
