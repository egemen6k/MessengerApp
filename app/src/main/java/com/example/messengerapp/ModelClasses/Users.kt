package com.example.messengerapp.ModelClasses

class Users {
    /*databasede oluşturduğumuz hashmap key isimleri ile aynı isimler olması lazım
    * dataları atayacağımız bir model class oluşturuyoruz*/
    private var uid: String = ""
    private var username: String = ""
    private var profile: String = ""
    private var cover: String = ""
    private var status: String = ""
    private var search: String = ""
    private var facebook: String = ""
    private var instagram: String = ""
    private var website: String = ""

    /*construtor çağırıyoruz*/
    constructor()

/*dataları eşleştirdik*/
    constructor(
        uid: String,
        username: String,
        profile: String,
        cover: String,
        status: String,
        search: String,
        facebook: String,
        instagram: String,
        website: String
    ) {
        this.uid = uid
        this.username = username
        this.profile = profile
        this.cover = cover
        this.status = status
        this.search = search
        this.facebook = facebook
        this.instagram = instagram
        this.website = website
    }

    /*dataların get ve set fonksiyonları*/
    fun getUID(): String?{
            return uid
    }

    fun setUID(uid: String){
            this.uid = uid
    }

    fun getUserName(): String?{
        return username
    }

    fun setUserName(username: String){
        this.username = username
    }

    fun getProfile(): String?{
        return profile
    }

    fun setProfile(profile: String){
        this.profile = profile
    }

    fun getCover(): String?{
        return cover
    }

    fun setCover(cover: String){
        this.cover = cover
    }

    fun getStatus(): String?{
        return status
    }

    fun setStatus(status: String){
        this.status = status
    }

    fun getSearch(): String?{
        return search
    }

    fun setSearch(search: String){
        this.search = search
    }

    fun getFacebook(): String?{
        return facebook
    }

    fun setFacebook(facebook: String){
        this.facebook = facebook
    }

    fun getInstagram(): String?{
        return instagram
    }

    fun setInstagram(instagram: String){
        this.instagram = instagram
    }

    fun getWebsite(): String?{
        return website
    }

    fun setWebsite(website: String){
        this.website = website
    }


}