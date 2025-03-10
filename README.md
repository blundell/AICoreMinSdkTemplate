#### AICore has minSDK 31, this template shows how to use it in a lower minSDK Android app

- The app module is minSDK 29

- The gemini module is minSDK 31

Normally you would get a manifest merger error:

```
Manifest merger failed : uses-sdk:minSdkVersion 29 cannot be smaller than version 31 declared in library [:gemini] /MyApplication/gemini/build/intermediates/merged_manifest/debug/processDebugManifest/AndroidManifest.xml as the library might be using APIs not available in 29
	Suggestion: use a compatible library with a minSdk of at most 29,
		or increase this project's minSdk version to at least 31,
		or use tools:overrideLibrary="com.blundell.tut.gemini" to force usage (may lead to runtime failures)
```

This example uses `tools:overrideLibrary` in combination with a SDK version check in the gemini module, to allow you to test out AICore in
an application that has a lower minSDK that Gemini AICore requires.

- Inside of `gemini/src/main/java/com/blundell/tut/gemini/SecondViewModel.kt` there is an SDK atLeast check.
- The `:gemini` module navigational composable checks with the ViewModel before proceeding to the UI.
- `androidx.navigation.compose.NavHost` is used in the `MainActivity` to navigate between the two modules composables.

We will either show the `SecondScreen` or show a `NoAccessScreen`:

```kotlin
@Composable
fun SecondComposable(viewModel: SecondViewModel = viewModel()) {
    val accessible = remember { viewModel.onRequestAccess() }
    if (accessible) {
        val state by viewModel.state.collectAsState()
        SecondScreen(state) {
            viewModel.onSubmit(it)
        }
    } else {
        NoAccessScreen()
    }
}
```
