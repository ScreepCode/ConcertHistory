package de.buseslaar.concerthistory.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * BaseViewModel is a base class for our ViewModels that provides common functionality.
 * This class includes a property to track the loading state and a method to
 * perform asynchronous requests with error handling.
 */
open class BaseViewModel : ViewModel() {
    /**
     * A MutableState property that tracks the loading state.
     * When `true`, it indicates that a request is being executed.
     */
    var isLoading by mutableStateOf(false)

    /**
     * Performs an asynchronous request and handles errors and the completion of the request.
     *
     * @param onError A function that is called when an error occurs. By default, the error is printed.
     * @param onFinally A function that is called after the request is completed, regardless of success. By default, nothing is executed.
     * @param onRequest A suspend function that performs the actual request.
     */
    inline fun asyncRequest(
        crossinline onError: (ex: Exception) -> Unit = { ex -> println(ex) },
        crossinline onFinally: (success: Boolean) -> Unit = { },
        crossinline onRequest: suspend () -> Unit
    ) = viewModelScope.launch {
        var success = false
        try {
            isLoading = true
            onRequest()
            success = true
        } catch (ex: Exception) {
            onError(ex)
        } finally {
            isLoading = false
            onFinally(success)
        }
    }
}
