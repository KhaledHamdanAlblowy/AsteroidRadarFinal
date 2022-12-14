package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.udacity.asteroidradar.FeedsAdapter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.worker.SyncWorker
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {
    
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        
        initWorkManager()
        
        // observe the data being returned from api to get Feeds
        viewModel.feeds.observe(viewLifecycleOwner){
            binding.statusLoadingWheel.isVisible = false
            val adapter = FeedsAdapter(it)
            binding.asteroidRecycler.adapter = adapter
        }
        
        setHasOptionsMenu(true)
        
        
        binding.statusLoadingWheel.isVisible = true
        if (viewModel.mainImage.value == null) viewModel.getTopImage()  // to call api once for example when user came back to this screen from detail screen
        if (viewModel.feeds.value == null) viewModel.getFeeds()
        
        return binding.root
    }
    
    private fun initWorkManager() {
        // work manager
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    
        val workManager = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
    
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "AsterWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            workManager
        )
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
