package activities

import activities.AbstractActivity
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class NoActivity : AbstractActivity() {
    override fun onMessageReceived(event: MessageReceivedEvent): Boolean {
        return false
    }
}