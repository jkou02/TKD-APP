package com.jkou.tkd_app.model

data class EventResponse(
    val message: String,
    val validado: Boolean,
    val jueces_confirmantes: List<String>
)
