package com.example.noteapp.screens.quiz

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
import com.example.noteapp.databinding.FragmentQuizBinding
import com.example.noteapp.room.AppDatabase
import com.example.noteapp.room.WordEntity
import com.example.noteapp.screens.quiz_result.QuizResultFragment
import com.example.noteapp.screens.quiz_result.QuizResultViewModel

class QuizFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: FragmentQuizBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var wordList: ArrayList<WordEntity>
    private var listIndex = 0
    private var countDownTimer: CountDownTimer? = null
    private var theTime: CountDownTimer? = null
    private var isPressed: Boolean = false
    private var correctAnswers:Int = 0
    private var wrongAnswers:Int = 0


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appDatabase = AppDatabase.getInstance(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordList = ArrayList(appDatabase.wordDao().getAll())
        wordList.shuffle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        answerLayoutVisible()
        binding.apply {

            backBtn.setOnClickListener {
                requireActivity().onBackPressed()
            }
            drawerMethod()
        }
    }

    private fun answerLayoutInvisible() {
        binding.emptyText.visibility = View.VISIBLE
        binding.boxLayout.visibility = View.GONE
    }
    private fun answerLayoutVisible() {
        binding.emptyText.visibility = View.GONE
        binding.boxLayout.visibility = View.VISIBLE
    }

    private fun drawerMethod() {

        if (wordList.isEmpty() || wordList.size <= listIndex) {
            binding.emptyText.text = "Please add at least 4 words to try the quiz!"
            answerLayoutInvisible()
        }

        if (wordList.isNotEmpty() && wordList.size==listIndex){
            val bundle = Bundle()
            bundle.putInt("correctAnswers",correctAnswers)
            bundle.putInt("wrongAnswers",wrongAnswers)
            findNavController().navigate(R.id.action_quizFragment_to_quizResultFragment,bundle)
            listIndex = 0
            return
        }

        val buildList = arrayListOf<WordEntity>().apply {
            val shuffleList = wordList.shuffled()
            addAll(shuffleList.filter { it != wordList[listIndex] }.take(3))
            add(wordList[listIndex])
            shuffle()
        }

        binding.question.text = wordList[listIndex].title
        binding.firstAnswer.text = buildList[0].description
        binding.secondAnswer.text = buildList[1].description
        binding.thirdAnswer.text = buildList[2].description
        binding.fourthAnswer.text = buildList[3].description
        setupAnswerButtons(wordList[listIndex])

        countDownTimer = object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.timer.setTextColor(Color.BLACK)
                binding.timer.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                listIndex++
                drawerMethod()
                isPressed = false
            }
        }.start()
    }

    private fun answerClicks(wordEntity: WordEntity, view: View) {
        if (!isPressed) {
            val shake: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            val answerButton = view as TextView

            if (answerButton.text == wordEntity.description) {
                correctAnswers+=1
                answerButton.setBackgroundResource(R.drawable.bg_green_true_12)
                binding.timer.text = "True"
                binding.timer.setTextColor(Color.parseColor("#36B73B"))
            } else {
                wrongAnswers+=1
                answerButton.setBackgroundResource(R.drawable.bg_red_false_12)
                binding.timer.text = "False"
                binding.timer.setTextColor(Color.parseColor("#F5392B"))
                binding.timer.startAnimation(shake)
            }

            timer(answerButton)
            isPressed = true
        }
    }

    private fun setClickListener(answerButton: TextView, wordEntity: WordEntity) {
        answerButton.setOnClickListener {
            answerClicks(wordEntity, answerButton)
        }
    }

    private fun setupAnswerButtons(wordEntity: WordEntity) {
        val answerButtons = listOf(
            binding.firstAnswer,
            binding.secondAnswer,
            binding.thirdAnswer,
            binding.fourthAnswer
        )

        for (button in answerButtons) {
            setClickListener(button, wordEntity)
        }
    }

    private fun timer(textView: TextView) {
        countDownTimer?.cancel()
        theTime = object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                listIndex++
                textView.setBackgroundResource(R.drawable.bg_transparent_outlined_12)
                drawerMethod()
                theTime?.cancel()
                isPressed = false
            }
        }
        theTime?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        theTime?.cancel()
        countDownTimer?.cancel()
    }
}