package com.example.oblig2nedbetaling

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.pow
import kotlin.math.roundToInt

class SharedViewModel: ViewModel() {

    private var _loan = MutableLiveData("200000")
    val loan: LiveData<String> = _loan

    private var _interest = MutableLiveData("3.6")
    val interest: LiveData<String> = _interest

    private var _terms = MutableLiveData("12")
    val terms: LiveData<String> = _terms

    var yearList = mutableListOf<String>()
    var debtAmountList = mutableListOf<String>()
    var termAmountList = mutableListOf<String>()
    var interestAmountList = mutableListOf<String>()
    var deductionAmountList = mutableListOf<String>()

    fun getLoan(newLoan: String) {
        _loan.value = newLoan
    }

    fun getTerms(newTerms: String) {
        _terms.value = newTerms
    }

    fun getInterest(newInterest: String) {
        _interest.value = newInterest
    }

    fun getHelperVariable(): Double {
        val k = 1.0 / (1 + (_interest.value!!.toDouble() / 100))
        return (k.pow(_terms.value!!.toDouble()) - 1.0) / (k - 1.0)
    }

    fun getAnnuityLists() {
        var termAmount = ((_loan.value!!.toDouble()
            .div(getHelperVariable())) * (1.0 + (_interest.value!!.toDouble() / 100.0)))
        var remainingLoan = _loan.value!!.toDouble()
        var interestAmount =
            remainingLoan * (1.0 + (_interest.value!!.toDouble() / 100.0)) - remainingLoan
        var deduction = termAmount - interestAmount
        for (i in 1.._terms.value!!.toInt()) {
            yearList.add(i.toString())
            termAmountList.add(termAmount.roundToInt().toString())
            interestAmountList.add(interestAmount.roundToInt().toString())
            deductionAmountList.add(deduction.roundToInt().toString())
            debtAmountList.add((remainingLoan - deduction).roundToInt().toString())
            remainingLoan -= deduction
            interestAmount =
                remainingLoan * (1.0 + (_interest.value!!.toDouble() / 100.0)) - remainingLoan
            deduction = termAmount - interestAmount
        }
    }

    fun getSerialLists() {
        var remainingLoan = _loan.value!!.toDouble()
        var deduction = remainingLoan / _terms.value!!.toDouble()
        var interestAmount = remainingLoan * (_interest.value!!.toDouble() / 100)
        var termAmount = deduction + interestAmount
        for (i in 1.._terms.value!!.toInt()) {
            interestAmountList.add(interestAmount.roundToInt().toString())
            deductionAmountList.add(deduction.roundToInt().toString())
            debtAmountList.add((remainingLoan - deduction).roundToInt().toString())
            yearList.add(i.toString())
            termAmount = deduction + interestAmount
            termAmountList.add(termAmount.roundToInt().toString())
            remainingLoan -= deduction
            interestAmount = remainingLoan * ((_interest.value!!.toDouble() / 100.0))
        }
    }

    fun clearAll() {
        yearList.clear()
        debtAmountList.clear()
        termAmountList.clear()
        interestAmountList.clear()
        deductionAmountList.clear()
    }
}