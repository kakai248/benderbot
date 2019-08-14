package activities

import Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

abstract class AbstractActivity : Activity {
    protected val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override val actions: Channel<Action> = Channel()

    override fun onStarting() {}

    override fun onEnding() {
        actions.close()
        scope.cancel()
    }

    override fun onMessageReceived(event: MessageReceivedEvent): Boolean {
        return false
    }
}