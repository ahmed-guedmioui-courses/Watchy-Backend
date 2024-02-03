package com.ahmedapps.secutry.haching

interface HashingService {

    fun generateSaltedHash(value: String, saltLength: Int = 23): SaltedHash
    fun verify(value: String, saltedHash: SaltedHash): Boolean

}