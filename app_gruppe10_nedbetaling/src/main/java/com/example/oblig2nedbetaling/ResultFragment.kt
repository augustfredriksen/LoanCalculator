package com.example.oblig2nedbetaling


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.oblig2nedbetaling.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        sharedViewModel.loan.observe(viewLifecycleOwner) { loan ->
            binding.loan.text = loan
        }
        sharedViewModel.interest.observe(viewLifecycleOwner) { interest ->
            binding.interest.text = interest
        }

        sharedViewModel.terms.observe(viewLifecycleOwner) { terms ->
            binding.terms.text = terms
        }

        binding.settingsButton.setOnClickListener{
            findNavController().navigate(R.id.action_resultFragment_to_settingsFragment)
        }

        settings()

        binding.yearList.text = sharedViewModel.yearList.joinToString(separator = "\n\n")
        binding.termAmountList.text = sharedViewModel.termAmountList.joinToString(separator = "\n\n")
        binding.interestAmountList.text = sharedViewModel.interestAmountList.joinToString(separator = "\n\n")
        binding.deductionAmountList.text = sharedViewModel.deductionAmountList.joinToString(separator = "\n\n")
        binding.remainingLoanList.text = sharedViewModel.debtAmountList.joinToString(separator = "\n\n")

        return binding.root
    }

    private fun settings() {
        val sp = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        val yearList = sp?.getBoolean("yearList", true)
        val termList = sp?.getBoolean("termList", true)
        val interestList = sp?.getBoolean("interestList", true)
        val deductionList = sp?.getBoolean("deductionList", true)
        val remainingList = sp?.getBoolean("remainingList", true)

        if (!yearList!!) {
            binding.llYearList.isGone = true
        }
        if (!termList!!) {
            binding.llTermAmountList.isGone = true
        }
        if (!interestList!!) {
            binding.llInterestAmountList.isGone = true
        }
        if (!deductionList!!) {
            binding.llDeductionList.isGone = true
        }
        if (!remainingList!!) {
            binding.llRemainingList.isGone = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}