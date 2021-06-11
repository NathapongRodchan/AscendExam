package com.me.ascendexam.Utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val mContext: Context?, private val spanCount: Int, private val  spacingDp: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val spacingPx = getPx(spacingDp)
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = spacingPx - column * spacingPx / spanCount
        outRect.right = (column + 1) * spacingPx / spanCount

        if (position < spanCount) {
            outRect.top = spacingPx
        }
        outRect.bottom = spacingPx
    }

    private fun getPx(dp: Int): Int{
        mContext?.let{
            val scale = it.resources.displayMetrics.density
            return@getPx (dp * scale).toInt()
        } ?: return dp
    }
}