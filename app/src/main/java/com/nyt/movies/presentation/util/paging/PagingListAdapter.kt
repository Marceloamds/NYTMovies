package com.nyt.movies.presentation.util.paging

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class PagingListAdapter<T, ViewHolder : RecyclerView.ViewHolder>(
    private val onProgressShownCallback: () -> Unit?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_PROGRESS = -4
        private const val VIEW_TYPE_PROGRESS_SUB_ITEM = -3
    }

    private var progressVisible = false
    private val progressPosition get() = shownList.size
    var shownList = mutableListOf<T>()

    abstract fun onCreateSubViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

    abstract fun onBindSubViewHolder(holder: ViewHolder, position: Int)

    override fun getItemCount(): Int {
        return if (progressVisible) shownList.size + 1 else shownList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PROGRESS -> ProgressViewHolder.inflate(parent)
            else -> onCreateSubViewHolder(parent, viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (progressVisible && position == progressPosition) return VIEW_TYPE_PROGRESS
        return getProgressSubItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProgressViewHolder) {
            onProgressShownCallback.invoke()
        } else {
            onBindSubViewHolder(holder as ViewHolder, position)
        }
    }

    open fun getProgressSubItemViewType(position: Int) = VIEW_TYPE_PROGRESS_SUB_ITEM

    fun setProgressVisible(visible: Boolean) {
        if (progressVisible == visible) {
            if (visible) notifyItemChanged(progressPosition)
        } else {
            progressVisible = visible
            if (visible) notifyItemInserted(progressPosition)
            else notifyItemRemoved(progressPosition)
        }
    }

    fun submitList(list: List<T>) {
        shownList = list.toMutableList()
        notifyDataSetChanged()
    }
}
