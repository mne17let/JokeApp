package com.example.jokeapp.data.dataSources.database

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.UIJoke
import com.example.jokeapp.data.api.JokeModelJSON
import com.example.jokeapp.data.dataSources.CacheDataSource
import com.example.jokeapp.data.dataSources.JokeCachedCallback
import io.realm.Realm
import io.realm.RealmResults

class CachedDataBase(private val realm: Realm): CacheDataSource {
    override fun addOrRemove(id: Int, joke: Joke): UIJoke {
        val alreadyExistsJoke: DataBaseJokeModel? = realm.where(DataBaseJokeModel::class.java).equalTo("id", id).findFirst()

        if(alreadyExistsJoke == null){
            val newJokeForDataBaseCache: DataBaseJokeModel = joke.toRealmJoke()

            val transaction = object : Realm.Transaction{
                override fun execute(realm: Realm) {
                    realm.insert(newJokeForDataBaseCache)
                }

            }

            realm.executeTransactionAsync(transaction)
            //realm.close()

            return joke.toFavouriteJoke()
        } else{
            val transaction = object : Realm.Transaction{
                override fun execute(realm: Realm) {
                    val alreadyExistsJokeForNewThread =
                        realm.where(DataBaseJokeModel::class.java).equalTo("id", id).findFirst()
                    alreadyExistsJokeForNewThread?.deleteFromRealm()
                }
            }

            realm.executeTransactionAsync(transaction)
            //realm.close()

            return joke.toStandardJoke()
        }
    }

    override fun getJokeFromCache(callback: JokeCachedCallback) {
        val jokesFromDataBase: RealmResults<DataBaseJokeModel> = realm
            .where(DataBaseJokeModel::class.java).findAll()

        if(jokesFromDataBase.isEmpty()){
            callback.cacheError()
        } else{
            val randomJokeFromDataBase = jokesFromDataBase.random()
            val randomJoke_id = randomJokeFromDataBase.id
            val randomJoke_type = randomJokeFromDataBase.type
            val randomJoke_setup = randomJokeFromDataBase.setup
            val randomJoke_punchline = randomJokeFromDataBase.punchline

            val joke = Joke(randomJoke_id, randomJoke_type, randomJoke_setup, randomJoke_punchline)

            callback.cachedSuccessfully(joke)
        }

        //realm.close()
    }
}