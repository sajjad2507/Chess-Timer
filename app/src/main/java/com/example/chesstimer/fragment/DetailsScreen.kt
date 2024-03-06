package com.example.chesstimer.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.chesstimer.R
import com.example.chesstimer.databinding.FragmentDetailsScreenBinding

class DetailsScreen : Fragment() {

    lateinit var binding: FragmentDetailsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsScreenBinding.inflate(layoutInflater, container, false)

        val noPlayer = arguments?.getString("noPlayer", "")
        val initialTime = arguments?.getString("initialTime", "")
        val increamentTime = arguments?.getString("incrementTime", "")

        binding.nextBtn.setOnClickListener {


            val bundle = Bundle()
            bundle.putString("noPlayer", noPlayer)
            bundle.putString("initialTime", initialTime)
            bundle.putString("incrementTime", increamentTime)

            NavHostFragment.findNavController(this).navigate(R.id.action_detailsScreen_to_timerScreen, bundle)

        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Lock the screen orientation to landscape when the fragment is resumed
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}