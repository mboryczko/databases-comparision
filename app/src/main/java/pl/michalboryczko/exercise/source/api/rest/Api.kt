package pl.michalboryczko.exercise.source.api.rest

import io.reactivex.Single
import pl.michalboryczko.exercise.model.presentation.Translate
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("pl/wordsToLearn.php")
    fun getWords(): Single<List<Translate>>

}