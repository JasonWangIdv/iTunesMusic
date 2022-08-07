package com.cinnox.itunes.repository.bean

data class SearchBean(
    val resultCount: Int,
    val results: List<MusicBean>
)