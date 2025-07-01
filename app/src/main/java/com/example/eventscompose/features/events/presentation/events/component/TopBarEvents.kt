package com.example.eventscompose.features.events.presentation.events.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.eventscompose.features.events.data.model.CategoriesResponseItem
import com.example.eventscompose.features.events.data.model.EventsResponseItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarEvents(
    groupedEvents: Map<String, List<EventsResponseItem>>,
    categories: List<CategoriesResponseItem>,
    onCategorySelected: (String) -> Unit,
    showCategoryMenu: Boolean,
//    onCalendarToggle: () -> Unit,
    onToggleCategoryMenu: () -> Unit
) {

    TopAppBar(
        title = {
            Text(
                "Events",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle drawer */ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            TextButton(onClick = {
                onToggleCategoryMenu()
            }) {
                Text("CATEGORIES")
            }

            DropdownMenu(
                expanded = showCategoryMenu,
                onDismissRequest = {
                    onToggleCategoryMenu()
                }
            ) {
                DropdownMenuItem(text = { Text("All") }, onClick = {
                    onCategorySelected("-1")
                    onToggleCategoryMenu()
                })

                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.shortTitle) },
                        onClick = {
                            onCategorySelected(category.id)
                            onToggleCategoryMenu()
                        }
                    )
                }
            }

            IconButton(onClick = {
//                onCalendarToggle()
            }) {
                Icon(Icons.Default.DateRange, contentDescription = "Date")
            }
        }
    )
}