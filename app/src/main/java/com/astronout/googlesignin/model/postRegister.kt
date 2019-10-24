package com.astronout.googlesignin.model

data class postRegister(
    var access_token: String,
    var social_id: String,
    var auth_type: String
)