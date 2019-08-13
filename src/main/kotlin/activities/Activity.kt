package activities

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

interface Activity {
    fun onStarting()
    fun onEnding()
    fun onMessageReceived(event: MessageReceivedEvent): Boolean
}