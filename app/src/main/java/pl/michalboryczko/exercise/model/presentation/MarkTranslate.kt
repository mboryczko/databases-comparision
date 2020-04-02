package pl.michalboryczko.exercise.model.presentation


data class MarkTranslate(
        var isMarked: Boolean,
        val translate: Translate
)


fun Translate.convertToMarkTranslate(): MarkTranslate{
    return MarkTranslate(false, this)
}


fun MarkTranslate.convertToTranslate(): Translate{
    return Translate(this.translate.id, this.translate.english, this.translate.spanish,
            this.translate.timesAnsweredRight, this.translate.timesAnsweredWrong, this.translate.shouldBeLearned)
}