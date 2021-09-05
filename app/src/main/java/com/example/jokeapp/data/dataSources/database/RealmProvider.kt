package com.example.jokeapp.data.dataSources.database

import io.realm.Realm

interface RealmProvider {
    fun getCurrentDataBase(): Realm
}

class BaseRealmProvider: RealmProvider{
    override fun getCurrentDataBase(): Realm {
        return Realm.getDefaultInstance()
    }

}