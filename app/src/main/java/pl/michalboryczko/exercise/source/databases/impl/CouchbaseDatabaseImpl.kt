package pl.michalboryczko.exercise.source.databases.impl

import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.presentation.Translate
import com.couchbase.lite.*
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import timber.log.Timber


class CouchbaseDatabaseImpl(val database: Database): DatabaseOperations(){

    override fun fetchAllWords(): Single<List<Translate>>{
        timer.startTimer()
        val query = QueryBuilder.select(SelectResult.all())
                .from(DataSource.database(database))
                .where(Expression.property("type").equalTo(Expression.string("word")))

        val result = query.execute()
        timer.stopTimer("couchdatabase fetchAllWords ${result.allResults().size}")

        query.addChangeListener {change->
            for (result in change.results) {
                result.keys.forEach {
                    Timber.d("couchdatabase keys: ${result.keys.size}")
                }
                Timber.d("couchdatabase r english: ${result.getString("english")} spanish: ${result.getString("spanish")} ")
            }
        }

        val output = mutableListOf<Translate>()
        result.allResults().forEach {
            Timber.d("couchdatabase r: ${it.toString()} eng: ${it.getString("english")} ${it.keys}")
            val english = it.getString("english")
            val spanish = it.getString("spanish")
            if(english != null && spanish != null){
                output.add(Translate(english, spanish))
            }
        }

        Timber.d("couchbase size fetched: ${output.size}")
        return Single.just(output)
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        timer.startTimer()
        val wordDocument = MutableDocument()
        database.save(wordDocument)


        words.forEach {
            val document = MutableDocument()
            document.setString("type", "word")
            document.setString("english", it.english)
            document.setString("spanish", it.spanish)
            database.save(document)
        }


        timer.stopTimer("couchdatabase saveAllWords")
        return Completable.complete()
    }
}