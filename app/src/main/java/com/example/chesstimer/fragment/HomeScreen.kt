package com.example.chesstimer.fragment

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.chesstimer.R
import com.example.chesstimer.databinding.FragmentHomeScreenBinding


class HomeScreen : Fragment() {

    lateinit var binding: FragmentHomeScreenBinding
    private var backPressedTime: Long = 0
    private val doubleBackToExitPressedMessage = "Press back again to exit"
    var spinner = ""
    var increment_ch = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater, container, false)

        // Data for the spinner
        val numbers = listOf("2", "3", "4", "5")

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, numbers)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        binding.edtNumberOfPlayers.adapter = adapter

        // Set a listener to handle item selections
        binding.edtNumberOfPlayers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                spinner = numbers[position]

                try {

                    (selectedItemView!! as TextView)!!.setTextColor(Color.parseColor("#ffffff"))

                } catch (e: Exception) {
                    Log.e("@@", "test fails")
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing here
            }
        }

        // Data for the spinner
        val increment = listOf("Fischer", "Bronstein")

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapterIncrement = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, increment)

        // Specify the layout to use when the list of choices appears
        adapterIncrement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        binding.edtIncreamentTime.adapter = adapterIncrement

        // Set a listener to handle item selections
        binding.edtIncreamentTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                increment_ch = increment[position]

                try {

                    (selectedItemView!! as TextView)!!.setTextColor(Color.parseColor("#ffffff"))

                } catch (e: Exception) {
                    Log.e("@@", "test fails")
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing here
            }
        }

        binding.startGameBtn.setOnClickListener {

            val noPlayer = spinner
            val initialTime = binding.edtInitialTime.text.toString()
            val increamentTime = increment_ch

            if (noPlayer.toInt() >= 2 && noPlayer.toInt() <= 5 && increamentTime.isNotEmpty() && increamentTime.isNotEmpty()) {

                val bundle = Bundle()
                bundle.putString("noPlayer", noPlayer)
                bundle.putString("initialTime", initialTime)
                bundle.putString("incrementTime", increamentTime)

                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_homeScreen_to_detailsScreen, bundle)

            } else if (noPlayer.isNotEmpty() || increamentTime.isNotEmpty() || increamentTime.isNotEmpty()) {

                Toast.makeText(requireContext(), "Fields can't be empty!", Toast.LENGTH_SHORT).show()
            }

        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressedTime + 2000 > System.currentTimeMillis()) {
                        requireActivity().finish()
                        return
                    } else {
                        Toast.makeText(
                            requireContext(),
                            doubleBackToExitPressedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    backPressedTime = System.currentTimeMillis()
                }
            })

        return binding.root
    }
    override fun onStart() {
        super.onStart()
        // Lock the screen orientation to landscape when the fragment is resumed
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}