package commands

import Action
import activities.trivia.TriviaGame
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class TriviaCommand : Command {
    override fun matches(instruction: String): Boolean {
        return instruction == "trivia"
    }

    override fun handle(params: List<String>, event: MessageReceivedEvent): Action? {
        return when {
            params.getOrNull(0) == "play" -> Action.ChangeActivity(TriviaGame(event.textChannel))
            params.getOrNull(0) == "quit" -> Action.ExitActivity
            else -> null
        }
    }
}