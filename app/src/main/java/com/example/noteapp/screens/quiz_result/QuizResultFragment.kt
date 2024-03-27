package com.example.noteapp.screens.quiz_result

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.adapters.QuizAdapter
import com.example.noteapp.databinding.FragmentQuizResultBinding
import com.example.noteapp.room.AppDatabase
import com.example.noteapp.room.WordEntity

class QuizResultFragment : Fragment() {

    private lateinit var viewModel: QuizResultViewModel
    private lateinit var binding: FragmentQuizResultBinding
    private lateinit var appDatabase: AppDatabase
    private var correctAnswers: Int? = null
    private var wrongAnswers: Int? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appDatabase = AppDatabase.getInstance(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        correctAnswers = arguments?.getInt("correctAnswers")
        wrongAnswers = arguments?.getInt("wrongAnswers")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textViewCorrectAnswers.text = "Total Correct Answers: ${correctAnswers.toString()}"
            textViewFalseAnswers.text = "Total False Answers: ${wrongAnswers.toString()}"
            buttonRestartQuiz.setOnClickListener {
//                findNavController().popBackStack(R.id.quizFragment, false)
                requireActivity().onBackPressed()
//                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//                fragmentTransaction.replace(R.id.nav_host_fragment, QuizResultFragment()) // Replace with a new instance of the fragment
//                fragmentTransaction.addToBackStack(null) // Add the transaction to the back stack
//                fragmentTransaction.commit()
            }
            buttonReturnToMenu.setOnClickListener {
                findNavController().popBackStack(R.id.homeFragment, false)

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}