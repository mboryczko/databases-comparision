package pl.michalboryczko.exercise.utils

import android.os.Environment
import pl.michalboryczko.exercise.model.presentation.Translate
import timber.log.Timber
import java.io.File

class WordParser{

    companion object {
        fun parseWords(fileName: String): List<Translate>{
            val translates = mutableListOf<Translate>()
            Timber.d("words parse processing")
            val downlaodDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            File(downlaodDirectory, fileName).forEachLine { line->
                val words = line.split(" ")
                translates.add(Translate(words[2], words[4]))
            }

            Timber.d("words parse processing END")
            return translates
        }


        fun parseSentences(fileName: String): List<Translate>{
            Timber.d("sentences parse sentences")
            val translates = mutableListOf<Translate>()
            var i = 0
            val downlaodDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val lines = File(downlaodDirectory, fileName).readLines()
            while(i < lines.size){
                translates.add(Translate(lines[i], lines[i+1]))
                i += 4
            }

            Timber.d("sentences parse sentences END")
            return translates
        }
    }
}