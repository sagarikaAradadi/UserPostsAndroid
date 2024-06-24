package com.samplerecyclerview.ui.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.samplerecyclerview.R
import com.samplerecyclerview.databinding.ActivityMainBinding
import com.samplerecyclerview.ui.ui.model.UsersModel
import com.samplerecyclerview.ui.ui.viewModel.UsersViewModel
import com.samplerecyclerview.ui.utils.Resource
import com.samplerecyclerview.ui.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var usersAdapter: UsersAdapter
    private val usersViewModel: UsersViewModel by viewModels()
    private var usersList:ArrayList<UsersModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        initUI()
    }

    private fun initUI() {
        //set toolbar
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = resources.getString(R.string.new_users)
        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle the Up button click
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed() // or finish() to close the activity
        }

        showLoader()
        //Call postAPI
        usersViewModel.fetchPosts()

        //Observe APis
        observeViewModel()



    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            observe(usersViewModel.userResponse, ::handlePostResponse)
        }
    }

    private fun showLoader() {
        binding.progressBar.visibility = View.VISIBLE


    }

    private fun hideLoader() {
        try {
            binding.progressBar.visibility = View.GONE
        } catch (_: Exception) {
        }
    }


    private fun handlePostResponse(resource: Resource<ArrayList<UsersModel>?>?) {
        hideLoader()
        Log.e("TAG", "handlePostResponse: "+resource?.data.orEmpty().size )
        when (resource) {
            is Resource.Success -> {
                usersList = resource.data as ArrayList<UsersModel>
                //InitAdapter
                binding.postRv.layoutManager=LinearLayoutManager(this)
                usersAdapter = UsersAdapter(this,usersList)
                binding.postRv.adapter=usersAdapter
                usersAdapter.notifyDataSetChanged()
            }

            is Resource.DataError -> {


            }

            is Resource.Loading -> {


            }

            else -> {}
        }
    }
}