package commands

import Action
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

interface Command {
    fun matches(instruction: String): Boolean
    fun handle(params: List<String>, event: MessageReceivedEvent): Action?
}