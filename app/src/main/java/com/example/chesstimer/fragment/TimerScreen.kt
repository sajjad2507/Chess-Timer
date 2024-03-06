package com.example.chesstimer.fragment

import android.content.Context.VIBRATOR_SERVICE
import android.content.pm.ActivityInfo
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.chesstimer.R
import com.example.chesstimer.databinding.FragmentTimerScreenBinding

class TimerScreen : Fragment() {

    lateinit var binding: FragmentTimerScreenBinding
    var ch = 1
    var countdownTimers: MutableList<CountDownTimer> = mutableListOf()
    var flow = 1
    private var isPaused = false
    var timer = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerScreenBinding.inflate(layoutInflater, container, false)

        val noPlayer = arguments?.getString("noPlayer", "")?.toInt()
        val initialTime = arguments?.getString("initialTime", "")?.toInt()

        setVisibility(noPlayer, initialTime)

        binding.player1Layout.setOnClickListener {

            if (flow > noPlayer!!) {
                flow = 1
            }

            if (flow == 1) {
                if (ch == 1) {
                    // Start the timer for the next player
                    startNextPlayerTimer(binding.player1Timer, binding.player1Timer)
                    ch = 2
                } else {
                    addValue(binding.player1Timer)
                    startNextPlayerTimer(binding.player2Timer, binding.player2Timer)
                    flow++
                }
            }
        }

        binding.player2Layout.setOnClickListener {
            if (flow == 2) {
                if (noPlayer == 2) {
                    // Start the timer for the next player
                    addValue(binding.player2Timer)
                    startNextPlayerTimer(binding.player1Timer, binding.player1Timer)
                } else {
                    addValue(binding.player2Timer)
                    // Start the timer for the next player
                    startNextPlayerTimer(binding.player3Timer, binding.player3Timer)
                }
                flow++
            }
        }

        binding.player3Layout.setOnClickListener {
            if (flow == 3) {
                if (noPlayer == 3) {
                    addValue(binding.player3Timer)
                    // Start the timer for the next player
                    startNextPlayerTimer(binding.player1Timer, binding.player1Timer)
                } else {
                    addValue(binding.player3Timer)
                    // Start the timer for the next player
                    startNextPlayerTimer(binding.player4Timer, binding.player4Timer)
                }
                flow++
            }
        }

        binding.player4Layout.setOnClickListener {
            if (flow == 4) {
                if (noPlayer == 4) {
                    addValue(binding.player4Timer)
                    // Start the timer for the next player
                    startNextPlayerTimer(binding.player1Timer, binding.player1Timer)
                } else {
                    addValue(binding.player4Timer)
                    // Start the timer for the next player
                    startNextPlayerTimer(binding.player5Timer, binding.player5Timer)
                }
                flow++
            }
        }

        binding.player5Layout.setOnClickListener {
            if (flow == 5) {
                addValue(binding.player5Timer)
                // Start the timer for the next player
                startNextPlayerTimer(binding.player1Timer, binding.player1Timer)
                flow = 1
            }
        }

        binding.HomeBtn.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_timerScreen_to_homeScreen)

        }

        binding.refreshBtn.setOnClickListener {

            stopAllTimers()
            flow = 1
            ch = 1
            setVisibility(noPlayer, initialTime)

        }

        binding.pauseBtn.setOnClickListener {

            // Pause the timer
            isPaused = true

        }

        binding.playBtn.setOnClickListener {

            // Resume the timer only if it's paused
            if (isPaused) {
                // Reset the paused state
                isPaused = false
                flow = 1
            }

        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                    return
                }
            })

        return binding.root
    }

    private fun addValue(view: TextView) {

        val incrementTime = arguments?.getString("incrementTime", "")

        if (incrementTime == "Fischer") {

            val value = view.text.toString()
            var newTime = 5

            if (!value.equals("Finished!")) {

                var valueInt = value.toInt() + newTime

                view.setText(valueInt.toString())

            }

        } else if (incrementTime == "Bronstein") {

            if (timer <= 5) {

                val value = view.text.toString()

                if (!value.equals("Finished!")) {

                    var valueInt = value.toInt() + timer

                    view.setText(valueInt.toString())

                    timer = 0
                }

            } else {

                val value = view.text.toString()

                if (!value.equals("Finished!")) {

                    var valueInt = value.toInt() + 5

                    view.setText(valueInt.toString())

                    timer = 0
                }

            }

        }

    }

    private fun setVisibility(noPlayer: Int?, initialTime: Int?) {

        if (noPlayer == 2) {
            binding.player1Layout.visibility = View.VISIBLE
            binding.player2Layout.visibility = View.VISIBLE
        } else if (noPlayer == 3) {
            binding.player1Layout.visibility = View.VISIBLE
            binding.player2Layout.visibility = View.VISIBLE
            binding.player3Layout.visibility = View.VISIBLE
        } else if (noPlayer == 4) {
            binding.player1Layout.visibility = View.VISIBLE
            binding.player2Layout.visibility = View.VISIBLE
            binding.player3Layout.visibility = View.VISIBLE
            binding.player4Layout.visibility = View.VISIBLE
        } else if (noPlayer == 5) {
            binding.player1Layout.visibility = View.VISIBLE
            binding.player2Layout.visibility = View.VISIBLE
            binding.player3Layout.visibility = View.VISIBLE
            binding.player4Layout.visibility = View.VISIBLE
            binding.player5Layout.visibility = View.VISIBLE
        } else {
            Toast.makeText(requireContext(), "Error while setting visibility!", Toast.LENGTH_SHORT)
                .show()
        }

        // Set the initial value
        var value = initialTime!!.toInt() * 60

        binding.player1Timer.setText(value.toString())
        binding.player2Timer.setText(value.toString())
        binding.player3Timer.setText(value.toString())
        binding.player4Timer.setText(value.toString())
        binding.player5Timer.setText(value.toString())

    }

    private fun createAndStartTimer(initialTime: Int, view: TextView) {
        // Check if the timer is not paused
        if (!isPaused) {
            val countdownTimer = object : CountDownTimer((initialTime * 1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // Check if the timer is not paused
                    if (!isPaused) {
                        timer++
                        val value = (millisUntilFinished / 1000).toString()
                        view.text = value
                        if (value == "30" || value == "20" || value == "10" || value == "5") {
                            vibrate(300)
                        }
                    }
                }

                override fun onFinish() {
                    // Check if the timer is not paused
                    if (!isPaused) {
                        view.setBackgroundColor(resources.getColor(R.color.red))
                        vibrate(300)
                        view.text = "Finished!"
                    }
                }
            }

            // Add the timer to the list
            countdownTimers.add(countdownTimer)

            // Start the countdown timer
            countdownTimer.start()
        }
    }

    private fun startNextPlayerTimer(currentPlayerTimer: TextView, view: TextView) {
        // Stop all existing timers
        stopAllTimers()

        if (currentPlayerTimer.text.equals("Finished!")) {
            // Increment the flow variable only if the timer has finished
//            flow++
        } else {
            // Start a new timer for the next player if the current player's timer has not finished
            createAndStartTimer(currentPlayerTimer.text.toString().toInt(), currentPlayerTimer)
        }

    }


    private fun stopAllTimers() {
        for (timer in countdownTimers) {
            timer.cancel()
        }
        countdownTimers.clear()
    }

    override fun onResume() {
        super.onResume()
        // Lock the screen orientation to landscape when the fragment is resumed
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun vibrate(duration: Long) {
        val vibrator = requireActivity().getSystemService(VIBRATOR_SERVICE) as Vibrator

        // Check if the device supports vibration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            // Deprecated in API 26 (Oreo), but still applicable for earlier versions
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }
}