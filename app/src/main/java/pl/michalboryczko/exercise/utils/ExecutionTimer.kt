package pl.michalboryczko.exercise.utils

import timber.log.Timber

class ExecutionTimer{

    private var startTime = System.currentTimeMillis()

    fun startTimer(){
        startTime = System.currentTimeMillis()
    }

    fun stopTimer(log: String){
        val currentTime = System.currentTimeMillis()
        val timeInMillisDifference = currentTime - startTime
        Timber.d("$log execution time: $timeInMillisDifference")
    }
}