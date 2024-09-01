package id.nesd.rcache.demo.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun FloatingMenu(
    isMenuOpen: MutableState<Boolean>,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background overlay when menu is open
        AnimatedVisibility(
            visible = isMenuOpen.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize() // Ensure it fills the entire screen
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable {
                        isMenuOpen.value = false
                    }
                    .zIndex(1f)
            )
        }

        // Menu content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Padding is only for content, not the overlay
                .zIndex(2f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            AnimatedVisibility(
                visible = isMenuOpen.value,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                    content = content
                )
            }

            // FAB Button
            FloatingActionButton(
                onClick = { isMenuOpen.value = !isMenuOpen.value },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = if (isMenuOpen.value) Icons.Default.Close else Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun FloatingMenuSample() {
    val isMenuOpen = remember { mutableStateOf(false) }

    FloatingMenu(isMenuOpen = isMenuOpen) {
        // Submenu items
        FloatingActionButton(
            onClick = { /* Handle Action 1 */ },
            containerColor = Color.Red,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("A1")
        }
        FloatingActionButton(
            onClick = { /* Handle Action 2 */ },
            containerColor = Color.Green,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("A2")
        }
        FloatingActionButton(
            onClick = { /* Handle Action 3 */ },
            containerColor = Color.Blue,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("A3")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingMenuSamplePreview() {
    FloatingMenuSample()
}