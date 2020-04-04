package pl.michalboryczko.exercise.model.presentation


data class Translate(
        val id: Long,
        val english: String,
        val spanish: String,
        var timesAnsweredRight: Int,
        val timesAnsweredWrong: Int,
        var shouldBeLearned: Boolean
)