package com.example.notetakerapp.ui.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakerapp.R
import com.example.notetakerapp.data.model.Note
import com.example.notetakerapp.data.remote.Resource
import com.example.notetakerapp.ui.adapter.NotesAdapter
import com.example.notetakerapp.ui.viewmodel.NoteVMFactory
import com.example.notetakerapp.ui.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var viewModel: NoteViewModel
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        setupViewModel()
        setupRecyclerView(recyclerView)
        observeData()
    }

    private fun setupViewModel() {
        val _application = requireNotNull(application)
        viewModel = ViewModelProvider.AndroidViewModelFactory(_application).create(NoteViewModel::class.java)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        notesAdapter = NotesAdapter()
        recyclerView.adapter = notesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeData() {
        // Observe local database notes (always up-to-date)
        viewModel.localNotes.observe(this) { localNotes ->
            notesAdapter.submitList(localNotes)
        }

        // Observe network state
        viewModel.networkNotes.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show loading indicator
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    // Hide loading indicator
                    progressBar.visibility = View.GONE

                    resource.data?.let { networkNotes ->
                        notesAdapter.submitList(networkNotes)
                    }
                }
                is Resource.Error -> {
                    // Hide loading indicator
                    progressBar.visibility = View.GONE

                    // Show error message
                    AlertDialog.Builder(this).setMessage(resource.message).setPositiveButton(R.string.ok
                    ) { dialogInterface, i -> dialogInterface.dismiss() }.show()
                }
            }
        }
    }



}