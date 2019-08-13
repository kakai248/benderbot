import activities.Activity

sealed class Action {
    data class ChangeActivity(val activity: Activity) : Action()
    object ExitActivity : Action()
}