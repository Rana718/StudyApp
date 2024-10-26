package com.example.studyapp.domain.model

import androidx.room.PrimaryKey

data class Session(
    val relatedToSubject: String,
    val date: Long,
    val duration: Long,
    val sessionSubjectId: Int,
//    @PrimaryKey(autoGenerate = true)
    val sessionId: Int? = null
)
