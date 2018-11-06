package com.frate.luxup.gallery.adapter

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GridSpacesItemDecoration(
        private var space: Int,
        private var numberOfColumns: Int,
        private var paddingBottom: Int = -1) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemPosition = parent.getChildAdapterPosition(view)
        val numberOfItems = parent.adapter!!.itemCount

        if (itemPosition % numberOfColumns != 0) { // items not in left column
            outRect.left = space
        }

        if (isNotInLastRow(itemPosition, numberOfItems)) {
            outRect.bottom = space
        } else if (paddingBottom != -1) {
            outRect.bottom = paddingBottom
        }
    }

    private fun isNotInLastRow(itemPosition: Int, numberOfItems: Int): Boolean {
        val numberOfRows = Math.ceil((numberOfItems / numberOfColumns).toDouble())
        val itemRow = Math.ceil(((itemPosition + 1) / numberOfColumns).toDouble())
        return itemRow == numberOfRows
    }
}
