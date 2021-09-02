package com.example.jokeapp.data.dataSources.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DataBaseJokeModel: RealmObject() {
    @PrimaryKey
    var id: Int = -1
    var type: String = ""
    var setup: String = ""
    var punchline: String = ""
}