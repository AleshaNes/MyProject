package com.alesha.my2048;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CellLayout extends ViewGroup {

    private int rowCount = 2;
    private int cellSize;
    private int spacing;

    public CellLayout(Context context) {
        super(context);
    }

    public CellLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CellLayout);
        rowCount = a.getInt(R.styleable.CellLayout_row_count, 2);
        spacing = (int)a.getDimension(R.styleable.CellLayout_spacing, 8);
        a.recycle();
    }

    public CellLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CellLayout);
        rowCount = a.getInt(R.styleable.CellLayout_row_count, 2);
        spacing = (int)a.getDimension(R.styleable.CellLayout_spacing, 8);
        a.recycle();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CellLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CellLayoutParams;
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CellLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CellLayoutParams();
    }

    private int measureWidth(int measureSpec){
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int size = 200;

        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY)
            size = specSize;

        return size;
    }

    private int measureHeight(int measureSpec){
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int size = 200;

        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY)
            size = specSize;

        return size;
    }

    private void childMeasure(){
        int childWidthSpec, childHeightSpec;
        View child;
        for (int i = 0; i < rowCount * rowCount; ++i) {
                child = getChildAt(i);
                childWidthSpec = MeasureSpec.makeMeasureSpec(cellSize - spacing * 2, MeasureSpec.EXACTLY);
                childHeightSpec = MeasureSpec.makeMeasureSpec(cellSize - spacing * 2, MeasureSpec.EXACTLY);
                child.measure(childWidthSpec, childHeightSpec);
            }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);

        int size = Math.min(measuredWidth, measureHeight);
        cellSize = (size - spacing * 2) / rowCount;

        childMeasure();

        setMeasuredDimension(size, size);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child;
        CellLayoutParams cellLayoutParams;
        for (int i = 0; i < rowCount * rowCount; i++) {
                child = getChildAt(i);
                cellLayoutParams = (CellLayoutParams)child.getLayoutParams();

                int left = cellLayoutParams.getColumn() * cellSize + spacing * 2;
                int top = cellLayoutParams.getRow() * cellSize + spacing * 2;
                int right = (cellLayoutParams.getColumn() + 1) * cellSize;
                int bottom = (cellLayoutParams.getRow() + 1) * cellSize;
                child.layout(left, top, right, bottom);
            }
    }

    public static class CellLayoutParams extends ViewGroup.LayoutParams {
        private int row = 0;
        private int column = 0;

        public CellLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);

            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CellLayout);
            row = a.getInt(R.styleable.CellLayout_layout_row, 0);
            column = a.getInt(R.styleable.CellLayout_layout_column, 0);

            a.recycle();
        }

        public CellLayoutParams(LayoutParams params) {
            super(params);

            if (params instanceof CellLayoutParams) {
                CellLayoutParams cellLayoutParams = (CellLayoutParams) params;
                row = cellLayoutParams.row;
                column = cellLayoutParams.column;
            }
        }

        public CellLayoutParams() {
            this(MATCH_PARENT, MATCH_PARENT);
        }

        public CellLayoutParams(int width, int height) {
            super(width, height);
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

}
