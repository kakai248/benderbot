import activities.Activity

sealed class Action {
    data class ChangeActivity(val scope: ActivityScope, val activity: Activity) : Action()
    data class ExitActivity(val scope: ActivityScope) : Action()
}