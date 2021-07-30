package com.taetae98.wildriftdictionary.dto

import java.io.Serializable

data class News(
    val image: String,
    val title: String,
    val url: String
) : Serializable