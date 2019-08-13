package commands

import Action
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class PingCommand : Command {
    override fun matches(instruction: String): Boolean {
        return instruction == "ping"
    }

    override fun handle(params: List<String>, event: MessageReceivedEvent): Action? {
        event.channel.sendMessage("pong").queue()
        return null
    }
}