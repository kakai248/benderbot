import activities.Activity
import activities.NoActivity
import commands.PingCommand
import commands.TriviaCommand
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

typealias DiscordActivity = net.dv8tion.jda.api.entities.Activity

fun main() {
    JDABuilder("")
        .addEventListeners(BenderBot())
        .setActivity(DiscordActivity.playing("In dev baby!"))
        .build()
}

class BenderBot : ListenerAdapter() {

    private val commandParser = CommandParser(
        prefix = "*",
        registeredCommands = *arrayOf(
            PingCommand(),
            TriviaCommand()
        )
    )

    private var activity: Activity = NoActivity()

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val action = commandParser.parse(event)
        if (action != null) {
            handleAction(action)
        } else {
            activity.onMessageReceived(event)
        }
    }

    private fun handleAction(action: Action) {
        when (action) {
            is Action.ChangeActivity -> handleChangeActivity(action)
            is Action.ExitActivity -> handleExitActivity()
        }
    }

    private fun handleChangeActivity(action: Action.ChangeActivity) {
        changeActivity(action.activity, forced = false)
    }

    private fun handleExitActivity() {
        changeActivity(NoActivity(), forced = true)
    }

    private fun changeActivity(newActivity: Activity, forced: Boolean = false) {
        if (activity::class != newActivity::class || forced) {
            activity.onEnding()
            activity = newActivity
            activity.onStarting()
        }
    }
}