package com.mfm.gansys.jdbc.model

data class UserTelegram(val idUser:Long, val name:String, val email:String, val isActivate:Int, val dateJoined:String, val codeActivation:String, val codeActivationLife:String)
