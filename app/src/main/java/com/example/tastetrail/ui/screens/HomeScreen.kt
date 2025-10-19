package com.example.tastetrail.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onFindRestaurants: () -> Unit) {
    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var isCuisineExpanded by remember { mutableStateOf(false) }
    var selectedCuisine by remember { mutableStateOf("Any") }
    val cuisineOptions = listOf("Any", "Italian", "Mexican", "Indian", "Chinese", "American")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Origin Field
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = origin,
                onValueChange = { origin = it },
                label = { Text("Origin") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* TODO: Implement Map Picker */ }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Pick Origin on Map")
            }
        }

        // Destination Field
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("Destination") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* TODO: Implement Map Picker */ }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Pick Destination on Map")
            }
        }

        // Cuisine Preference Dropdown
        Box(modifier = Modifier.fillMaxWidth()) {
            ExposedDropdownMenuBox(
                expanded = isCuisineExpanded,
                onExpandedChange = { isCuisineExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedCuisine,
                    onValueChange = {}, // Read-only
                    label = { Text("Cuisine Preference") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCuisineExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = isCuisineExpanded,
                    onDismissRequest = { isCuisineExpanded = false }
                ) {
                    cuisineOptions.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                selectedCuisine = it
                                isCuisineExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // Find Restaurants Button
        Button(
            onClick = onFindRestaurants,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Find Restaurants")
        }
    }
}
