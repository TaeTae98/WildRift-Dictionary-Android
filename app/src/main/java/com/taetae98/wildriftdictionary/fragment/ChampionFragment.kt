package com.taetae98.wildriftdictionary.fragment

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.taetae98.wildriftdictionary.R
import com.taetae98.wildriftdictionary.adapter.ChampionPageAdapter
import com.taetae98.wildriftdictionary.databinding.BindingFragment
import com.taetae98.wildriftdictionary.databinding.FragmentChampionBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChampionFragment : BindingFragment<FragmentChampionBinding>(R.layout.fragment_champion) {
    @Inject
    lateinit var championPageAdapter: ChampionPageAdapter

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_champion_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.search -> {
                findNavController().navigate(ChampionFragmentDirections.actionChampionFragmentToChampionSearchDialog())
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateViewPager()
        onCreateTabLayoutMediator()

        return binding.root
    }

    private fun onCreateViewPager() {
        with(binding.viewPager) {
            adapter = championPageAdapter
        }

        championPageAdapter.onChampionClickListener = {
            findNavController().navigate(ChampionFragmentDirections.actionChampionFragmentToChampionActivity(it))
        }
    }

    private fun onCreateTabLayoutMediator() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setIcon(championPageAdapter.currentList[position].icon)
        }.attach()
    }
}