import commands.Command
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class CommandProcessor(
    var prefix: String = "",
    vararg registeredCommands: Command
) {

    private val commands = registeredCommands.toList()

    fun process(event: MessageReceivedEvent): Action? {
        val rawMessage = event.message.contentRaw.run {
            if (prefix.isNotEmpty() && !startsWith(prefix)) {
                return null
            }
            replaceFirst(prefix, "")
        }

        val parsedMessage = parseMessage(rawMessage)
        if (parsedMessage.isEmpty()) {
            return null
        }

        for (command in commands) {
            if (command.matches(parsedMessage[0])) {
                return command.handle(parsedMessage.drop(1), event)
            }
        }
        return null
    }

    private fun parseMessage(message: String): List<String> {
        return message.toLowerCase().split(" ")
    }
}