package com.blundell.tut.gemini

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.edge.aicore.GenerativeModel
import com.google.ai.edge.aicore.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

sealed interface State {
    data object Idle : State
    data object Loading : State
    data class Success(
        val answer: String,
    ) : State
}

class SecondViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Idle)
    val state: StateFlow<State> = _state

    /**
     * Gemini-nano experimental currently requires a minSdk of 31
     * https://developer.android.com/ai/gemini-nano/experimental#configure-gradle
     *
     * This method is expected to be called before any other method on this ViewModel,
     * to deny access to lower SDK devices.
     * If this fails to happen (developer error) then the app will crash.
     */
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    fun onRequestAccess(): Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU).also {
            Log.d("TUT", "SecondViewModel init. Access was ${if (it) "allowed" else "denied"}.")
        }
    }

    fun onSubmit(request: String) {
        _state.value = State.Loading
        // Here is a method from the com.google.ai.edge.aicore:aicore library
        // The point of this example is not to do any "AI"
        // but using this method proves we are able to use the library on >= T
        // and it will crash on a device < T
        val gm = GenerativeModel(
            generationConfig = generationConfig {
                context = application
                temperature = 0.2f
                topK = 16
                maxOutputTokens = 256
            }
        )
        Log.d("TUT", "Model created! $gm")
        viewModelScope.launch(context = Dispatchers.IO) {
            delay(1000)
            _state.value = State.Success("${Random.nextInt()} Done! \n\n You asked: '$request'.")
        }
    }
}
