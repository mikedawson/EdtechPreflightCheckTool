package net.mike_dawson.edtechpreflightchecktool.datalayer.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import kotlin.time.Clock

class PlanDataSource(
    private val settings: Settings,
    private val json: Json,
) {

    data class InvalidationCommand(
        val time: Long = Clock.System.now().toEpochMilliseconds()
    )

    private val _invalidationFlow = MutableSharedFlow<InvalidationCommand>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    init {
        _invalidationFlow.tryEmit(InvalidationCommand())
    }

    fun store(plan: Plan) {
        settings[KEY_ID_PREFIX + plan.id] = json.encodeToString(Plan.serializer(), plan)
        _invalidationFlow.tryEmit(InvalidationCommand())
    }

    fun get(id: String): Plan? {
        return settings.getStringOrNull(KEY_ID_PREFIX + id)?.let {
            json.decodeFromString(Plan.serializer(), it)
        }
    }

    fun listAllAsFlow(): Flow<List<Plan>> {
        return _invalidationFlow.map {
            settings.keys.filter { it.startsWith(KEY_ID_PREFIX) }
                .mapNotNull { key ->
                    settings.getStringOrNull(key)?.let { jsonStr ->
                        json.decodeFromString(Plan.serializer(), jsonStr)
                    }
                }
        }
    }


    companion object {

        const val KEY_ID_PREFIX = "plan_"


    }

}