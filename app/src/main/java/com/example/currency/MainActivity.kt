package com.example.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

import android.view.View
import android.widget.AdapterView

class MainActivity : AppCompatActivity() {

    private lateinit var editTextSource: EditText
    private lateinit var editTextTarget: EditText
    private lateinit var spinnerSourceCurrency: Spinner
    private lateinit var spinnerTargetCurrency: Spinner

    private val exchangeRates = mapOf(
        "USD" to 1.0,    // Tỉ giá USD
        "VND" to 24000.0, // Tỉ giá VND
        "EUR" to 0.85    // Tỉ giá EUR
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextSource = findViewById(R.id.editTextSource)
        editTextTarget = findViewById(R.id.editTextTarget)
        spinnerSourceCurrency = findViewById(R.id.spinnerSourceCurrency)
        spinnerTargetCurrency = findViewById(R.id.spinnerTargetCurrency)

        val currencies = arrayOf("USD", "VND", "EUR")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSourceCurrency.adapter = adapter
        spinnerTargetCurrency.adapter = adapter

        // Lắng nghe sự thay đổi trong editTextSource để cập nhật kết quả
        editTextSource.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Lắng nghe sự thay đổi của Spinner để cập nhật kết quả
        spinnerSourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTargetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun convertCurrency() {
        val sourceAmount = editTextSource.text.toString().toDoubleOrNull() ?: 0.0
        val sourceCurrency = spinnerSourceCurrency.selectedItem.toString()
        val targetCurrency = spinnerTargetCurrency.selectedItem.toString()

        val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
        val targetRate = exchangeRates[targetCurrency] ?: 1.0

        val convertedAmount = sourceAmount * targetRate / sourceRate
        editTextTarget.setText(String.format("%.2f", convertedAmount))
    }
}
