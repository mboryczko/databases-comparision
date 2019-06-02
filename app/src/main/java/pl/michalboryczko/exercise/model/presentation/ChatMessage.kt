package pl.michalboryczko.exercise.model.presentation

data class ChatMessage(
        val user: String,
        val message: String,
        val time: String,
        val uid: String,
        val sessionId: String,
        val isOwnMessage: Boolean
){
    //needed for firestore mapping
    constructor(): this("", "", "", "",  "", false)
}


data class Message(
        val user: String,
        val message: String,
        val time: String,
        val uid: String,
        val sessionId: String
)