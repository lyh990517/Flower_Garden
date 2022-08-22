package com.project.Entity

data class OwnerEntity (
    val id : String,
    val pwd : String,
    val nickName : String,
    val StoreName: String,
    val flowers : List<FlowerEntity>
        ){
    constructor() : this("","","","", emptyList())
}