package com.example.android_basic_study_08.repository

import com.example.android_basic_study_08.entity.DetailImage
import com.example.android_basic_study_08.entity.NewImage
import com.example.android_basic_study_08.entity.RandomImage

interface PhotoRepository {
    suspend fun getPhotoList(
        page : Int
    ): Result<List<NewImage>>

    suspend fun getPhotoDetail(
        id : String
    ): Result<DetailImage>

    suspend fun getPhotoRandom(
        count: Int
    ): Result<List<RandomImage>>
}