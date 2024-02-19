package com.dnapayments.mp.utils.compose_paging

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

inline fun <Value : Any> LazyListScope.items(
    feed: List<Value>,
    noinline requestNextPage: () -> Unit,
    noinline key: ((item: Value) -> Any)? = null,
    crossinline content: @Composable LazyItemScope.(item: Value) -> Unit
) {
    items(
        count = feed.size,
        key = if (key != null) { index -> key(feed[index]) } else null
    ) { index ->
        val fetchDistanceReached = remember(index, feed.size) {
            index == feed.size - 1
        }
        LaunchedEffect(fetchDistanceReached) {
            if (fetchDistanceReached) requestNextPage()
        }
        content(feed[index])
    }
}

inline fun <Value : Any> LazyListScope.itemsIndexed(
    feed: List<Value>,
    noinline requestNextPage: () -> Unit,
    noinline key: ((index: Int, item: Value) -> Any)? = null,
    crossinline content: @Composable LazyItemScope.(index: Int, item: Value) -> Unit
) {
    items(
        count = feed.size,
        key = if (key != null) { index -> key(index, feed[index]) } else null
    ) { index ->
        val fetchDistanceReached = remember(index, feed.size) {
            index == feed.size - 1
        }
        LaunchedEffect(fetchDistanceReached) {
            if (fetchDistanceReached) requestNextPage()
        }
        content(index, feed[index])
    }
}
