package com.example.chatapp.Models

class UsersViewModel() {
    var display_name: String? = null
    var status: String? = null
    var thumb_image: String? = null
    var image: String? = null

    constructor(display_name:String, status:String, thumb_image:String, image:String)  :this () {
        this.display_name = display_name
        this.status = status
        this.thumb_image = thumb_image
        this.image = image

    }




}