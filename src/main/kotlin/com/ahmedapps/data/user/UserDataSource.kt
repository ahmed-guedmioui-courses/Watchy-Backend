package com.ahmedapps.data.user


interface UserDataSource {
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: User): Boolean
    suspend fun updateUser(updatedUser: User): Boolean
}