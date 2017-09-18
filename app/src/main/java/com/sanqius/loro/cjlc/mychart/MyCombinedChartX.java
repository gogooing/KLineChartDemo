package com.sanqius.loro.cjlc.mychart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.sanqius.loro.cjlc.bean.DataParse;

/**
 * Created by loro on 2017/2/8.
 */
public class MyCombinedChartX extends CombinedChart {
    private MyLeftMarkerView myMarkerViewLeft;
    private MyHMarkerView myMarkerViewH;
    private MyBottomMarkerView myBottomMarkerView;
    private DataParse minuteHelper;

    public MyCombinedChartX(Context context) {
        super(context);
    }

    public MyCombinedChartX(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCombinedChartX(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        /*此两处不能重新示例*/
        mXAxis = new MyXAxis();
        mAxisLeft = new MyYAxis(YAxis.AxisDependency.LEFT);
        mXAxisRenderer = new MyXAxisRenderer(mViewPortHandler, (MyXAxis) mXAxis, mLeftAxisTransformer, this);
        mAxisRendererLeft = new MyYAxisRenderer(mViewPortHandler, (MyYAxis) mAxisLeft, mLeftAxisTransformer);

        mAxisRight = new MyYAxis(YAxis.AxisDependency.RIGHT);
        mAxisRendererRight = new MyYAxisRenderer(mViewPortHandler, (MyYAxis) mAxisRight, mRightAxisTransformer);

    }

    /*返回转型后的左右轴*/
    @Override
    public MyYAxis getAxisLeft() {
        return (MyYAxis) super.getAxisLeft();
    }

    @Override
    public MyXAxis getXAxis() {
        return (MyXAxis) super.getXAxis();
    }


    @Override
    public MyYAxis getAxisRight() {
        return (MyYAxis) super.getAxisRight();
    }


    public void setMarker(MyLeftMarkerView markerLeft, MyHMarkerView markerH, DataParse minuteHelper) {
        this.myMarkerViewLeft = markerLeft;
        this.myMarkerViewH = markerH;
        this.minuteHelper = minuteHelper;
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyBottomMarkerView markerBottom, DataParse minuteHelper) {
        this.myMarkerViewLeft = markerLeft;
        this.myBottomMarkerView = markerBottom;
        this.minuteHelper = minuteHelper;
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyBottomMarkerView markerBottom, MyHMarkerView markerH, DataParse minuteHelper) {
        this.myMarkerViewLeft = markerLeft;
        this.myBottomMarkerView = markerBottom;
        this.myMarkerViewH = markerH;
        this.minuteHelper = minuteHelper;
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        if (!mDrawMarkerViews || !valuesToHighlight())
            return;
        for (int i = 0; i < mIndicesToHighlight.length; i++) {
            Highlight highlight = mIndicesToHighlight[i];
            int xIndex = mIndicesToHighlight[i].getXIndex();
            int dataSetIndex = mIndicesToHighlight[i].getDataSetIndex();
            float deltaX = mXAxis != null
                    ? mXAxis.mAxisRange
                    : ((mData == null ? 0.f : mData.getXValCount()) - 1.f);
            if (xIndex <= deltaX && xIndex <= deltaX * mAnimator.getPhaseX()) {
                Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
                // make sure entry not null
                if (e == null || e.getXIndex() != mIndicesToHighlight[i].getXIndex())
                    continue;
                float[] pos = getMarkerPosition(e, highlight);
                // check bounds
                if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                    continue;

                if (null != myMarkerViewH) {
                    myMarkerViewH.refreshContent(e, mIndicesToHighlight[i]);
                    int width = (int) mViewPortHandler.contentWidth();
                    myMarkerViewH.setTvWidth(width);
                    myMarkerViewH.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    myMarkerViewH.layout(0, 0, width,
                            myMarkerViewH.getMeasuredHeight());
                    myMarkerViewH.draw(canvas, mViewPortHandler.contentLeft(), mIndicesToHighlight[i].getTouchY() - myMarkerViewH.getHeight() / 2);
                }

                if (null != myMarkerViewLeft) {
                    //修改标记值
                    float yValForHighlight = mIndicesToHighlight[i].getTouchYValue();
                    myMarkerViewLeft.setData(yValForHighlight);

                    myMarkerViewLeft.refreshContent(e, mIndicesToHighlight[i]);

                    myMarkerViewLeft.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    myMarkerViewLeft.layout(0, 0, myMarkerViewLeft.getMeasuredWidth(),
                            myMarkerViewLeft.getMeasuredHeight());

                    myMarkerViewLeft.draw(canvas, mViewPortHandler.contentLeft(), mIndicesToHighlight[i].getTouchY() - myMarkerViewLeft.getHeight() / 2);

                }

                if (null != myBottomMarkerView) {
                    String time = minuteHelper.getDatas().get(mIndicesToHighlight[i].getXIndex()).time;
                    myBottomMarkerView.setData(time);
                    myBottomMarkerView.refreshContent(e, mIndicesToHighlight[i]);

                    myBottomMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    myBottomMarkerView.layout(0, 0, myBottomMarkerView.getMeasuredWidth(),
                            myBottomMarkerView.getMeasuredHeight());

                    myBottomMarkerView.draw(canvas, pos[0] - myBottomMarkerView.getWidth() / 2, mViewPortHandler.contentBottom());
                }


//                /*float yValForXIndex1 = minuteHelper.getKLineDatas().get(mIndicesToHighlight[i].getXIndex()).open;
//                float yValForXIndex2 = minuteHelper.getKLineDatas().get(mIndicesToHighlight[i].getXIndex()).close;*/
//                String time = minuteHelper.getDatas().get(xIndex).time;
//                myBottomMarkerView.setData(time);
//
//                //修改标记值
//                float yValForHighlight = mIndicesToHighlight[i].getTouchYValue();
//                myMarkerViewLeft.setData(yValForHighlight);
//
//                /*myMarkerViewLeft.setData(yValForXIndex1);
//                myMarkerViewRight.setData(yValForXIndex2);*/
//
//                myMarkerViewLeft.refreshContent(e, mIndicesToHighlight[i]);
//
//                myBottomMarkerView.refreshContent(e, mIndicesToHighlight[i]);
//
//
//                if (null != myMarkerViewH) {
//                    myMarkerViewH.refreshContent(e, mIndicesToHighlight[i]);
//                    int width = (int) mViewPortHandler.contentWidth();
//                    myMarkerViewH.setTvWidth(width);
//                    myMarkerViewH.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                    myMarkerViewH.layout(0, 0, width,
//                            myMarkerViewH.getMeasuredHeight());
//                    myMarkerViewH.draw(canvas, mViewPortHandler.contentLeft(), mIndicesToHighlight[i].getTouchY() - myMarkerViewH.getHeight() / 2);
//                }
//
//
//                myMarkerViewLeft.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                myMarkerViewLeft.layout(0, 0, myMarkerViewLeft.getMeasuredWidth(),
//                        myMarkerViewLeft.getMeasuredHeight());
//
//
//                myBottomMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                myBottomMarkerView.layout(0, 0, myBottomMarkerView.getMeasuredWidth(),
//                        myBottomMarkerView.getMeasuredHeight());
//
//
//                myBottomMarkerView.draw(canvas, pos[0] - myBottomMarkerView.getWidth() / 2, mViewPortHandler.contentBottom());
//                /*myMarkerViewLeft.draw(canvas, mViewPortHandler.contentLeft() - myMarkerViewLeft.getWidth(), pos[1] - myMarkerViewLeft.getHeight() / 2);
//                myMarkerViewRight.draw(canvas, mViewPortHandler.contentRight(), pos[1] - myMarkerViewRight.getHeight() / 2);*/
//
//                myMarkerViewLeft.draw(canvas, mViewPortHandler.contentLeft(), mIndicesToHighlight[i].getTouchY() - myMarkerViewLeft.getHeight() / 2);


            }
        }
    }
}
