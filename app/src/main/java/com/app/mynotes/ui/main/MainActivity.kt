package com.app.mynotes.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mynotes.R
import com.app.mynotes.databinding.ActivityMainBinding
import com.app.mynotes.helper.ViewModelFactory
import com.app.mynotes.ui.insert.NoteAddUpdateActivity

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val _binding get() = binding as ActivityMainBinding

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        adapter = NoteAdapter()

        _binding.rvNotes.layoutManager = LinearLayoutManager(this@MainActivity)
        _binding.rvNotes.setHasFixedSize(true)
        _binding.rvNotes.adapter = adapter

        val mainViewModel = obtainViewModel(this)
        mainViewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        _binding.fabAdd.setOnClickListener{ view ->
            if (view.id == R.id.fab_add){
                val intent = Intent(this, NoteAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstace(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding.root
    }
}