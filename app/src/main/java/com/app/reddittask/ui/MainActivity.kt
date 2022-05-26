package com.app.reddittask.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.reddittask.adapters.CharactersAdapter
import com.app.reddittask.databinding.ActivityMainBinding
import com.app.reddittask.utils.gone
import com.app.reddittask.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.reddittask.utils.showSnack

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private lateinit var charactersAdapter: CharactersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initObservations()
    }

    private fun initListener() {

        charactersAdapter = CharactersAdapter()
        binding.recyclerCharacters.layoutManager = LinearLayoutManager(this)
        binding.recyclerCharacters.adapter = charactersAdapter

    }

    private fun initObservations() {

        viewModel.uiStateLiveData.observe(this) { state ->
            when (state) {
                is LoadingState -> {
                    binding.recyclerCharacters.gone()
                    binding.progressCharacters.visible()
                }

                is EmptyState -> {
                    binding.recyclerCharacters.visible()
                    binding.progressCharacters.gone()
                    showRetryDialog()
                }

                is ContentState -> {
                    binding.recyclerCharacters.visible()
                    binding.progressCharacters.gone()
                }

                is ErrorState -> {
                    binding.progressCharacters.gone()
                    binding.root.showSnack(state.message)
                    showRetryDialog()
                }
            }
        }

        viewModel.charactersResponseLiveData.observe(this) { response ->
            charactersAdapter.differ.submitList(response.characters)
        }
    }

    private fun showRetryDialog() {
        val alert: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(this)
        alert.setMessage("Do you Want to Retry Again ??")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, _ ->
                viewModel.retry()
            }).setNegativeButton("No", null)
        val dialog: android.app.AlertDialog? = alert.create()
        dialog!!.show()
    }

}