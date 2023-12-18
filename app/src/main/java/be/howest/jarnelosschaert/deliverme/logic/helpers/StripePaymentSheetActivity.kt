package be.howest.jarnelosschaert.deliverme.logic.helpers

import android.content.Intent
import android.content.RestrictionsManager.RESULT_ERROR
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import be.howest.jarnelosschaert.deliverme.logic.services.responses.PayResponse
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class StripePaymentSheetActivity(): ComponentActivity() {
    lateinit var paymentSheet: PaymentSheet
    private lateinit var payResponse: PayResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        payResponse = intent.getParcelableExtra("payResponse")!!

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        PaymentConfiguration.init(this, payResponse.publishableKey)
        presentPaymentSheet()
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        Log.d("PaymentSheet", "PaymentSheet result: $paymentSheetResult")
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                setResult(RESULT_CANCELED)
                finish()
            }
            is PaymentSheetResult.Failed -> {
                setResult(RESULT_ERROR)
                finish()
            }
            is PaymentSheetResult.Completed -> {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
          payResponse.clientSecret,
            PaymentSheet.Configuration(
               merchantDisplayName = "DeliverMe",
                allowsDelayedPaymentMethods = true
            )
        )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}