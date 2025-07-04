package com.example.eventscompose.features.events.presentation.events.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.eventscompose.features.events.data.model.Category
import kotlin.math.exp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarEvents(
    categories: List<Category>,
    selectedCategoryId : String,
    onCategorySelected: (String) -> Unit,
    showCategoryMenu: Boolean,
    onCalendarToggle: () -> Unit,
    onToggleCategoryMenu: () -> Unit
) {

    TopAppBar(
        title = {
            Text(
                "Events",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle drawer */ }) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            TextButton(onClick = {
                onToggleCategoryMenu()
            }) {
                Text(
                    "CATEGORIES",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            DropdownMenu(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                expanded = showCategoryMenu,
                onDismissRequest = {
                    onToggleCategoryMenu()
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = (selectedCategoryId == "-1"),
                                onClick = null
                            )
                            Text(
                                "All",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    onClick = {
                        onCategorySelected("-1")
                        onToggleCategoryMenu()
                    }
                )

                categories.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedCategoryId == category.id,
                                    onClick = null
                                )
                                Text(
                                    category.shortTitle,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        onClick = {
                            onCategorySelected(category.id)
                            onToggleCategoryMenu()
                        }
                    )
                }
            }

            IconButton(onClick = {
                onCalendarToggle()
            }) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Date",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}