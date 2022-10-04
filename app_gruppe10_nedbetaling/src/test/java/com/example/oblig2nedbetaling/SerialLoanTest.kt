package com.example.oblig2nedbetaling

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SerialLoanTest {


    private lateinit var viewModel: SharedViewModel

    @get:Rule var rule: TestRule = InstantTaskExecutorRule(
    )


    @Before
    fun setup(){
        viewModel = SharedViewModel()

    }

    @Test
    fun `test serial loan interest amount is correct`(){


        viewModel.run {
            clearAll()
            getLoan("100000")
            getTerms("12")
            getInterest("3.6")
            getHelperVariable()
            getSerialLists()
        }

        val expected = 3300
        val actual = viewModel.interestAmountList[1].toInt()
        assertEquals(
            expected,
            actual
        )
        val expected2 = 900
        val actual2 = viewModel.interestAmountList[9].toInt()
        assertEquals(
            expected2,
            actual2
        )

    }
    @Test
    fun `test serial loan term amount is correct`(){
        viewModel.run {
            clearAll()
            getLoan("100000")
            getTerms("12")
            getInterest("3.6")
            getHelperVariable()
            getSerialLists()
        }

        val expected = 11633
        val actual = viewModel.termAmountList[1].toInt()
        assertEquals(
            expected,
            actual
        )
        val expected2 = 9233
        val actual2 = viewModel.termAmountList[9].toInt()
        assertEquals(
            expected2,
            actual2
        )
    }

    @Test
    fun `test annuity loan term amount is correct`(){
        viewModel.run {
            clearAll()
            getLoan("100000")
            getTerms("12")
            getInterest("7")
            getHelperVariable()
            getAnnuityLists()
        }

        val expected = 12590
        val actual = viewModel.termAmountList[1].toInt()
        assertEquals(
            expected,
            actual
        )
        val expected2 = 12590
        val actual2 = viewModel.termAmountList[9].toInt()
        assertEquals(
            expected2,
            actual2
        )
    }

    @Test
    fun `test annuity loan rent amount is correct`(){
        viewModel.run {
            clearAll()
            getLoan("100000")
            getTerms("12")
            getInterest("7")
            getHelperVariable()
            getAnnuityLists()
        }

        val expected = 6609
        val actual = viewModel.interestAmountList[1].toInt()
        assertEquals(
            expected,
            actual
        )
        val expected2 = 2313
        val actual2 = viewModel.interestAmountList[9].toInt()
        assertEquals(
            expected2,
            actual2
        )
    }

    @Test
    fun `test clearall works`(){
        viewModel.run {
            clearAll()
            getLoan("100000")
            getTerms("12")
            getInterest("7")
            getHelperVariable()
            getAnnuityLists()
        }
        viewModel.clearAll()

        val actual = viewModel.interestAmountList

        assert(actual.isEmpty()
        )
    }
}