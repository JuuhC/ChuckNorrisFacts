package com.carrati.chucknorrisfacts.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.carrati.chucknorrisfacts.R
import com.carrati.chucknorrisfacts.databinding.ActivityMainBinding
import com.carrati.chucknorrisfacts.presentation.adapters.FactsAdapter
import com.carrati.chucknorrisfacts.utils.InternetCheck
import com.carrati.chucknorrisfacts.presentation.extension.visible
import com.carrati.chucknorrisfacts.presentation.viewmodels.MainViewModel
import com.carrati.chucknorrisfacts.presentation.extension.ViewState
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

        setupViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        if(adapter == null) {
            layoutManager = LinearLayoutManager(context)
            adapter = factsAdapter
        }
    }

    private fun setupViewModel() {

        viewModel.stateListFacts.observe(this, Observer { state ->
            when(state) {
                is ViewState.Success -> {
                    factsAdapter.facts.clear()
                    factsAdapter.facts.addAll(state.data.toMutableList())
                    factsAdapter.notifyDataSetChanged()
                    setVisibilities(showList = true)
                }
                is ViewState.Failed -> {
                    setVisibilities(showError = true)
                }
            }
        })

        setVisibilities(showProgressBar = true)
        viewModel.listFacts()
    }

    private fun getRandomFact() {

        viewModel.stateGetRandonFact.observe(this, Observer { state ->
            when(state) {
                is ViewState.Success -> {
                    if(!factsAdapter.facts.contains(state.data)) {
                        factsAdapter.facts.add(0, state.data)
                        factsAdapter.notifyItemInserted(0)
                        factsAdapter.notifyDataSetChanged()
                    } else {
                        val index = factsAdapter.facts.indexOf(state.data)
                        factsAdapter.notifyItemMoved(index, 0)
                        Toast.makeText(this@MainActivity, "Card já existente, movido ao top", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.stateGetRandonFact.value = null
                    setVisibilities(showList = true)
                }
                is ViewState.Failed -> {
                    InternetCheck(object : InternetCheck.Consumer {
                        override fun accept(internet: Boolean) {
                            if (!internet) {
                                AlertDialog.Builder(this@MainActivity)
                                    .setTitle("Erro")
                                    .setMessage("Sem conexão, tente novamente mais tarde")
                                    .setPositiveButton("OK") {_,_ ->}
                                    .show()
                            } else {
                                AlertDialog.Builder(this@MainActivity)
                                    .setTitle("Erro")
                                    .setMessage("Erro ao carregar card: ${state.throwable.message}")
                                    .setPositiveButton("OK") {_,_ ->}
                                    .show()
                            }
                        }
                    })
                    viewModel.stateGetRandonFact.value = null
                    setVisibilities(showList = true)
                }
            }
        })

        setVisibilities(showProgressBar = true, showList = true)
        viewModel.getRandomFact()
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
            alertDialog.setPositiveButton("OK") { _, _ -> }
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
