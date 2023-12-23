package com.example.noteapp

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.noteapp.adapters.WordAdapter
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.room.AppDatabase
import com.example.noteapp.room.WordEntity
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity(){

    private val appDatabase: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var wordList: ArrayList<WordEntity>
    private lateinit var wordAdapter: WordAdapter
    private lateinit var databaseReference:DatabaseReference
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val window = this.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //icon color -> black
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

//            window?.decorView?.systemUiVisibility = 0
        }


        setUpWithNavController()

        binding.apply {


//            userListRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
//            userList = ArrayList(appDatabase.userDao().getAll())
//            val adapter = CustomAdapter(userList)
//            userListRecycler.adapter = adapter
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
//                    Toast.makeText(this@MainActivity,"Success",Toast.LENGTH_SHORT).show()
//                }.addOnFailureListener{
//                    Toast.makeText(this@MainActivity,it.message,Toast.LENGTH_SHORT).show()
//                }
//
//            }

        }




    }

    private fun setUpWithNavController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigation,navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.homeFragment -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.noteFragment -> binding.bottomNavigation.visibility = View.VISIBLE
                else -> binding.bottomNavigation.visibility = View.GONE
            }
        }
    }




//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//
//        when(item.itemId) {
//            R.id.homeFragment -> {
//                return true
//            }
//            R.id.noteFragment -> {
//                return true
//            }
//        }
//        return false
//
//    }
}