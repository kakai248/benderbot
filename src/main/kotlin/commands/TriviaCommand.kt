package commands

import Action
import ActivityScope
import activities.trivia.TriviaGame
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class TriviaCommand : Command {
    override fun matches(instruction: String): Boolean {
        return instruction == "trivia"
    }

    override fun handle(params: List<String>, event: MessageReceivedEvent): Action? {
        val scope = ActivityScope(
            guild = event.guild,
            channel = event.channel
        )

        return when {
            params.getOrNull(0) == "play" -> Action.ChangeActivity(scope, TriviaGame(event.textChannel))
            params.getOrNull(0) in listOf("quit", "stop", "exit") -> Action.ExitActivity(scope)
            else -> null
        }
    }
}