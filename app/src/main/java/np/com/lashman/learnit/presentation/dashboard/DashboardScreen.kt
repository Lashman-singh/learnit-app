package np.com.lashman.learnit.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import np.com.lashman.learnit.R
import np.com.lashman.learnit.domain.model.Subject
import np.com.lashman.learnit.domain.model.Task
import np.com.lashman.learnit.presentation.components.AddSubjectDialog
import np.com.lashman.learnit.presentation.components.CountCard
import np.com.lashman.learnit.presentation.components.SubjectCard
import np.com.lashman.learnit.presentation.components.tasksList
import np.com.lashman.learnit.presentation.destinations.DashboardScreenRouteDestination
import np.com.lashman.learnit.presentation.destinations.SignInScreenDestination
import np.com.lashman.learnit.presentation.destinations.SubjectScreenRouteDestination
import np.com.lashman.learnit.presentation.destinations.TaskScreenRouteDestination
import np.com.lashman.learnit.presentation.destinations.WikipediaScreenDestination
import np.com.lashman.learnit.presentation.subject.SubjectScreenNavArgs
import np.com.lashman.learnit.presentation.task.TaskScreenNavArgs
import np.com.lashman.learnit.util.SnackbarEvent

@RootNavGraph(start = true)
@Destination
@Composable
fun DashboardScreenRoute(
    navigator: DestinationsNavigator
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    if (currentUser == null) {
        // If user is not authenticated, navigate to Sign-In screen
        navigator.navigate(SignInScreenDestination())
    } else {
        val viewModel: DashboardViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val tasks by viewModel.tasks.collectAsStateWithLifecycle()

        DashboardScreen(
            state = state,
            tasks = tasks,
            onEvent = viewModel::onEvent,
            snackbarEvent = viewModel.snackbarEventFlow,
            onSignOut = {
                auth.signOut()
                navigator.navigate(SignInScreenDestination()) {
                    popUpTo(DashboardScreenRouteDestination.route) { inclusive = true }
                }
            },
            onSubjectCardClick = { subjectId ->
                subjectId?.let {
                    val navArg = SubjectScreenNavArgs(subjectId = subjectId)
                    navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
                }
            },
            onTaskCardClick = { taskId ->
                val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
                navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
            },
            onWikiButtonClick = {
                navigator.navigate(WikipediaScreenDestination())
            }
        )
    }
}

@Composable
private fun DashboardScreen(
    state: DashboardState,
    tasks: List<Task>,
    onEvent: (DashboardEvent) -> Unit,
    snackbarEvent: SharedFlow<SnackbarEvent>,
    onSignOut: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onWikiButtonClick: () -> Unit
) {
    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collectLatest { event ->
            when(event) {
                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
                SnackbarEvent.NavigateUp -> {}
            }
        }
    }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        subjectName = state.subjectName,
        goalHours = state.goalStudyHours,
        selectedColors = state.subjectCardColors,
        onSubjectNameChange = { onEvent(DashboardEvent.OnSubjectNameChange(it)) },
        onGoalHoursChange = { onEvent(DashboardEvent.OnGoalStudyHoursChange(it)) },
        onColorChange = { onEvent(DashboardEvent.OnSubjectCardColorChange(it)) },
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = {
            onEvent(DashboardEvent.SaveSubject)
            isAddSubjectDialogOpen = false
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { DashboardScreenTopBar(onSignOut = onSignOut) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    subjectCount = state.totalSubjectCount,
                    studiedHours = state.totalStudiedHours.toString(),
                    goalHours = state.totalGoalStudyHours.toString()
                )
            }
            item {
                SubjectCardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = state.subjects,
                    onAddIconClicked = { isAddSubjectDialogOpen = true },
                    onSubjectCardClick = onSubjectCardClick
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onWikiButtonClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00d2ff)
                        )
                    ) {
                        Text(text = "Wikipedia")
                    }
                }
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n " +
                        "Click the + button in subject screen to add new task.",
                tasks = tasks,
                onCheckBoxClick = { onEvent(DashboardEvent.OnTaskIsCompleteChange(it)) },
                onTaskCardClick = onTaskCardClick
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar(onSignOut: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "LearnIT",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        actions = {
            IconButton(onClick = onSignOut) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Sign Out",
                    tint = Color.Black
                )
            }
        }
    )
}

@Composable
private fun CountCardsSection(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String
) {
    Row(modifier = modifier) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
    }
}

@Composable
private fun SubjectCardsSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subjects.\n Click the + button to add new subject.",
    onAddIconClicked: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onAddIconClicked) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Subject"
                )
            }
        }
        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.books),
                contentDescription = emptyListText
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors.map { Color(it) },
                    onClick = { onSubjectCardClick(subject.subjectId) }
                )
            }
        }
    }
}