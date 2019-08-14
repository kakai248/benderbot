import activities.Activity
import commands.PingCommand
import commands.TriviaCommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

typealias DiscordActivity = net.dv8tion.jda.api.entities.Activity

fun main(args: Array<String>) {
    JDABuilder(args[0])
        .addEventListeners(BenderBot())
        .setActivity(DiscordActivity.playing("In dev baby!"))
        .build()
}

class BenderBot : ListenerAdapter() {

    private val commandParser = CommandProcessor(
        prefix = "*",
        registeredCommands = *arrayOf(
            PingCommand(),
            TriviaCommand()
        )
    )

    private val activities = mutableMapOf<ActivityScope, Activity>()

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val action = commandParser.process(event)
        if (action != null) {
            handleAction(action)
        } else {
            val scope = ActivityScope(
                guild = event.guild,
                channel = event.channel
            )

            // If we couldn't find an activity with this scope or the activity didn't handle the message, pass the
            // message to the message processor
            if (activities[scope]?.onMessageReceived(event) != false) {
                // TODO: message processor
            }
        }
    }

    private fun handleAction(action: Action) {
        when (action) {
            is Action.ChangeActivity -> handleChangeActivity(action)
            is Action.ExitActivity -> handleExitActivity(action)
        }
    }

    private fun handleChangeActivity(action: Action.ChangeActivity) {
        val currentActivity = activities[action.scope]

        // If we are switching to the same activity, don't do anything.
        if (currentActivity != null && currentActivity::class == action.activity::class) {
            return
        }

        exitActivity(action.scope)
        activities[action.scope] = action.activity

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                action.activity.actions
                    .consumeAsFlow()
                    .collect { action ->
                        handleAction(action)
                    }
            }
        }

        action.activity.onStarting()
    }

    private fun handleExitActivity(action: Action.ExitActivity) {
        exitActivity(action.scope)
    }

    private fun exitActivity(scope: ActivityScope) {
        activities[scope]?.onEnding()
        activities.remove(scope)
    }
}