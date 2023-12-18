package be.howest.jarnelosschaert.deliverme.ui.screens

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.helpers.StripePaymentSheetActivity
import be.howest.jarnelosschaert.deliverme.logic.models.Package
import be.howest.jarnelosschaert.deliverme.logic.services.responses.PayResponse
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.ContentLabel
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.Title
import be.howest.jarnelosschaert.deliverme.ui.helpers.functions.showAddress

@Composable
fun PayScreen(
    modifier: Modifier = Modifier,
    appPackage: Package,
    context: Context,
    payResponse: PayResponse,
    onGoBack: () -> Unit,
    onPay: (ActivityResult) -> Unit
) {
    Box(modifier = modifier) {
        LazyColumn(content = {
            item {
                Title(text = "Delivery", onGoBack = onGoBack, withGoBack = true)
                ContentLabel(label = "Sender", content = appPackage.sender.person.name)
                ContentLabel(
                    label = "Sender address",
                    content = showAddress(appPackage.senderAddress)
                )
                ContentLabel(label = "Receiver", content = appPackage.receiver.person.name)
                ContentLabel(
                    label = "Receiver address",
                    content = showAddress(appPackage.receiverAddress)
                )
                ContentLabel(label = "Package size", content = appPackage.packageSize.value)
                ContentLabel(label = "Description", content = appPackage.description)
                ContentLabel(label = "Fee", content = "€ ${appPackage.fee}")
                PayButton(context = context, payResponse = payResponse, onPay = onPay)
            }
        })
    }
}

@Composable
fun PayButton(
    context: Context,
    payResponse: PayResponse,
    onPay: (ActivityResult) -> Unit
) {
    val intent = Intent(context, StripePaymentSheetActivity::class.java)
    intent.putExtra("payResponse", payResponse)

    val stripeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { onPay(it) }

    GeneralButton(
        onClick = { stripeLauncher.launch(intent) }, text = "Pay delivery",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}