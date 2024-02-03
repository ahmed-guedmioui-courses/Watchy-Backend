package com.ahmedapps.data.user

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class MongoUserDataSource(
    db: CoroutineDatabase
) : UserDataSource {

    private val users = db.getCollection<User>()

    override suspend fun getUserByEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }

    override suspend fun updateUser(updatedUser: User): Boolean {
        val updateResult = users.replaceOne(
            User::email eq updatedUser.email,
            updatedUser
        )

        return updateResult.wasAcknowledged()
    }

}











