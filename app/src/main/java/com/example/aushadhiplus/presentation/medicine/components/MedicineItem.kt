package com.example.aushadhiplus.presentation.medicine.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.aushadhiplus.domain.model.Medicine

@Composable
fun MedicineItem(
    medicine: Medicine
) {
    val isLowStock = medicine.quantity <= medicine.lowStockThreshold

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(color = Color(0xFFF6F8F7)), elevation = CardDefaults.cardElevation(4.dp)

    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // Medicine Name
            Text(
                text = medicine.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Manufacturer
            Text(
                text = "Manufacturer: ${medicine.manufacturer}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = buildAnnotatedString {
                        // This part stays regular
                        append("Price: ")

                        // This part becomes bold
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("₹${medicine.price}")
                        }
                    }, style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = buildAnnotatedString {
                        append("Qty: ")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("₹${medicine.price}")
                        }
                    }, style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Category: ${medicine.category}", style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Expiry: ${medicine.expiryDate.take(10)}",
                style = MaterialTheme.typography.bodyLarge
            )

            if (isLowStock) {

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Low Stock",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


val data = Medicine(
    id = "5",
    name = "Crocin Tablet",
    manufacturer = "GSK",
    price = 35,
    quantity = 5,
    expiryDate = "2026-10-10",
    category = "Tablet",
    lowStockThreshold = 5
)


@Preview(showBackground = true)
@Composable
private fun MedicineItemPreview() {
    MedicineItem(
        medicine = data
    )

}