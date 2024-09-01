package id.nesd.rcache.demo.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DismissibleList(
    items: List<T>,
    onDismiss: (T) -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items) { item ->
            var isDismissed by remember { mutableStateOf(false) }
            val dismissScale by animateFloatAsState(targetValue = if (isDismissed) 0f else 1f)

            if (!isDismissed) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .scale(dismissScale)
                ) {
                    val swipeState = myRememberSwipeToDismissBoxState { dismissValue ->
                        if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                            isDismissed = true
                            onDismiss(item)
                            return@myRememberSwipeToDismissBoxState true
                        }
                        return@myRememberSwipeToDismissBoxState false
                    }

                    SwipeToDismissBox(
                        state = swipeState,
                        backgroundContent = {
                            if (swipeState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.error)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Row {
                                        Text(text = "Delete", color = Color.White)
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        },
                        content = {
                            itemContent(item)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myRememberSwipeToDismissBoxState(onDismissed: (SwipeToDismissBoxValue) -> Boolean): SwipeToDismissBoxState {
    return rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> onDismissed(dismissValue)
                SwipeToDismissBoxValue.StartToEnd -> false // Disable StartToEnd swipe
                else -> false
            }
        }
    )
}