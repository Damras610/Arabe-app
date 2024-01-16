package com.damras.arabe.app.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.damras.arabe.R
import com.damras.arabe.app.theme.ArabeVocabulaireTheme
import com.damras.arabe.appModule
import com.damras.arabe.model.Dialect
import com.damras.arabe.model.MultiDialectWord
import com.damras.arabe.model.Word
import org.koin.compose.KoinApplication

@Composable
fun Main(navController: NavController, viewModel: MainViewModel) {
    MainContent(
        wordsFrenchToArabic = viewModel.frenchToArabic.value,
        wordsArabicToFrench = viewModel.arabicToFrench.value,
        dictionaryMode = viewModel.dictionaryMode.value,
        onAddWordButtonClicked = { viewModel.navigateToAddWord(navController) },
        onSwitchDictionaryMode = { viewModel.switchDictionaryMode() }
    )
}

@Composable
fun MainContent(
    wordsFrenchToArabic: Map<Char, List<MultiDialectWord>>,
    wordsArabicToFrench: Map<Char, List<Word>>,
    dictionaryMode: MainViewModel.DICTIONARY_MODE,
    onAddWordButtonClicked: () -> Unit,
    onSwitchDictionaryMode: () -> Unit
) {
    Scaffold(
        topBar = { MainTopBar(dictionaryMode, onAddWordButtonClicked, onSwitchDictionaryMode) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            if (dictionaryMode == MainViewModel.DICTIONARY_MODE.FRENCH_TO_ARABIC)
                WordsListFrench(wordsFrenchToArabic)
            if (dictionaryMode == MainViewModel.DICTIONARY_MODE.ARABIC_TO_FRENCH)
                WordsListArabic(wordsArabicToFrench)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    dictionaryMode: MainViewModel.DICTIONARY_MODE,
    onAddWordButtonClicked: () -> Unit,
    onSwitchDictionaryMode: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor  = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(text = stringResource(id = R.string.main_screen_topbar_title)) },
        actions = {
            IconButton(onClick = { onAddWordButtonClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.main_screen_fab_cd)
                )
            }
            IconButton(onClick = { onSwitchDictionaryMode() }) {
                if (dictionaryMode == MainViewModel.DICTIONARY_MODE.FRENCH_TO_ARABIC)
                    Text(text = "FR")
                else
                    Text(text = "AR")
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordsListFrench(groupedWords: Map<Char, List<MultiDialectWord>>) {
    LazyColumn {
        groupedWords.forEach { (letter, words) ->
            stickyHeader {
                CharacterHeader(letter, false)
            }

            items(words) { word ->
                WordRowFrench(word)
                Divider()
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordsListArabic(groupedWords: Map<Char, List<Word>>) {
    LazyColumn {
        groupedWords.forEach { (letter, words) ->
            stickyHeader {
                CharacterHeader(letter, true)
            }

            items(words) { word ->
                WordRowArabic(word)
                Divider()
            }
        }

    }
}

@Composable
fun CharacterHeader(letter: Char, toEnd: Boolean) {
    val arrangement = if (toEnd) Arrangement.End else Arrangement.Start
    Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = arrangement) {
            Text(
                text = letter.toString(),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun WordRowFrench(word: MultiDialectWord) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 48.dp)
        .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Row {
            FrenchWord(wordInFrench = word.french)
            Text(modifier = Modifier.padding(horizontal = 4.dp), text = ":")
            Column {
                word.words.forEach {
                    ArabicWord(it.arabic, it.dialect, it.transcription)
                }
            }
        }
    }
}

@Composable
fun WordRowArabic(word: Word) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 48.dp)
        .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically) {
        Row {
            FrenchWord(wordInFrench = word.french)
            Text(modifier = Modifier.padding(horizontal = 4.dp), text = ":", style = MaterialTheme.typography.titleLarge)
            ArabicWord(word.arabic, word.dialect, word.transcription, true)
        }

    }
}

@Composable
fun FrenchWord(wordInFrench: String) {
    Row {
        Text(
            text = wordInFrench,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun ArabicWord(wordInArabic: String, dialect: Dialect, transcription: String?, reversed: Boolean = false) {
    Row {
        if (!reversed) {
            ArabicDialectFlag(dialect = dialect)
            ArabicWord(wordInArabic)
            ArabicTranscription(transcription)
        } else {
            ArabicTranscription(transcription)
            ArabicWord(wordInArabic)
            ArabicDialectFlag(dialect = dialect)
        }
    }
}

@Composable
fun ArabicTranscription(transcription: String?) {
    if (!transcription.isNullOrBlank()) {
        Text(
            text = "($transcription)",
            style = MaterialTheme.typography.titleLarge,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun ArabicWord(wordInArabic: String) {
    Text(
        modifier = Modifier
            .padding(horizontal = 4.dp),
        text = wordInArabic,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun ArabicDialectFlag(dialect: Dialect) {
    val dialectDrawable = getIconFromDialect(dialect)
    if (dialectDrawable != 0) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(24.dp),
            painter = painterResource(id = dialectDrawable),
            contentDescription = "" // TODO
        )
    }
}

fun getIconFromDialect(dialect: Dialect): Int {
    return when (dialect) {
        Dialect.MAGHREB -> R.drawable.flag_of_maghreb
        Dialect.EGYPTIAN -> R.drawable.flag_of_egypt
        Dialect.BEDOUIN -> R.drawable.flag_of_libya
        Dialect.MIDDLE_EAST -> R.drawable.flag_of_palestine
        Dialect.ARABIC_PENINSULA -> R.drawable.flag_of_saudi_arabia
        else -> 0
    }
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
fun MainPreview() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        val words = listOf(
            Word(0, "Maison", "الرَّحْمـَنِ", Dialect.MODERN,""),
            Word(0, "Arbre", "الْعَالَمِينَ", Dialect.MODERN,""),
            Word(0, "Abricot", "الرَّحِيمِ", Dialect.MODERN,""),
            Word(0, "Abrit", "بِسْمِ ", Dialect.MODERN,""),
            Word(0, "Colonne", "الرَّحْمـنِ ", Dialect.MODERN,""),
            Word(0, "Francais", "مَـالِكِ ", Dialect.MODERN,""),
            Word(0, "Transit", "يَوْمِ ", Dialect.MODERN,""),
            Word(0, "Transport", "الدِّينِ", Dialect.MODERN,""),
            Word(0, "Portable", "الرَّحِيمِ", Dialect.MODERN,""),
            Word(0, "Portée", "الْعَالَمِينَ", Dialect.MODERN,""),
            Word(0, "Prisonnier", "الضَّالِّينَ", Dialect.MODERN,""),
            Word(0, "Liberté", "عَلَيهِمْ ", Dialect.MODERN,""),
            Word(0, "Renouveau", "عَلَيهِمْ", Dialect.MODERN,""),
            Word(0, "Bonheur", "نَسْتَعِينُ", Dialect.MODERN,""),
            Word(0, "Paris", "الدِّينِ", Dialect.MODERN,""),
            Word(0, "Vivre", "نَعْبُدُ ", Dialect.MODERN,""),
            Word(0, "Ordinateur", "رَبِّ ", Dialect.MODERN,""),
        )
        val wordsFrenchToArabic = words.sortedBy {
            it.french
        }.groupBy {
            it.french
        }.map {
            MultiDialectWord(it.key, it.value)
        }.groupBy {
            it.french.first()
        }

        val wordsArabicToFrench = words.sortedBy {
            it.arabic
        }.groupBy {
            it.arabic.first()
        }

        ArabeVocabulaireTheme {
            MainContent(wordsFrenchToArabic, wordsArabicToFrench, MainViewModel.DICTIONARY_MODE.ARABIC_TO_FRENCH, {}, {})
        }
    }
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
fun CharacterHeaderPreview() {
    ArabeVocabulaireTheme {
        Column {
            CharacterHeader('A', false)
            CharacterHeader('B', false)
            CharacterHeader('ا', true)
            CharacterHeader('ك', true)
        }
    }
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
fun WordRowFrenchPreview() {
    ArabeVocabulaireTheme {
        Column {
            WordRowFrench(MultiDialectWord("Bienvenue", listOf(
                Word(0, "Bienvenue", "مرحبا", Dialect.BEDOUIN, "mar-haban"),
                Word(0, "Bienvenue", "بِسْمِ", Dialect.EGYPTIAN, "mar-haban"),
                Word(0, "Bienvenue", "الرَّحْمـَنِ", Dialect.ARABIC_PENINSULA, "mar-haban")
                )
            ))
        }
    }
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
fun WordRowArabicPreview() {
    ArabeVocabulaireTheme {
        Column {
            WordRowArabic(Word(0, "Bienvenue", "مرحبا", Dialect.BEDOUIN, "mar-haban"))
        }
    }
}