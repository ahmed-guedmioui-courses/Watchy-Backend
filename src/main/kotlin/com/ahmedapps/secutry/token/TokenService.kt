package com.ahmedapps.secutry.token

interface TokenService {

    fun generate(
        config: TokenConfig,
        vararg claim: TokenClaim
    ): String

}