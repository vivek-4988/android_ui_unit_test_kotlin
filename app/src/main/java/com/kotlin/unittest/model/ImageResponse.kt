package com.kotlin.unittest.model

data class ImageResponse(
    val hits: List<ImageResult>,
    val total:Int,
    val totalHits:Int
)
