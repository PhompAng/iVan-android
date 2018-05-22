package com.firebaseapp.ivan.ivan.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.wongnai.android.MultipleViewAdapter;

/**
 * @author Xenocide93 on 2/7/17.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {
	private int spacing;
	private boolean isSpaceTop = true;
	private boolean isSpaceBottom = true;
	private boolean isSpaceLeft = true;
	private boolean isSpaceRight = true;
	private boolean hasSpaceOnTheEdge = true;
	private MultipleViewAdapter adapter;
	private GridLayoutManager.SpanSizeLookup spanSizeLookup;

	private SparseArray<PositionData> positionCache = new SparseArray<>();

	/**
	 * Set enable spaceBottom.
	 *
	 * @param spaceBottom
	 *            spaceBottom
	 */
	public void setSpaceBottom(boolean spaceBottom) {
		isSpaceBottom = spaceBottom;
	}

	/**
	 * Set enable spaceLeft.
	 *
	 * @param spaceLeft
	 *            spaceLeft
	 */
	public void setSpaceLeft(boolean spaceLeft) {
		isSpaceLeft = spaceLeft;
	}

	/**
	 * Set enable spaceRight.
	 *
	 * @param spaceRight
	 *            spaceRight
	 */
	public void setSpaceRight(boolean spaceRight) {
		isSpaceRight = spaceRight;
	}

	/**
	 * Set enable spaceTop.
	 *
	 * @param spaceTop
	 *            spaceTop
	 */
	public void setSpaceTop(boolean spaceTop) {
		isSpaceTop = spaceTop;
	}

	/**
	 * The Menu Item offset.
	 *
	 * @param itemOffset
	 *            spacing item
	 */
	public GridItemDecoration(int itemOffset) {
		spacing = itemOffset;
	}

	/**
	 * The Menu Item offset.
	 *
	 * @param context
	 *            Context
	 * @param itemOffsetId
	 *            itemOffsetId
	 */
	public GridItemDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
		this(context.getResources().getDimensionPixelSize(itemOffsetId));
		Log.d("ItemDecoration", "construct decoration");
	}

	/**
	 * Gets is edge.
	 *
	 * @return is edge
	 */
	public boolean hasSpaceOnTheEdge() {
		return hasSpaceOnTheEdge;
	}

	/**
	 * Sets is hasSpaceOnTheEdge.
	 *
	 * @param hasSpaceOnTheEdge
	 *            hasSpaceOnTheEdge
	 */
	public void setHasSpaceOnTheEdge(boolean hasSpaceOnTheEdge) {
		this.hasSpaceOnTheEdge = hasSpaceOnTheEdge;
	}

	/**
	 * Sets adapter to indicate the header and footer index.
	 *
	 * @param adapter
	 *            adapter
	 */
	public void setAdapter(MultipleViewAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * Sets span size look up.
	 *
	 * @param spanSizeLookup
	 *            spanSizeLookup
	 */
	public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
		this.spanSizeLookup = spanSizeLookup;
	}

	/**
	 * Clear cache in case of refreshing adapter data.
	 */
	public void clearItemDecorationCache() {
		Log.d("ItemDecoration", "clear item decoration cache");
		positionCache.clear();
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);

		if (!(view.getLayoutParams() instanceof GridLayoutManager.LayoutParams)) {
			throw new IllegalStateException("BusinessPhotoGridItemDecoration only support GridLayoutManager");
		}

		GridLayoutManager.LayoutParams layoutParams = ((GridLayoutManager.LayoutParams) view.getLayoutParams());
		int maxSpanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
		int index = parent.getChildAdapterPosition(view);
		int span = spanSizeLookup.getSpanSize(index);
		int column = layoutParams.getSpanIndex();
		PositionData positionData = getPositionData(index, maxSpanCount, spanSizeLookup.getSpanSize(index));

		if (positionData == null) {
			fillSpaceForUnknownPosition(outRect);
		} else {
			fillSpace(outRect, index, positionData.row, column, span, maxSpanCount);
		}
	}

	/**
	 * Fill space according to row column information provided.
	 *
	 * @param outRect
	 *            outRect
	 * @param index
	 *            index
	 * @param row
	 *            row
	 * @param column
	 *            column
	 * @param span
	 *            span
	 * @param maxSpanCount
	 *            maxSpanCount
	 */
	protected void fillSpace(Rect outRect, int index, int row, int column, int span, int maxSpanCount) {
		if (!hasSpaceOnTheEdge && maxSpanCount > 0) {
			if (span < maxSpanCount) {
				outRect.left = column * spacing / maxSpanCount; // startColumn * ((1f / maxSpanCount) * spacing)
				outRect.right = spacing - (column + 1) * spacing / maxSpanCount; // spacing - (startColumn + 1) * ((1f /    maxSpanCount) * spacing)
			}
			if (row == 0) {
				outRect.top = 0;
			} else {
				outRect.top = spacing; // item top
			}
		} else {
			outRect.set(isSpaceLeft ? spacing : 0, isSpaceTop ? spacing : 0, isSpaceRight ? spacing : 0,
					isSpaceBottom ? spacing : 0);
		}
	}

	/**
	 * In case adapter does not report position or report as -1, fallback to this
	 * function to fill space.
	 * 
	 * @param outRect
	 *            outRect
	 */
	protected void fillSpaceForUnknownPosition(Rect outRect) {
		outRect.set(0, 0, 0, 0);
	}

	private PositionData getPositionData(int adapterPosition, int maxSpan, int currentPositionSpanSize) {
		// position zero, easy answer.
		if (adapterPosition == 0) {
			return getZeroPositionData();
		}

		// adapter does not specify position, what the ... just
		if (adapterPosition < 0) {

		}

		// has cache, answer with cached value
		PositionData cacheValue = positionCache.get(adapterPosition);
		if (cacheValue != null) {
			Log.v("ItemDecoration", "Cache hit, position = " + adapterPosition);
			return cacheValue;
		}

		// no luck, start loop to find answer
		Log.d("ItemDecoration", "Cache missed, position = " + adapterPosition);
		return rebuildPositionCacheUntilPosition(adapterPosition, maxSpan, currentPositionSpanSize);
	}

	private PositionData rebuildPositionCacheUntilPosition(int adapterPosition, int maxSpan, int currentPositionSpanSize) {
		// find the last unknown PositionData index
		int lastKnownPosition = -1;
		for (int i = adapterPosition; i >= 0; i--) {
			PositionData cacheValue = positionCache.get(i);
			if (cacheValue != null) {
				lastKnownPosition = i;
				break;
			}
		}
		if (lastKnownPosition == -1) {
			positionCache.append(0, getZeroPositionData());
			lastKnownPosition = 0;
		} else if (lastKnownPosition < -1) {
			throw new IllegalStateException("previousData shouldn't be less than -1 (" + lastKnownPosition + ")");
		}

		// Got that last unknown position. re-built the cache position until the requested position.
		for (int i = lastKnownPosition + 1; i <= adapterPosition; i++) {
			PositionData previousPositionData = positionCache.get(i - 1);
			if (previousPositionData == null) {
				throw new IllegalStateException(
						"previousData shouldn't be null here, miscalculate lastKnownPosition = " + lastKnownPosition);
			}

			int previousPositionSpanSize = previousPositionData.spanSize;
			int row = previousPositionData.row;
			int previousStartColumn = previousPositionData.startColumn;

			// got previous position, calculate current position data
			int currentStartColumn;
			if (previousStartColumn + previousPositionSpanSize + currentPositionSpanSize > maxSpan) {
				// can not fit in this row, go to next row.
				row++;
				currentStartColumn = 0;
			} else {
				// enough space in this row.
				currentStartColumn = previousStartColumn + previousPositionSpanSize;
			}

			Log.d("ItemDecoration", "rebuild Cache, position = " + i);
			PositionData rebuiltData = new PositionData(i, currentPositionSpanSize, row, currentStartColumn);

			// got to PositionData for index i, cache data
			positionCache.append(i, rebuiltData);

			if (i == adapterPosition) {
				return rebuiltData;
			}
		}
		return null;
	}

	private PositionData getZeroPositionData() {
		Log.d("ItemDecoration", "rebuild Cache, position = 0 (easy answer)");
		return new PositionData(0, spanSizeLookup.getSpanSize(0), 0, 0);
	}

	private class PositionData {
		private int index = -1; // 0-base
		private int row = -1; // 0-base
		private int startColumn = -1; // 0-base
		private int spanSize = -1;

		PositionData(int index, int spanSize, int row, int startColumn) {
			this.index = index;
			this.spanSize = spanSize;
			this.row = row;
			this.startColumn = startColumn;
		}

		@Override
		public String toString() {
			return "index: " + index + " | row: " + row + " | column: " + startColumn + " | span: " + spanSize;
		}
	}
}
