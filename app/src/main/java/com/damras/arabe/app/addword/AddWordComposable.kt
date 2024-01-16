package com.damras.arabe.app.addword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.damras.arabe.R
import com.damras.arabe.model.Dialect

@Composable
fun AddWord(navController: NavController, viewModel: AddWordViewModel) {
    AddWordContent(
        onNavigateBackButton = { viewModel.navigateBack(navController) },
        onAddWordButton = { french, arabic, transcription, dialect ->
            viewModel.addWord(french, arabic, transcription, dialect)
            viewModel.navigateBack(navController)
        }
    )
}

@Composable
fun AddWordContent(
    onNavigateBackButton: () -> Unit,
    onAddWordButton: (String, String, String, Dialect) -> Unit
) {
    Scaffold(
        topBar = { AddWordTopBar(onNavigateBackButton) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            AddWordForm(onAddWordButton)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordTopBar(onNavigateBackButton: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor  = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(text = stringResource(id = R.string.add_word_screen_topbar_title)) },
        navigationIcon = {
            IconButton(onClick = onNavigateBackButton) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.add_word_screen_naviback_cd)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordForm(
    onAddWordButton: (String, String, String, Dialect) -> Unit
) {

    var frenchWord by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var arabicWord by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var transcription by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var dialect = remember { mutableStateOf(Dialect.MODERN) }
    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BasicTextField(
            value = frenchWord,
            onValueChange = { frenchWord = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleLarge,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(Color.LightGray, RoundedCornerShape(percent = 30))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "FR")
                    Spacer(Modifier.width(16.dp))
                    innerTextField()
                }
            }
        )

        BasicTextField(
            value = arabicWord,
            onValueChange = { arabicWord = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleLarge,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(Color.LightGray, RoundedCornerShape(percent = 30))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "AR")
                    Spacer(Modifier.width(16.dp))
                    innerTextField()
                }
            }
        )

        BasicTextField(
            value = transcription,
            onValueChange = { transcription = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleLarge,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(Color.LightGray, RoundedCornerShape(percent = 30))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_spatial_audio_off_24),
                        contentDescription = ""
                    )
                    Spacer(Modifier.width(16.dp))
                    innerTextField()
                }
            }
        )

        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = {
                expanded.value = !expanded.value
            }
        ) {
            TextField(
                value = stringResource(id = getStringFromDialect(dialect.value)),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },

            ) {
                Dialect.entries.forEach {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = getStringFromDialect(it))) },
                        onClick = {
                            dialect.value = it
                            expanded.value = false
                        })
                }
            }
        }

        Button(
            enabled = frenchWord.text.isNotEmpty() && arabicWord.text.isNotEmpty() ,
            modifier = Modifier.align(Alignment.End),
            onClick = { onAddWordButton(frenchWord.text, arabicWord.text, transcription.text, dialect.value) })
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Check, contentDescription = "")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(id = R.string.add_word_screen_button))
            }
        }
    }
}

fun getStringFromDialect(dialect: Dialect): Int {
    return when (dialect) {
        Dialect.MODERN -> R.string.add_word_screen_dialect_modern
        Dialect.MAGHREB -> R.string.add_word_screen_dialect_maghreb
        Dialect.EGYPTIAN -> R.string.add_word_screen_dialect_egyptian
        Dialect.BEDOUIN -> R.string.add_word_screen_dialect_bedouin
        Dialect.MIDDLE_EAST -> R.string.add_word_screen_dialect_middle_east
        Dialect.ARABIC_PENINSULA -> R.string.add_word_screen_dialect_arabic_peninsula
    }
}