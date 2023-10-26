package be.howest.jarnelosschaert.deliverme.ui.screens.settingPages

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.ui.helpers.GeneralTextPopup
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Label
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.SmallButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title

data class PopupContent(
    val title: String,
    val label: String,
    val confirmButton: String = "Change",
    val toastText: String,
    val onDismiss: () -> Unit,
    val onConfirm: () -> Unit
)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    logout: () -> Unit,
    deleteAccount: () -> Unit
) {
    var isPopupVisible by remember { mutableStateOf(false) }
    var popupContent by remember { mutableStateOf(PopupContent("", "", "", "", {}, {})) }
    Box(modifier = modifier.fillMaxWidth()) {
        Column() {
            Title(text = "Profile", onGoBack = onGoBack, withGoBack = true)
            LazyColumn(content = {
                item {
                    ProfilePicture()
                    Profile(onEdit = { popupContent = it; isPopupVisible = true }, onDismiss = {isPopupVisible = false})
                    ProfileButtons(logout = logout, deleteAccount = deleteAccount)
                }
            })
        }
    }
    if (isPopupVisible) {
        GeneralTextPopup(
            title = popupContent.title,
            label = popupContent.label,
            confirmButton = popupContent.confirmButton,
            toastText = popupContent.toastText,
            onDismiss = popupContent.onDismiss,
            onConfirm = { isPopupVisible = false }
        )
    }
}

@Composable
fun ProfileButtons(
    logout: () -> Unit,
    deleteAccount: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        SmallButton(text = "Add address", onClick = {})
        SmallButton(text = "Change password", onClick = {})
        SmallButton(text = "Change profile picture", onClick = {})
        SmallButton(text = "Log out", onClick = logout, isError = true, modifier = Modifier.align(Alignment.End))
        SmallButton(text = "Delete account", onClick = deleteAccount, isError = true, modifier = Modifier.align(Alignment.End))
    }
}

@Composable
fun Profile(
    onEdit : (PopupContent) -> Unit,
    onDismiss: () -> Unit
) {
    EditableContentLabel(label = "Username", text = "Daan Hautekiet", onEdit = onEdit, popupContent = PopupContent(
        title = "Change username",
        label = "New username",
        toastText = "Username changed",
        onDismiss = {onDismiss()},
        onConfirm = {}
    ))
    EditableContentLabel(label = "Email", text = "daan.hautekiet@gmail.com", onEdit = onEdit, popupContent = PopupContent(
        title = "Change email",
        label = "New email",
        toastText = "Email changed",
        onDismiss = {onDismiss()},
        onConfirm = {}
    ))
    EditableContentLabel(label = "Phone number", text = "0472 12 34 56", onEdit = onEdit, popupContent = PopupContent(
        title = "Change phone number",
        label = "New phone number",
        toastText = "Phone number changed",
        onDismiss = {onDismiss()},
        onConfirm = {}
    ))
    EditableContentLabel(label = "Home", text = "Kortrijksestraat 12, 8500 Kortrijk", onEdit = onEdit, popupContent = PopupContent(
        title = "Change home address",
        label = "New home address",
        toastText = "Home address changed",
        onDismiss = {onDismiss()},
        onConfirm = {}
    ))
    EditableContentLabel(label = "Extra address 1", text = "Kortrijksestraat 14, 8500 Kortrijk", onEdit = onEdit, popupContent = PopupContent(
        title = "Change extra address 1",
        label = "New extra address 1",
        toastText = "Extra address 1 changed",
        onDismiss = {onDismiss()},
        onConfirm = {}
    ))
}

@Composable
fun EditableContentLabel(
    label: String,
    text: String,
    onEdit: (PopupContent) -> Unit,
    popupContent: PopupContent
) {
    Label(text = label)
    Box(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colors.onBackground, MaterialTheme.shapes.small)
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Content(text = text, modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .size(20.dp)
                    .clickable(onClick = {onEdit(popupContent)})
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ProfilePicture() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Profile picture",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}
