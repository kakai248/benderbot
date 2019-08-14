package activities

import Action
import kotlinx.coroutines.channels.Channel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

interface Activity {
    val actions: Channel<Action>

    fun onStarting()
    fun onEnding()
    fun onMessageReceived(event: MessageReceivedEvent): Boolean
}