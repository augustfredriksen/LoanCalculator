package com.example.oblig2nedbetaling

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.oblig2nedbetaling.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private var _binding: FragmentTitleBinding? = null
    private val binding get() = _binding!!
    var checked1 = true

    private val sharedViewModel: SharedViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTitleBinding.inflate(inflater, container, false)

        sharedViewModel.loan.observe(viewLifecycleOwner) { loan ->
            binding.loan.setText(loan)
        }
        sharedViewModel.interest.observe(viewLifecycleOwner) { interest ->
            binding.interest.setText(interest)
        }

        sharedViewModel.terms.observe(viewLifecycleOwner) { terms ->
            binding.terms.setText(terms)
        }
        onRadioButtonClicked()


        binding.loanSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var step = progress * 10000
                binding.loan.setText(step.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.interestSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var step = progress.toDouble() / 10.0
                binding.interest.setText(step.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.termsSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var step = progress / 5
                binding.terms.setText(step.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })




        binding.settingsButton.setOnClickListener{
            findNavController().navigate(R.id.action_titleFragment_to_settingsFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.calcBtn.setOnClickListener {
            sharedViewModel.clearAll()
            sharedViewModel.getInterest(binding.interest.text.toString())
            sharedViewModel.getLoan(binding.loan.text.toString())
            sharedViewModel.getTerms(binding.terms.text.toString())
            if (checked1) {
                sharedViewModel.getAnnuityLists()
            }
            else {
                sharedViewModel.getSerialLists()
            }

            /*sharedViewModel.getYearList()
            sharedViewModel.getTermAmountList()
            sharedViewModel.getInterestAmountList()
            sharedViewModel.getDebtAmountList()
            sharedViewModel.getDeductionAmountList()*/

            findNavController().navigate(R.id.action_titleFragment_to_resultFragment)
        }
    }

    fun onRadioButtonClicked() {
        binding.annuityBtn.setOnClickListener {
            checked1 = true
            Log.d("ViewModel", getString(R.string.Annuity_loan_checked))

        }
        binding.serialBtn.setOnClickListener {
            checked1 = false
            Log.d("ViewModel", getString(R.string.Serial_loan_checked))
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}