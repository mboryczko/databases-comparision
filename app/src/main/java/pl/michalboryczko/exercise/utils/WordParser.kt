package pl.michalboryczko.exercise.utils

import android.os.Environment
import pl.michalboryczko.exercise.model.presentation.Translate
import timber.log.Timber
import java.io.File

class WordParser{

    companion object {
        fun parseWords(fileName: String, words: Int): List<Translate>{
            val translates = mutableListOf<Translate>()
            Timber.d("wordsToLearn parse processing")
            val downlaodDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            var i = 0
            File(downlaodDirectory, fileName).forEachLine { line->
                i += 1
                if(i < words){
                    val words = line.split(" ")
                    translates.add(Translate(translates.size.toLong(), words[4]+translates.size, words[2]+translates.size,  0, 0, false))
                }
            }

            Timber.d("wordsToLearn parsed ${translates.size} processing END")
            return translates
        }


        fun parseSentences(fileName: String): List<Translate>{
            //Timber.d("sentences parse sentences")
            val translates = mutableListOf<Translate>()
            var i = 0
            val downlaodDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val lines = File(downlaodDirectory, fileName).readLines()
            while(i < lines.size){
                translates.add(Translate(translates.size.toLong(), lines[i], lines[i+1], 0, 0, false))
                i += 4
            }

            //Timber.d("sentences parse sentences END")
            return translates
        }
    }
}