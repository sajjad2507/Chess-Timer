package com.example.chesstimer.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.chesstimer.R
import com.example.chesstimer.databinding.FragmentSplashScreenBinding

class SplashScreen : Fragment() {

    lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed({

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_splashScreen_to_homeScreen)


        }, 3000)

        return binding.root
    }
}