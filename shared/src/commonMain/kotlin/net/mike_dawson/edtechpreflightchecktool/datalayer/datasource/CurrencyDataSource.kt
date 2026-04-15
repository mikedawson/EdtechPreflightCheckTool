package net.mike_dawson.edtechpreflightchecktool.datalayer.datasource

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Currency

class CurrencyDataSource(
    private val json: Json,
    private val currencyJsonProvider: suspend () -> String,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default + Job())
) {

    private val completable  = CompletableDeferred<List<Currency>>()

    init {
        scope.launch {
            val currencyMap: Map<String, Currency> = json.decodeFromString(
                MapSerializer(String.serializer(), Currency.serializer()),
                currencyJsonProvider()
            )
            completable.complete(currencyMap.values.toList().sortedBy{ it.code } )
        }
    }


    suspend fun getCurrencies(): List<Currency> {
        return completable.await()
    }

}