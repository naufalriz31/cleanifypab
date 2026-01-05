package com.example.cleanfypab.ui.screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cleanfypab.viewmodel.ScanViewModel
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

@Composable
fun ScanScreen(
    nav: NavHostController,
    vm: ScanViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    val bgGradient = Brush.verticalGradient(listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE)))
    val primaryGreen = Color(0xFF2ECC71)

    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasPermission = granted }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    // ✅ Navigate kalau room ketemu
    LaunchedEffect(state.navigateRoomId) {
        val roomId = state.navigateRoomId ?: return@LaunchedEffect
        nav.navigate("room_detail/$roomId") // pastikan route ini ada di AppNavHost
        vm.consumeNavigation()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
    ) {
        if (!hasPermission) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Butuh izin kamera untuk scan QR.")
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
                ) { Text("Izinkan Kamera") }
            }
            return@Box
        }

        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            onQrValue = { qr ->
                vm.onQrScanned(qr)
            }
        )

        if (state.loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error?.let { err ->
            AlertDialog(
                onDismissRequest = { vm.resetError() },
                confirmButton = {
                    TextButton(onClick = { vm.resetError() }) { Text("OK") }
                },
                title = { Text("Gagal Scan") },
                text = { Text(err) }
            )
        }
    }
}

@Composable
private fun CameraPreview(
    modifier: Modifier = Modifier,
    onQrValue: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }

                val analysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                analysis.setAnalyzer(
                    ContextCompat.getMainExecutor(ctx),
                    QrAnalyzer(onQrValue)
                )

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        analysis
                    )
                } catch (_: Exception) {}

            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}

private class QrAnalyzer(
    private val onQrValue: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    // anti spam: agar tidak memanggil onQrValue berkali-kali super cepat
    private var lastValue: String? = null
    private var lastTime: Long = 0L

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage == null) {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                val value = barcodes.firstOrNull()?.rawValue?.trim()
                val now = System.currentTimeMillis()

                if (!value.isNullOrBlank()) {
                    val tooFast = (now - lastTime) < 800
                    val same = (value == lastValue)

                    if (!(tooFast && same)) {
                        lastValue = value
                        lastTime = now
                        onQrValue(value)
                    }
                }
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}
