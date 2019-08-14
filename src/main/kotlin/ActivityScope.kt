import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.MessageChannel

data class ActivityScope(
    val guild: Guild,
    val channel: MessageChannel
)