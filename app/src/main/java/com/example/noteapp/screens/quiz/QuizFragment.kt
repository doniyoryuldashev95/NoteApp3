package com.example.noteapp.screens.quiz

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapters.CustomAdapter
import com.example.noteapp.adapters.QuizAdapter
import com.example.noteapp.databinding.FragmentQuizBinding
import com.example.noteapp.room.AppDatabase
import com.example.noteapp.room.WordEntity

class QuizFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: FragmentQuizBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var quizAdapter: QuizAdapter
    private lateinit var wordList: ArrayList<WordEntity>
    private var listIndex = 0
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var theTime:CountDownTimer


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appDatabase = AppDatabase.getInstance(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordList = ArrayList(appDatabase.wordDao().getAll())
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
        binding.apply {
            backBtn.setOnClickListener {
                requireActivity().onBackPressed()
            }
            drawerMethod()


        }
    }

    private fun drawerMethod() {
        var buildList = arrayListOf<WordEntity>()
        val shuffleList = arrayListOf<WordEntity>()
        shuffleList.addAll(wordList)
        shuffleList.shuffle()
        if (wordList.isNotEmpty() && wordList.size > 4 && wordList.size > listIndex) {
            answerLayoutVisible()
            wordList
            Log.d("TAG", "drawerMethod: ${wordList[listIndex].title} $listIndex")
            binding.question.text = wordList[listIndex].title
            for (secondInnerList in shuffleList) {
                if (secondInnerList != wordList[listIndex] && buildList.size < 3) {
                    buildList.add(secondInnerList)
                }
            }
            buildList.add(wordList[listIndex])
            buildList.shuffle()

            binding.firstAnswer.text = buildList[0].description
            binding.secondAnswer.text = buildList[1].description
            binding.thirdAnswer.text = buildList[2].description
            binding.fourthAnswer.text = buildList[3].description
            answerClicks(wordList[listIndex])

            buildList = arrayListOf()
            countDownTimer = object : CountDownTimer(10000, 1000) {

                // Callback function, fired on regular interval
                override fun onTick(millisUntilFinished: Long) {
                    binding.timer.text = (millisUntilFinished / 1000).toString()

                }

                // Callback function, fired
                // when the time is up
                override fun onFinish() {
                    listIndex++
                    buildList = arrayListOf()
                    drawerMethod()
//                    countDownTimer.cancel()
                }
            }.start()

        } else if (wordList.size == listIndex) {
            binding.emptyText.text = "Congratulation! You just have finished the quiz."
//            Log.d("TAG", "drawerMethod: this s")
            answerLayoutInvisible()
        } else {
//            Log.d("TAG", "drawerMethod: Worked Already ${wordList.size} " + (listIndex))
            binding.emptyText.text = "Please add 4 or more words to try quiz!"
            answerLayoutInvisible()
        }
    }


    private fun answerLayoutVisible() {
        binding.boxLayout.visibility = View.VISIBLE
        binding.emptyText.visibility = View.GONE

    }

    private fun answerLayoutInvisible() {
        binding.boxLayout.visibility = View.GONE
        binding.emptyText.visibility = View.VISIBLE
    }

    private fun answerClicks(wordEntity: WordEntity) {
        binding.firstAnswer.setOnClickListener {
            if (binding.firstAnswer.text.equals(wordEntity.description)) {
                binding.firstAnswer.setBackgroundResource(R.drawable.bg_green_true_12)
            } else {
                binding.firstAnswer.setBackgroundResource(R.drawable.bg_red_false_12)
            }
            timer(binding.firstAnswer)
        }
        binding.secondAnswer.setOnClickListener {
            if (binding.secondAnswer.text.equals(wordEntity.description)) {
                binding.secondAnswer.setBackgroundResource(R.drawable.bg_green_true_12)
            } else {
                binding.secondAnswer.setBackgroundResource(R.drawable.bg_red_false_12)
            }
            timer(binding.secondAnswer)
        }
        binding.thirdAnswer.setOnClickListener {
            if (binding.thirdAnswer.text.equals(wordEntity.description)) {
                binding.thirdAnswer.setBackgroundResource(R.drawable.bg_green_true_12)
            } else {
                binding.thirdAnswer.setBackgroundResource(R.drawable.bg_red_false_12)
            }
            timer(binding.thirdAnswer)
        }
        binding.fourthAnswer.setOnClickListener {
            if (binding.fourthAnswer.text.equals(wordEntity.description)) {
                binding.fourthAnswer.setBackgroundResource(R.drawable.bg_green_true_12)
            } else {
                binding.fourthAnswer.setBackgroundResource(R.drawable.bg_red_false_12)
            }
            timer(binding.fourthAnswer)
        }
    }

    private fun timer(textView: TextView){
        countDownTimer.cancel()
        binding.timer.text = ""
        theTime = object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                listIndex++
                textView.setBackgroundResource(R.drawable.bg_transparent_outlined_12)
                drawerMethod()
                theTime.cancel()
            }
        }
        theTime.start()
    }
}