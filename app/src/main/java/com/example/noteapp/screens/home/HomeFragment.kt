package com.example.noteapp.screens.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapters.CustomAdapter
import com.example.noteapp.adapters.WordAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.room.AppDatabase
import com.example.noteapp.room.WordEntity
import com.example.noteapp.state.Word
import com.google.firebase.database.DatabaseReference
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding:FragmentHomeBinding

    private lateinit var wordList: ArrayList<WordEntity>
    private lateinit var wordAdapter: WordAdapter
    private lateinit var customAdapter: CustomAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var searchView: SearchView

    private lateinit var appDatabase: AppDatabase


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appDatabase = AppDatabase.getInstance(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return  binding.root
    }

    override fun onResume() {
        super.onResume()
//        wordList = ArrayList(appDatabase.wordDao().getAll())
//        customAdapter.notifyDataSetChanged()
        refresher()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordList = ArrayList(appDatabase.wordDao().getAll())
        wordList.reverse()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.view?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        binding.searchView.queryHint = "No data found"
//        binding.searchView.isIconified = false
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
//        wordList = ArrayList(appDatabase.wordDao().getAll())

        binding.apply {
            mainCard.elevation = 10f
            if (wordList.isNotEmpty()){
                wordTitle.text = wordList[0].title
                wordDescription.text = wordList[0].description
            }
            viewModel.setValue(wordList)
            wordListAdapter()
            addButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addWordFragment)
//                findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<WordEntity>("key")?.observe(viewLifecycleOwner) {result -> // Do something with the result.
//                    wordList.add(result)
//                  }
                }


//            binding.saveBtn.setOnClickListener {
//                val user = UserEntity(
//                    firstName = txtName.text.toString(),
//                    lastName = txtSurname.text.toString()
//                )
//                val note = ShouldNot(txtName.text.toString(),txtSurname.text.toString())
//                appDatabase.userDao().insertUser(user)
//                userList.add(user)
//                adapter.notifyItemInserted(userList.size)
//
//                databaseReference = FirebaseDatabase.getInstance().getReference("Note/ShouldNot")
//                databaseReference.child(txtName.text.toString()).setValue(note).addOnSuccessListener {
//                    txtName.text.clear()
//                    txtSurname.text.clear()
//                    Toast.makeText(this@MainActivity,"Success", Toast.LENGTH_SHORT).show()
//                }.addOnFailureListener{
//                    Toast.makeText(this@MainActivity,it.message, Toast.LENGTH_SHORT).show()
//                }
//
//            }

        }


    }

    private fun filterList(newText: String?) {
        val filterList = ArrayList<WordEntity>()
        for (i in wordList) {
            if (newText != null) {
                if (i.title.lowercase().startsWith(newText.lowercase())){
                    filterList.add(i)
                }
            }
        }
        if (filterList.isEmpty()){
                Toast.makeText(requireContext(),"No words found",Toast.LENGTH_SHORT).show()
        } else {
            customAdapter.setFilterList(filterList)
        }

    }

    private fun refresher(){
        binding.refresher.setOnRefreshListener {
            getWordList()
            binding.wordTitle.text = wordList[0].title
            binding.wordDescription.text = wordList[0].description
            binding.refresher.isRefreshing = false
        }
    }

    private fun getWordList(){
        wordList = ArrayList(appDatabase.wordDao().getAll())
        wordList.reverse()
        viewModel.setValue(wordList)
        viewModel.getValue().observe(viewLifecycleOwner, Observer {
            wordList = it
            wordListAdapter()
            Toast.makeText(requireContext(),"Refreshed",Toast.LENGTH_SHORT).show()
        })

    }

    private fun wordListAdapter(){
        binding.wordListRecycler.layoutManager = LinearLayoutManager(requireContext())
        customAdapter = CustomAdapter(wordList)
        binding.wordListRecycler.adapter = customAdapter
        customAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("WordEntity",it)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bundle)
        }
    }
}