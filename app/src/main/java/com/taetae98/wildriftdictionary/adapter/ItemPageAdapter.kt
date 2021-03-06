package com.taetae98.wildriftdictionary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.wildriftdictionary.R
import com.taetae98.wildriftdictionary.base.BaseAdapter
import com.taetae98.wildriftdictionary.base.BaseHolder
import com.taetae98.wildriftdictionary.databinding.HolderItemPageBinding
import com.taetae98.wildriftdictionary.dto.Item
import com.taetae98.wildriftdictionary.repository.ItemRepository
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ItemPageAdapter @Inject constructor(
    private val itemRepository: ItemRepository
) : BaseAdapter<ItemPageAdapter.ItemPage>(itemCallback) {
    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<ItemPage>() {
            override fun areItemsTheSame(oldItem: ItemPage, newItem: ItemPage): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: ItemPage, newItem: ItemPage): Boolean {
                return oldItem == newItem
            }
        }
    }

    init {
        setHasStableIds(true)
        submitList(
            listOf(
                ItemPage(R.drawable.icon_physical, Item.Type.PHYSICAL),
                ItemPage(R.drawable.icon_magic, Item.Type.MAGIC),
                ItemPage(R.drawable.icon_defense, Item.Type.DEFENSE),
                ItemPage(R.drawable.icon_boots, Item.Type.BOOTS),
            )
        )
    }

    var onItemClickListener: ((Item) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, ItemPage> {
        return ItemPageHolder(
            HolderItemPageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    inner class ItemPageHolder(binding: HolderItemPageBinding) : BaseHolder<HolderItemPageBinding, ItemPage>(binding) {
        private val upgradedItemAdapter by lazy {
            ItemAdapter().apply {
                onItemClickListener = {
                    this@ItemPageAdapter.onItemClickListener?.invoke(it)
                }
            }
        }

        private val midItemAdapter by lazy {
            ItemAdapter().apply {
                onItemClickListener = {
                    this@ItemPageAdapter.onItemClickListener?.invoke(it)
                }
            }
        }

        private val enchantmentsItemAdapter by lazy {
            ItemAdapter().apply {
                onItemClickListener = {
                    this@ItemPageAdapter.onItemClickListener?.invoke(it)
                }
            }
        }

        private val basicItemAdapter by lazy {
            ItemAdapter().apply {
                onItemClickListener = {
                    this@ItemPageAdapter.onItemClickListener?.invoke(it)
                }
            }
        }

        override fun onBind(element: ItemPage) {
            super.onBind(element)
            binding.itemPage = element

            onCreateUpgradedRecyclerView()
            onCreateMidRecyclerView()
            onCreateEnchantmentsRecyclerView()
            onCreateBasicRecyclerView()
        }

        private fun onCreateUpgradedRecyclerView() {
            with(binding.upgradedRecyclerView) {
                adapter = upgradedItemAdapter
            }

            upgradedItemAdapter.submitList(itemRepository.findByTypeAndLevel(element.type, Item.Level.UPGRADED))
        }

        private fun onCreateMidRecyclerView() {
            with(binding.midRecyclerView) {
                adapter = midItemAdapter
            }

            midItemAdapter.submitList(itemRepository.findByTypeAndLevel(element.type, Item.Level.MID))
        }

        private fun onCreateEnchantmentsRecyclerView() {
            with(binding.enchantmentsRecyclerView) {
                adapter = enchantmentsItemAdapter
            }

            enchantmentsItemAdapter.submitList(itemRepository.findByTypeAndLevel(element.type, Item.Level.ENCHANTMENTS))
        }

        private fun onCreateBasicRecyclerView() {
            with(binding.basicRecyclerView) {
                adapter = basicItemAdapter
            }

            basicItemAdapter.submitList(itemRepository.findByTypeAndLevel(element.type, Item.Level.BASIC))
        }
    }

    data class ItemPage(
        @DrawableRes
        val icon: Int,
        val type: Item.Type
    )
}