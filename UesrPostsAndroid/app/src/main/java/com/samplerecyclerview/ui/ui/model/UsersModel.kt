package com.samplerecyclerview.ui.ui.model

data class UsersModel(val name:String, val username:String, val address:AddressModel){
}

data class AddressModel(val street:String, val suite:String, val city:String)


