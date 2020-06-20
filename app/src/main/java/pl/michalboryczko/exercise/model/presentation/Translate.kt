package pl.michalboryczko.exercise.model.presentation

import pl.michalboryczko.exercise.model.database.greendao.TranslateGreen


data class Translate(
        val id: Long,
        val english: String,
        var spanish: String,
        var timesAnsweredRight: Int,
        val timesAnsweredWrong: Int,
        var shouldBeLearned: Boolean
)

abstract class Word{
    abstract val wordId: Long
    abstract val wordEnglish: String
    abstract var wordSpanish: String
    abstract var wordTimesAnsweredRight: Int
    abstract val wordTimesAnsweredWrong: Int
    abstract var wordShouldBeLearned: Boolean
}



fun TranslateGreen.convertToTranslate(): Translate{
    return Translate(this.id, this.english, this.spanish, this.timesAnsweredRight, this.timesAnsweredWrong, this.shouldBeLearned)
}


fun Translate.convertToTranslateGreen(): TranslateGreen{
    return TranslateGreen(this.id, this.english, this.spanish, this.timesAnsweredRight, this.timesAnsweredWrong, this.shouldBeLearned)
}