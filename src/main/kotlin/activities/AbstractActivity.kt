package activities

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

abstract class AbstractActivity : Activity {
    override fun onStarting() {}

    override fun onEnding() {}

    override fun onMessageReceived(event: MessageReceivedEvent): Boolean {
        return false
    }
}