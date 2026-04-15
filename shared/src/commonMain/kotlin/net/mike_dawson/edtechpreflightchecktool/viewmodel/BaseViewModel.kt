package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.NavResult
import net.mike_dawson.edtechpreflightchecktool.nav.NavResultReturner

abstract class BaseViewModel(
    protected val savedStateHandle: SavedStateHandle,
): ViewModel() {
    protected val _appUiState = MutableStateFlow(AppUiState())

    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()

    protected val _navCommandFlow = MutableSharedFlow<NavCommand>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    val navCommandFlow: Flow<NavCommand> = _navCommandFlow.asSharedFlow()

    private var lastNavResultTimestampCollected: Long = savedStateHandle.get<String>(
        KEY_LAST_COLLECTED_TS
    )?.toLong() ?: 0L
        set(value) {
            field = value
            savedStateHandle[KEY_LAST_COLLECTED_TS] = value.toString()
        }

    /**
     * Shorthand to observe results. Avoids two edge cases:
     *
     * 1. "Replay" - when the ViewModel is recreated, if no other result has been returned in the
     *    meantime, the last result would be collected again. The flow of NavResultReturner always
     *    replays the most recent result returned (required to allow a collector which starts after
     *    the result was sent to collect it).
     *
     *    This is avoided by tracking the timestamp of the last item collected.
     *
     * 2. Replay from previous viewmodel: when the user goes from screen A to screen B, then C,
     *    returns a result to screen A, and then navigates forward to screen B again with new arguments.
     *    The new instance of screen B does not remember receiving any results, so the result from
     *    the old instance of screen C looks new.
     *
     *    This is avoided by setting the alstNavResultTimestampCollected to the first start time
     *    on init.
     *
     */
    fun NavResultReturner.filteredResultFlowForKey(
        key: String,
    ) : Flow<NavResult> {
        return resultFlowForKey(key).filter {
            val isNew = it.timestamp > lastNavResultTimestampCollected
            if(isNew)
                lastNavResultTimestampCollected = it.timestamp

            isNew
        }
    }

    companion object {

        const val KEY_LAST_COLLECTED_TS = "collectedTs"

    }

}