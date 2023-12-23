package com.example.noteapp.screens.note

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.room.AppDatabase
import com.example.noteapp.screens.add_word_screen.AddWordViewModel

class NoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var binding: FragmentNoteBinding
    private lateinit var appDatabase: AppDatabase
//    private val callBack = object : OnBackPressedCallback(true){
//        override fun handleOnBackPressed() {
//            findNavController().navigate(R.id.action_noteFragment_to_homeFragment)
//        }
//
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appDatabase = AppDatabase.getInstance(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            quizWordsLayout.setOnClickListener {
                findNavController().navigate(R.id.action_noteFragment_to_quizFragment)
            }
        }

//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callBack)
    }




}