package com.nyt.movies.presentation.util.paging

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nyt.movies.presentation.util.extension.adding

abstract class PagingListAdapter<T, ViewHolder : RecyclerView.ViewHolder>(
    diffUtilCallback: DiffUtil.ItemCallback<T>,
    private val onProgressShownCallback: () -> Unit?
) : ListAdapter<T, RecyclerView.ViewHolder>(diffUtilCallback) {

    companion object {
        private const val VIEW_TYPE_PROGRESS = -4
        private const val VIEW_TYPE_PROGRESS_SUB_ITEM = -3
    }

    private var progressVisible = false
    private var currentMoviesList = listOf<T>()

    abstract fun onCreateSubViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

    abstract fun onBindSubViewHolder(holder: ViewHolder, position: Int)

    override fun getItemCount(): Int {
        return if (progressVisible) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PROGRESS -> ProgressViewHolder.inflate(parent)
            else -> onCreateSubViewHolder(parent, viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (progressVisible && position == getProgressPosition()) return VIEW_TYPE_PROGRESS
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
            if (visible) notifyItemChanged(getProgressPosition())
        } else {
            progressVisible = visible
            if (visible) notifyItemInserted(getProgressPosition())
            else notifyItemRemoved(getProgressPosition())
        }
    }

    private fun getProgressPosition() = super.getItemCount()
}
