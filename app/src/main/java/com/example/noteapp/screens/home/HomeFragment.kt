package com.example.noteapp.screens.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.adapters.CustomAdapter
import com.example.noteapp.adapters.WordAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.room.AppDatabase
import com.example.noteapp.room.WordEntity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


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
                binding.titleAndDescriptionLy.visibility = View.VISIBLE
                binding.noWordsTxt.visibility = View.GONE
                binding.wordTitle.text = wordList[0].title
                binding.wordDescription.text = wordList[0].description
            } else {
                binding.titleAndDescriptionLy.visibility = View.GONE
                binding.noWordsTxt.visibility = View.VISIBLE            }
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

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedCourse: WordEntity =
                    wordList.get(viewHolder.adapterPosition)

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                wordList.removeAt(viewHolder.adapterPosition)
                appDatabase.wordDao().delete(deletedCourse)

                // below line is to notify our item is removed from adapter.
                customAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                Snackbar.make(binding.wordListRecycler, "Deleted " + deletedCourse.title, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            wordList.add(position, deletedCourse)
                            appDatabase.wordDao().insertUser(deletedCourse)
                            // below line is to notify item is
                            // added to our adapter class.
                            customAdapter.notifyItemInserted(position)
                        }).show()
            }

/*            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(Color.RED)
                    .addSwipeRightActionIcon(R.drawable.baseline_delete_forever_24)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }*/


            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(binding.wordListRecycler)


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
            if (wordList.isNotEmpty()){
                binding.titleAndDescriptionLy.visibility = View.VISIBLE
                binding.noWordsTxt.visibility = View.GONE
                binding.wordTitle.text = wordList[0].title
                binding.wordDescription.text = wordList[0].description
            } else {
                binding.titleAndDescriptionLy.visibility = View.GONE
                binding.noWordsTxt.visibility = View.VISIBLE
            }
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