package com.ahmedapps.data.user

import com.ahmedapps.data.media.model.Media
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    val name: String,
    val email: String,
    val password: String,
    val salt: String,
    val mediaList: ArrayList<Media>,

    @BsonId val id: ObjectId = ObjectId()
)
