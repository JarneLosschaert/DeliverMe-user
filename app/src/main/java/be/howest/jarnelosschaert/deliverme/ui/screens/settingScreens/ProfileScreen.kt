package be.howest.jarnelosschaert.deliverme.ui.screens.settingScreens

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
import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralChoicePopup
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralTextPopup
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Content
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Label
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showAddress
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showPhoneNumber

data class PopupContent(
    val title: String,
    val label: String = "",
    val content: String = "",
    val confirmButton: String = "Change",
    val onDismiss: () -> Unit,
    val onConfirm: (String) -> Unit
)

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    customer: Customer,
    onGoBack: () -> Unit,
    logout: () -> Unit,
    deleteAccount: () -> Unit,
    changeUserName: (String) -> Unit,
    changeEmail: (String) -> Unit,
    changePhone: (String) -> Unit,
    changeAddress: () -> Unit,
    changePassword: (String) -> Unit
) {
    var isTextPopupVisible by remember { mutableStateOf(false) }
    var textPopupContent by remember { mutableStateOf(PopupContent("", "", "", "", {}, {})) }
    if (isTextPopupVisible) {
        GeneralTextPopup(
            title = textPopupContent.title,
            label = textPopupContent.label,
            confirmButton = textPopupContent.confirmButton,
            onDismiss = textPopupContent.onDismiss,
            onConfirm = textPopupContent.onConfirm
        )
    }
    var isChoicePopupVisible by remember { mutableStateOf(false) }
    var choicePopupContent by remember { mutableStateOf(PopupContent("", "", "", "", {}, {})) }
    if (isChoicePopupVisible) {
        GeneralChoicePopup(
            title = choicePopupContent.title,
            content = choicePopupContent.content,
            confirmButton = choicePopupContent.confirmButton,
            onDismiss = choicePopupContent.onDismiss,
            onConfirm = choicePopupContent.onConfirm
        )
    }
    Box(modifier = modifier.fillMaxWidth()) {
        Column() {
            Title(text = "Profile", onGoBack = onGoBack, withGoBack = true)
            LazyColumn(content = {
                item {
                    ProfilePicture()
                    Profile(
                        customer = customer,
                        onTextEdit = { textPopupContent = it; isTextPopupVisible = true },
                        onChoiceEdit = { choicePopupContent = it; isChoicePopupVisible = true },
                        onDismiss = { isTextPopupVisible = false; isChoicePopupVisible = false },
                        changeUserName = changeUserName,
                        changeEmail = changeEmail,
                        changePhone = changePhone,
                        changeAddress = changeAddress
                    )
                    AuthButtons(
                        logout = logout,
                        deleteAccount = deleteAccount,
                        changePassword = changePassword,
                        onEdit = { choicePopupContent = it; isChoicePopupVisible = true },
                        onEditPassword = { textPopupContent = it; isTextPopupVisible = true },
                        onDismiss = { isChoicePopupVisible = false; isTextPopupVisible = false }
                    )
                }
            })
        }
    }

}

@Composable
fun AuthButtons(
    logout: () -> Unit,
    deleteAccount: () -> Unit,
    changePassword: (String) -> Unit,
    onEdit: (PopupContent) -> Unit,
    onEditPassword: (PopupContent) -> Unit,
    onDismiss: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        GeneralButton(text = "Change password", onClick = {
            onEditPassword(
                PopupContent(
                    title = "Change password",
                    label = "New password",
                    onDismiss = { onDismiss() },
                    onConfirm = { changePassword(it) }
                )
            )
        })
        GeneralButton(text = "Change profile picture", onClick = {})
        GeneralButton(
            text = "Log out",
            isError = true,
            modifier = Modifier.align(Alignment.End),
            onClick = {
                onEdit(
                    PopupContent(
                        title = "Log out",
                        content = "Are you sure you want to log out?",
                        confirmButton = "Log out",
                        onDismiss = { onDismiss() },
                        onConfirm = { logout() }
                    )
                )
            }
        )
        GeneralButton(
            text = "Delete account",
            isError = true,
            modifier = Modifier.align(Alignment.End),
            onClick = {
                onEdit(
                    PopupContent(
                        title = "Delete account",
                        content = "Are you sure you want to delete your account?",
                        confirmButton = "Delete",
                        onDismiss = { onDismiss() },
                        onConfirm = { deleteAccount() }
                    )
                )
            }
        )
    }
}

@Composable
fun Profile(
    customer: Customer,
    onTextEdit: (PopupContent) -> Unit,
    onChoiceEdit: (PopupContent) -> Unit,
    onDismiss: () -> Unit,
    changeUserName: (String) -> Unit,
    changeEmail: (String) -> Unit,
    changePhone: (String) -> Unit,
    changeAddress: () -> Unit
) {
    EditableContentLabel(label = "Username",
        text = customer.person.name,
        onEdit = onTextEdit,
        popupContent = PopupContent(
            title = "Change username",
            label = "New username",
            onDismiss = { onDismiss() },
            onConfirm = { changeUserName(it) }
        ))
    EditableContentLabel(label = "Email",
        text = customer.person.email,
        onEdit = onTextEdit,
        popupContent = PopupContent(
            title = "Change email",
            label = "New email",
            onDismiss = { onDismiss() },
            onConfirm = { changeEmail(it) }
        ))
    EditableContentLabel(label = "Phone number",
        text = showPhoneNumber(customer.person.phone),
        onEdit = onTextEdit,
        popupContent = PopupContent(
            title = "Change phone number",
            label = "New phone number",
            onDismiss = { onDismiss() },
            onConfirm = { changePhone(it) }
        ))
    EditableContentLabel(label = "Home address",
        text = showAddress(customer.homeAddress),
        onEdit = onChoiceEdit,
        popupContent = PopupContent(
            title = "Change home address",
            content = "Are you sure you want to change your home address?",
            onDismiss = { onDismiss() },
            onConfirm =  { changeAddress() }
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
                    .clickable(onClick = { onEdit(popupContent) })
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
