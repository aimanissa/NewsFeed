package com.aimanissa.android.newsfeed.data.app.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "news_headlines")
data class NewsItem(
    @PrimaryKey
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String? = "",
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String? = ""
) : Parcelable