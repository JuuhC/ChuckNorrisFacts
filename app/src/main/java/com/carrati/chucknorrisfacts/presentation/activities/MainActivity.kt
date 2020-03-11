package com.carrati.chucknorrisfacts.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.carrati.chucknorrisfacts.R
import com.carrati.chucknorrisfacts.databinding.ActivityMainBinding
import com.carrati.chucknorrisfacts.presentation.adapters.FactsAdapter
import com.carrati.chucknorrisfacts.presentation.extension.visible
import com.carrati.chucknorrisfacts.presentation.viewmodels.MainViewModel
import com.carrati.chucknorrisfacts.presentation.viewmodels.ViewState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val factsAdapter: FactsAdapter by inject()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = factsAdapter
    }

    private fun setupViewModel() {
        viewModel.listFacts()

        viewModel.stateListFacts.observe(this, Observer { state ->
            when(state) {
                is ViewState.Success -> {
                    factsAdapter.facts = state.data
                    factsAdapter.notifyDataSetChanged()
                    setVisibilities(showList = true)
                }
                is ViewState.Loading -> {
                    setVisibilities(showProgressBar = true)
                }
                is ViewState.Failed -> {
                    setVisibilities(showError = true)
                }
            }
        })
    }

    private fun getRandomFact() {
        viewModel.getRandomFact()

        viewModel.stateGetRandonFact.observe(this, Observer { state ->
            when(state) {
                is ViewState.Success -> {
                    factsAdapter.facts = state.data
                    factsAdapter.notifyDataSetChanged()
                    setVisibilities(showList = true)
                }
                is ViewState.Loading -> {
                    setVisibilities(showProgressBar = true)
                }
                is ViewState.Failed -> {
                    setVisibilities(showError = true)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_search) {
            getRandomFact()
        }

        if (id == R.id.action_aboutus) {
            val alertDialog = AlertDialog.Builder(this@MainActivity)
            alertDialog.setTitle("Sobre")
            alertDialog.setMessage("Receba uma piada aleatória sobre Chuck Norris ao clicar na lupa")
            alertDialog.show()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setVisibilities(showProgressBar: Boolean = false, showList: Boolean = false, showError: Boolean = false) {
        binding.progressBar.visible(showProgressBar)
        binding.recyclerView.visible(showList)
        binding.btnTryAgain.visible(showError)
    }
}
