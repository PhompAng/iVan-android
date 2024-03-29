package com.wongnai.android

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup

/**
 * @author phompang on 21/1/2018 AD.
 */
class MultipleViewAdapter(factorySize: Int) : RecyclerView.Adapter<ItemViewHolder<Any>>() {
	private val viewHolderFactories: SparseArray<ViewHolderFactory<*>> = SparseArray(factorySize)
	private val list = mutableListOf<ObjectHolder>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<Any> {
		return viewHolderFactories[viewType].create(parent) as ItemViewHolder<Any>
	}

	override fun getItemCount(): Int = list.size

	override fun getItemViewType(position: Int): Int {
		return list[position].type
	}

	override fun onBindViewHolder(holder: ItemViewHolder<Any>, position: Int) {
		if (position < list.size) {
			val obj = list[position].obj
			if (obj != null) {
				holder.fillData(obj, position)
			}
		}
	}

	fun registerViewHolderFactory(type: Int, viewHolderFactory: ViewHolderFactory<*>) {
		viewHolderFactories.put(type, viewHolderFactory)
	}

	fun add(item: Any, type: Int, position: Int = list.size, notifyItemInserted: Boolean = true) {
		if (viewHolderFactories.get(type) == null) {
			throw NullPointerException("Register view holder factory before  type " + type)
		}
		if (position < 0) {
			return
		}

		val holder = ObjectHolder(item, type)
		if (position <= list.size) {
			list.add(position, holder)
		}
		if (notifyItemInserted) {
			notifyItemInserted(position)
		}
	}

	fun addAll(items: List<Any>, type: Int, notifyItemRangeInserted: Boolean = true) {
		if (viewHolderFactories.get(type) == null) {
			throw NullPointerException("Register view holder factory before  type " + type)
		}

		var startIndex = -1
		for (item in items) {
			val holder = ObjectHolder(item, type)
			list.add(holder)
			if (startIndex == -1) {
				startIndex = list.size
			}
		}
		if (notifyItemRangeInserted) {
			notifyItemRangeInserted(startIndex, items.size)
		}
	}

	fun remove(item: Any) {
		var foundIndex = -1
		list.forEachIndexed { index, objectHolder ->
			if (objectHolder.obj == item) {
				foundIndex = index
				return@forEachIndexed
			}
		}
		if (foundIndex > -1) {
			list.removeAt(foundIndex)
			notifyItemRemoved(foundIndex)
		}
	}

	fun remove(type: Int) {
		val removeIndex = mutableListOf<Int>()
		list.forEachIndexed { index, objectHolder ->
			if (objectHolder.type == type) {
				removeIndex += index
			}
		}
		if (removeIndex.isEmpty()) {
			notifyDataSetChanged()
			return
		}
		removeIndex.forEach {
			list.removeAt(it)
			notifyItemRemoved(it)
		}
	}

	fun clear() {
		list.clear()
		notifyDataSetChanged()
	}

	fun getItem(position: Int): Any? = list[position].obj

	inner class ObjectHolder(val obj: Any?, val type: Int)
}