package com.example.cleanfypab.viewmodel

data class ScanUiState(
    val loading: Boolean = false,
    val lastQr: String? = null,

    // kalau ketemu -> navigate ke route "room_detail/{roomId}"
    val navigateRoomId: String? = null,

    val error: String? = null
)
