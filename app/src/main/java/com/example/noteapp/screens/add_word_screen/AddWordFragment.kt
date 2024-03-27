package com.example.noteapp.screens.add_word_screen

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noteapp.adapters.ColorAdapter
import com.example.noteapp.databinding.FragmentAddWordBinding
import com.example.noteapp.room.AppDatabase
import com.example.noteapp.room.WordEntity
import com.example.noteapp.state.ColorObject
import java.util.ArrayList

class AddWordFragment : Fragment() {

    private lateinit var viewModel: AddWordViewModel
    private lateinit var binding: FragmentAddWordBinding
//    private lateinit var colorAdapter: ColorAdapter
//    private lateinit var colorList: ArrayList<ColorObject>
//    private var colorObject = ColorObject()
//    private val appDatabase: AppDatabase by lazy {
//        AppDatabase.getInstance(requireContext())
//    }

    private lateinit var appDatabase: AppDatabase


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appDatabase = AppDatabase.getInstance(context)
//        colorList = arrayListOf(
//            ColorObject("#51e2f5","Bright blue"),
//            ColorObject("#9df9ef","Blue Green"),
//            ColorObject("#edf756","Dusty White"),
//            ColorObject("#ffa8B6","Pink Sand"),
//            ColorObject("#a28089","Dark Sand"),
//            ColorObject("#d0bdf4","Medium Purple"),
//            ColorObject("#8458B3","Purple Pain"),
//            ColorObject("#fbe3e8","Pinky"),
//            ColorObject("#5cbdb9","Blue Greeny"),
//            ColorObject("#ebf6f5","Teeny Greeny"),
//            ColorObject("#beef00","Bright Green"),
//            ColorObject("#e1b382","Sand Tan"),
//            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddWordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.view?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.addButton.setOnClickListener {

            binding.apply {
                if (wordTitle.text.isNullOrEmpty()||wordDescription.text.isNullOrEmpty()){
                    Toast.makeText(requireContext(),"Please fill all in",Toast.LENGTH_SHORT).show()
                } else {
                    val word = WordEntity(
                        title = wordTitle.text.toString(),
                        description = wordDescription.text.toString())
                    appDatabase.wordDao().insertUser(word)
                    Toast.makeText(requireContext(),"Successfully added",Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
//                    Toast.makeText(requireContext(),word.title+" "+word.description,Toast.LENGTH_SHORT).show()
                    Log.d("CheckText", "onViewCreated: ${word.title} ${word.description}")

                }
            }

        }
        binding.colorRecycler.layoutManager = GridLayoutManager(requireContext(),6)
//        colorAdapter = ColorAdapter(colorList)
//        binding.colorRecycler.adapter = colorAdapter
//        colorAdapter.setOnItemClickListener {
//            colorObject = it
//        }
//        binding.apply {
//            val note = Word(wordTitle.text.toString(), wordDescription.text.toString())
//
//            userList.add(user)
//            adapter.notifyItemInserted(userList.size)
//
//            databaseReference = FirebaseDatabase.getInstance().getReference("Note/ShouldNot")
//            databaseReference.child(txtName.text.toString()).setValue(note).addOnSuccessListener {
//                txtName.text.clear()
//                txtSurname.text.clear()
//                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
//            }
//        }


    }
}