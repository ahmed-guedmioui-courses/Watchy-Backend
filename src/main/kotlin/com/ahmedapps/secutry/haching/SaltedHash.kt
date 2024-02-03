package com.ahmedapps.secutry.haching

data class SaltedHash (
    val hash: String,
    val salt: String,
)