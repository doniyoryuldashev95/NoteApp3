package com.example.noteapp.screens.detail

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapp.databinding.FragmentInDetailBinding
import com.example.noteapp.room.WordEntity

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentInDetailBinding
    private lateinit var data : WordEntity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        data = arguments?.getSerializable("WordEntity") as WordEntity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.view?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        binding.apply {
            backBtn.setOnClickListener {
                requireActivity().onBackPressed()
            }
            wordTitle.text = data.title
            wordDescription.text = data.description

        }
    }
}