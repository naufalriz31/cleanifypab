package com.example.cleanfypab.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
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
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@OptIn(ExperimentalGetImage::class)
@Composable
fun ScanScreen(nav: NavHostController) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasScanned by remember { mutableStateOf(false) }
    val previewView = remember { PreviewView(context) }

    // ==== REQUEST CAMERA PERMISSION ====
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

    // ==== SETUP CAMERA ====
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
                    if (!hasScanned) {
                        for (barcode in barcodes) {
                            barcode.rawValue?.let { result ->
                                val id = result.toIntOrNull()
                                if (id != null) {
                                    hasScanned = true
                                    nav.navigate("detail/$id") {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        }
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            {
                val cameraProvider = cameraProviderFuture.get()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    analysis
                )
            },
            ContextCompat.getMainExecutor(context)
        )
    }

    // ==== UI ====
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

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

        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.Center)
                .border(3.dp, Color(0xFF00E676), RoundedCornerShape(20.dp))
        )
    }
}
