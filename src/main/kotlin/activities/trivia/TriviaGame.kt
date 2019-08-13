package activities.trivia

import activities.AbstractActivity
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class TriviaGame(private val channel: TextChannel) : AbstractActivity() {

    override fun onStarting() {
        channel.sendMessage("Welcome to the trivia game!").queue()
    }

    override fun onEnding() {
        channel.sendMessage("Leaving trivia game!").queue()
    }

    override fun onMessageReceived(event: MessageReceivedEvent): Boolean {
        println("[Trivia]: ${event.message.contentRaw}")
        return false
    }
}