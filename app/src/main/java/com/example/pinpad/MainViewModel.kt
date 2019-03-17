package com.example.pinpad

import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.example.pinpad.model.PinBlockGenerator

/**
 * Handles the receiving an entered PIN and exposing an generated PIN block.
 */
class MainViewModel(private val pinBlockGenerator: PinBlockGenerator = PinBlockGenerator()) : ViewModel() {

    val userPin = ObservableField<String>()
    val isPinValid = ObservableBoolean(false)
    val generatedPinBlock = ObservableField<String>()

    init {
        userPin.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                // If 3 is less than the length set valid to true. If there is no length use 0.
                isPinValid.set(PinBlockGenerator.MINIMUM_FORMAT_3_LENGTH <= userPin.get()?.length ?: 0)
            }
        })
    }

    fun onGenerateClicked(view: View) {
        userPin.get()?.let {
            val block = pinBlockGenerator.generateFormat3Block(it)
            generatedPinBlock.set(block)
        }
    }
}