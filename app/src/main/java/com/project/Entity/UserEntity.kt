package com.project.Entity

data class UserEntity (
    val id : String,
    val pwd : String,
    val nickName : String
        ){
    constructor() : this("","","")
}