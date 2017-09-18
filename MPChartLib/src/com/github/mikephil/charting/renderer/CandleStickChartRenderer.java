
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/*画蜡烛图、值、高亮*/
public class CandleStickChartRenderer extends LineScatterCandleRadarRenderer {

    protected CandleDataProvider mChart;

    private float[] mShadowBuffers = new float[8];
    private float[] mBodyBuffers = new float[4];
    private float[] mRangeBuffers = new float[4];
    private float[] mOpenBuffers = new float[4];
    private float[] mCloseBuffers = new float[4];

    public CandleStickChartRenderer(CandleDataProvider chart, ChartAnimator animator,
                                    ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        mChart = chart;
    }

    @Override
    public void initBuffers() {

    }

    @Override
    public void drawData(Canvas c) {

        CandleData candleData = mChart.getCandleData();

        for (ICandleDataSet set : candleData.getDataSets()) {

            if (set.isVisible() && set.getEntryCount() > 0)
                drawDataSet(c, set);
        }
    }

    @SuppressWarnings("ResourceAsColor")
    protected void drawDataSet(Canvas c, ICandleDataSet dataSet) {

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        float phaseX = Math.max(0.f, Math.min(1.f, mAnimator.getPhaseX()));
        float phaseY = mAnimator.getPhaseY();
        float barSpace = dataSet.getBarSpace();
        boolean showCandleBar = dataSet.getShowCandleBar();

        int minx = Math.max(mMinX, 0);
        int maxx = Math.min(mMaxX + 1, dataSet.getEntryCount());

        int lastIndex = (int) Math.ceil((maxx - minx) * phaseX + (float)minx);
        dataSet.calcMinMax(minx,lastIndex-1);
        float maxValue=dataSet.getYMax();
        float minValue=dataSet.getYMin();


        mRenderPaint.setStrokeWidth(dataSet.getShadowWidth());

        int minIndex = 0,maxIndex = 0;
        // draw the body
        for (int j = minx,
             count = (int) Math.ceil((maxx - minx) * phaseX + (float)minx);
             j < count;
             j++) {

            // get the entry
            CandleEntry e = dataSet.getEntryForIndex(j);

            final int xIndex = e.getXIndex();

            if (xIndex < minx || xIndex >= maxx)
                continue;

            final float open = e.getOpen();
            final float close = e.getClose();
            final float high = e.getHigh();
            final float low = e.getLow();

            if (showCandleBar) {
                // calculate the shadow

                mShadowBuffers[0] = xIndex;
                mShadowBuffers[2] = xIndex;
                mShadowBuffers[4] = xIndex;
                mShadowBuffers[6] = xIndex;

                if (open > close) {
                    mShadowBuffers[1] = high * phaseY;
                    mShadowBuffers[3] = open * phaseY;
                    mShadowBuffers[5] = low * phaseY;
                    mShadowBuffers[7] = close * phaseY;
                } else if (open < close) {
                    mShadowBuffers[1] = high * phaseY;
                    mShadowBuffers[3] = close * phaseY;
                    mShadowBuffers[5] = low * phaseY;
                    mShadowBuffers[7] = open * phaseY;
                } else {
                    mShadowBuffers[1] = high * phaseY;
                    mShadowBuffers[3] = open * phaseY;
                    mShadowBuffers[5] = low * phaseY;
                    mShadowBuffers[7] = mShadowBuffers[3];
                }

                trans.pointValuesToPixel(mShadowBuffers);

                // draw the shadows

                if (dataSet.getShadowColorSameAsCandle()) {

                    if (open > close)
                        mRenderPaint.setColor(
                                dataSet.getDecreasingColor() == ColorTemplate.COLOR_NONE ?
                                        dataSet.getColor(j) :
                                        dataSet.getDecreasingColor()
                        );

                    else if (open < close)
                        mRenderPaint.setColor(
                                dataSet.getIncreasingColor() == ColorTemplate.COLOR_NONE ?
                                        dataSet.getColor(j) :
                                        dataSet.getIncreasingColor()
                        );

                    else
                        mRenderPaint.setColor(
                                dataSet.getNeutralColor() == ColorTemplate.COLOR_NONE ?
                                        dataSet.getColor(j) :
                                        dataSet.getNeutralColor()
                        );

                } else {
                    mRenderPaint.setColor(
                            dataSet.getShadowColor() == ColorTemplate.COLOR_NONE ?
                                    dataSet.getColor(j) :
                                    dataSet.getShadowColor()
                    );
                }

                mRenderPaint.setStyle(Paint.Style.STROKE);

                c.drawLines(mShadowBuffers, mRenderPaint);

                // calculate the body

                mBodyBuffers[0] = xIndex - 0.5f + barSpace;
                mBodyBuffers[1] = close * phaseY;
                mBodyBuffers[2] = (xIndex + 0.5f - barSpace);
                mBodyBuffers[3] = open * phaseY;

                trans.pointValuesToPixel(mBodyBuffers);

                // draw body differently for increasing and decreasing entry
                if (open > close) { // decreasing

                    if (dataSet.getDecreasingColor() == ColorTemplate.COLOR_NONE) {
                        mRenderPaint.setColor(dataSet.getColor(j));
                    } else {
                        mRenderPaint.setColor(dataSet.getDecreasingColor());
                    }

                    mRenderPaint.setStyle(dataSet.getDecreasingPaintStyle());

                    c.drawRect(
                            mBodyBuffers[0], mBodyBuffers[3],
                            mBodyBuffers[2], mBodyBuffers[1],
                            mRenderPaint);

                } else if (open < close) {

                    if (dataSet.getIncreasingColor() == ColorTemplate.COLOR_NONE) {
                        mRenderPaint.setColor(dataSet.getColor(j));
                    } else {
                        mRenderPaint.setColor(dataSet.getIncreasingColor());
                    }

                    mRenderPaint.setStyle(dataSet.getIncreasingPaintStyle());

                    c.drawRect(
                            mBodyBuffers[0], mBodyBuffers[1],
                            mBodyBuffers[2], mBodyBuffers[3],
                            mRenderPaint);
                } else { // equal values

                    if (dataSet.getNeutralColor() == ColorTemplate.COLOR_NONE) {
                        mRenderPaint.setColor(dataSet.getColor(j));
                    } else {
                        mRenderPaint.setColor(dataSet.getNeutralColor());
                    }

                    c.drawLine(
                            mBodyBuffers[0], mBodyBuffers[1],
                            mBodyBuffers[2], mBodyBuffers[3],
                            mRenderPaint);
                }
            } else {

                mRangeBuffers[0] = xIndex;
                mRangeBuffers[1] = high * phaseY;
                mRangeBuffers[2] = xIndex;
                mRangeBuffers[3] = low * phaseY;

                mOpenBuffers[0] = xIndex - 0.5f + barSpace;
                mOpenBuffers[1] = open * phaseY;
                mOpenBuffers[2] = xIndex;
                mOpenBuffers[3] = open * phaseY;

                mCloseBuffers[0] = xIndex + 0.5f - barSpace;
                mCloseBuffers[1] = close * phaseY;
                mCloseBuffers[2] = xIndex;
                mCloseBuffers[3] = close * phaseY;

                trans.pointValuesToPixel(mRangeBuffers);
                trans.pointValuesToPixel(mOpenBuffers);
                trans.pointValuesToPixel(mCloseBuffers);

                // draw the ranges
                int barColor;

                if (open > close)
                    barColor = dataSet.getDecreasingColor() == ColorTemplate.COLOR_NONE
                            ? dataSet.getColor(j)
                            : dataSet.getDecreasingColor();
                else if (open < close)
                    barColor = dataSet.getIncreasingColor() == ColorTemplate.COLOR_NONE
                            ? dataSet.getColor(j)
                            : dataSet.getIncreasingColor();
                else
                    barColor = dataSet.getNeutralColor() == ColorTemplate.COLOR_NONE
                            ? dataSet.getColor(j)
                            : dataSet.getNeutralColor();

                mRenderPaint.setColor(barColor);
                c.drawLine(
                        mRangeBuffers[0], mRangeBuffers[1],
                        mRangeBuffers[2], mRangeBuffers[3],
                        mRenderPaint);
                c.drawLine(
                        mOpenBuffers[0], mOpenBuffers[1],
                        mOpenBuffers[2], mOpenBuffers[3],
                        mRenderPaint);
                c.drawLine(
                        mCloseBuffers[0], mCloseBuffers[1],
                        mCloseBuffers[2], mCloseBuffers[3],
                        mRenderPaint);

            }

            /*//显示最大值和最小值
            if (high == maxValue) {
                maxIndex = j;

                if (maxIndex > minIndex){
                    //画右边
                    String highString = "← " + Float.toString(high);

                    //计算显示位置
                    //计算文本宽度
                    int highStringWidth = Utils.calcTextWidth(mValuePaint, highString);
                    int highStringHeight = Utils.calcTextHeight(mValuePaint, highString);

                    trans.pointValuesToPixel(mBodyBuffers);
                    mValuePaint.setColor(dataSet.getValueTextColor(j / 2));
                    float[] valuePostion = new float[2];
                    valuePostion[0] = xIndex;
                    valuePostion[1] = high;
                    trans.pointValuesToPixel(valuePostion);
                    c.drawText(highString, valuePostion[0] + highStringWidth/2, valuePostion[1]-highStringHeight/2, mValuePaint);

                    //c.drawText(highString,0,highString.length(),);
                }else{
                    //画左边
                    String highString = Float.toString(high) +" →";

                    int highStringWidth = Utils.calcTextWidth(mValuePaint, highString);
                    int highStringHeight = Utils.calcTextHeight(mValuePaint, highString);

                    trans.pointValuesToPixel(mBodyBuffers);
                    mValuePaint.setColor(dataSet.getValueTextColor(j / 2));
                    float[] valuePostion = new float[2];
                    valuePostion[0] = xIndex;
                    valuePostion[1] = high;
                    trans.pointValuesToPixel(valuePostion);
                    c.drawText(highString, valuePostion[0] - highStringWidth/2, valuePostion[1]-highStringHeight/2, mValuePaint);
                }
            }

            if (low == minValue) {
                minIndex = j;
                if (maxIndex > minIndex){
                    //画右边
                    String highString = "← " + Float.toString(low);

                    //计算显示位置
                    //计算文本宽度
                    int highStringWidth = Utils.calcTextWidth(mValuePaint, highString);
                    int highStringHeight = Utils.calcTextHeight(mValuePaint, highString);

                    trans.pointValuesToPixel(mBodyBuffers);
                    mValuePaint.setColor(dataSet.getValueTextColor(j / 2));
                    float[] valuePostion = new float[2];
                    valuePostion[0] = xIndex;
                    valuePostion[1] = low;
                    trans.pointValuesToPixel(valuePostion);
                    c.drawText(highString, valuePostion[0]+highStringWidth/2, valuePostion[1]+highStringHeight, mValuePaint);

                    //c.drawText(highString,0,highString.length(),);
                }else{
                    //画左边
                    String highString = Float.toString(low) +" →";

                    int highStringWidth = Utils.calcTextWidth(mValuePaint, highString);
                    int highStringHeight = Utils.calcTextHeight(mValuePaint, highString);
                    trans.pointValuesToPixel(mBodyBuffers);
                    mValuePaint.setColor(dataSet.getValueTextColor(j / 2));
                    float[] valuePostion = new float[2];
                    valuePostion[0] = xIndex;
                    valuePostion[1] = low;
                    trans.pointValuesToPixel(valuePostion);
                    c.drawText(highString, valuePostion[0]-highStringWidth/2, valuePostion[1]+highStringHeight, mValuePaint);
                }
            }*/
        }
    }

    @Override
    public void drawValues(Canvas c) {

        // if values are drawn
        /*if (mChart.getCandleData().getYValCount() < mChart.getMaxVisibleCount()
                * mViewPortHandler.getScaleX()) {*/

            List<ICandleDataSet> dataSets = mChart.getCandleData().getDataSets();

            for (int i = 0; i < dataSets.size(); i++) {

                ICandleDataSet dataSet = dataSets.get(i);

                if (!dataSet.isDrawValuesEnabled() || dataSet.getEntryCount() == 0)
                    continue;

                // apply the text-styling defined by the DataSet
                applyValueTextStyle(dataSet);

                Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

                int minx = Math.max(mMinX, 0);
                int maxx = Math.min(mMaxX + 1, dataSet.getEntryCount());

                float[] positions = trans.generateTransformedValuesCandle(
                        dataSet, mAnimator.getPhaseX(), mAnimator.getPhaseY(), minx, maxx);

                float yOffset = Utils.convertDpToPixel(5f);

//                for (int j = 0; j < positions.length; j += 2) {
//
//                    float x = positions[j];
//                    float y = positions[j + 1];
//
//                    if (!mViewPortHandler.isInBoundsRight(x))
//                        break;
//
//                    if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y))
//                        continue;
//
//                    CandleEntry entry = dataSet.getEntryForIndex(j / 2 + minx);
//
//                    drawValue(c, dataSet.getValueFormatter(), entry.getHigh(), entry, i, x, y - yOffset, dataSet.getValueTextColor(j / 2));
//                }

//                float[] numbers = new float[positions.length];
                /*List<Float> numbers=new ArrayList<>();
                for (int j = 0; j < positions.length; j += 2) {
                    float x = positions[j];
                    float y = positions[j + 1];

                    if (!mViewPortHandler.isInBoundsRight(x))
                        break;

                    if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y))
                        continue;

                    CandleEntry entry = dataSet.getEntryForIndex(j / 2 + minx);
                    numbers.add(entry.getVal());
                }*/


                //绘制最大和最小值
                /*if(numbers.size()>0){
                    //dataSet.get
                    float max=numbers.get(0); // 把数据中的第1个元素存max
                    float min=numbers.get(0); // 把数据中的第1个元素存min
                    for(int k=1;k<numbers.size();k++){ // 从第二个元素开始遍历数组
                        if(numbers.get(k)>max){  // 假如元素大于max 就把当前值赋值给max
                            max=numbers.get(k);
                        }
                        if(numbers.get(k)<min){  // 假如元素小于min 就把当前值赋值给min
                            min=numbers.get(k);
                        }
                    }

                    for (int j = 0; j < positions.length; j += 2) {

                        float x = positions[j];
                        float y = positions[j + 1];

                        if (!mViewPortHandler.isInBoundsRight(x))
                            break;

                        if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y))
                            continue;

                        CandleEntry entry = dataSet.getEntryForIndex(j / 2 + minx);

                        if(entry.getVal()==max) {
                            drawValue(c, dataSet.getValueFormatter(), entry.getVal(), entry, i, x,
                                    y - yOffset, dataSet.getValueTextColor(j / 2));
                        }
                        if(entry.getVal()==min){
                            drawValue(c, dataSet.getValueFormatter(), entry.getVal(), entry, i, x,
                                    y + yOffset, dataSet.getValueTextColor(j / 2));
                        }
                    }
                }*/


                //计算最大值和最小值
                float maxValue = 0,minValue = 0;
                int maxIndex = 0 , minIndex = 0;
                CandleEntry maxEntry = null, minEntry = null;
                boolean firstInit = true;
                for (int j = 0; j < positions.length; j += 2) {

                    float x = positions[j];
                    float y = positions[j + 1];

                    if (!mViewPortHandler.isInBoundsRight(x))
                        break;

                    if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y))
                        continue;

                    CandleEntry entry = dataSet.getEntryForIndex(j / 2 + minx);

                    if (firstInit){
                        maxValue = entry.getHigh();
                        minValue = entry.getLow();
                        firstInit = false;
                        maxEntry = entry;
                        minEntry = entry;
                    }else{
                        if (entry.getHigh() > maxValue)
                        {
                            maxValue = entry.getHigh();
                            maxIndex = j;
                            maxEntry = entry;
                        }

                        if (entry.getLow() < minValue){
                            minValue = entry.getLow();
                            minIndex = j;
                            minEntry = entry;
                        }

                    }
                }

                //绘制最大值和最小值
                float x = positions[minIndex];
                float y = positions[minIndex + 1];
                if (maxIndex > minIndex){
                    //画右边
                    String highString = "← " + Float.toString(minValue);

                    //计算显示位置
                    //计算文本宽度
                    int highStringWidth = Utils.calcTextWidth(mValuePaint, highString);
                    int highStringHeight = Utils.calcTextHeight(mValuePaint, highString);

                    float[] tPosition=new float[2];
                    tPosition[1]=minValue;
                    trans.pointValuesToPixel(tPosition);
                    mValuePaint.setColor(dataSet.getValueTextColor(minIndex / 2));
                    c.drawText(highString, x + highStringWidth /2, tPosition[1], mValuePaint);
                }else{
                    //画左边
                    String highString = Float.toString(minValue) +" →";

                    //计算显示位置
                    int highStringWidth = Utils.calcTextWidth(mValuePaint, highString);
                    int highStringHeight = Utils.calcTextHeight(mValuePaint, highString);

                    /*mValuePaint.setColor(dataSet.getValueTextColor(minIndex / 2));
                    c.drawText(highString, x-highStringWidth/2, y+yOffset, mValuePaint);*/
                    float[] tPosition=new float[2];
                    tPosition[1]=minValue;
                    trans.pointValuesToPixel(tPosition);
                    mValuePaint.setColor(dataSet.getValueTextColor(minIndex / 2));
                    c.drawText(highString, x - highStringWidth /2, tPosition[1], mValuePaint);
                }

                x = positions[maxIndex];
                y = positions[maxIndex + 1];
                if (maxIndex > minIndex){
                    //画左边
                    String highString = Float.toString(maxValue) +" →";

                    int highStringWidth = Utils.calcTextWidth(mValuePaint, highString);
                    int highStringHeight = Utils.calcTextHeight(mValuePaint, highString);

                    float[] tPosition=new float[2];
                    tPosition[0] = maxEntry == null ? 0f:maxEntry.getXIndex();
                    tPosition[1] = maxEntry == null ? 0f:maxEntry.getHigh();
                    trans.pointValuesToPixel(tPosition);

                    mValuePaint.setColor(dataSet.getValueTextColor(maxIndex / 2));
                    //c.drawText(highString, x+highStringWidth , y-yOffset, mValuePaint);
                    c.drawText(highString, tPosition[0] - highStringWidth /2, tPosition[1], mValuePaint);

                    /*mValuePaint.setColor(dataSet.getValueTextColor(maxIndex / 2));
                    c.drawText(highString, x - highStringWidth, y-yOffset, mValuePaint);*/
                }else{
                    //画右边
                    String highString = "← " + Float.toString(maxValue);

                    //计算显示位置
                    int highStringWidth = Utils.calcTextWidth(mValuePaint, highString);
                    int highStringHeight = Utils.calcTextHeight(mValuePaint, highString);

                    float[] tPosition=new float[2];
                    tPosition[0] = maxEntry == null ? 0f:maxEntry.getXIndex();
                    tPosition[1] = maxEntry == null ? 0f:maxEntry.getHigh();
                    trans.pointValuesToPixel(tPosition);

                    mValuePaint.setColor(dataSet.getValueTextColor(maxIndex / 2));
                    //c.drawText(highString, x+highStringWidth , y-yOffset, mValuePaint);
                    c.drawText(highString, tPosition[0] +highStringWidth/2, tPosition[1], mValuePaint);

                }

            }
        //}
    }



    @Override
    public void drawExtras(Canvas c) {
    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {

        CandleData candleData = mChart.getCandleData();

        for (Highlight high : indices) {

            final int minDataSetIndex = high.getDataSetIndex() == -1
                    ? 0
                    : high.getDataSetIndex();
            final int maxDataSetIndex = high.getDataSetIndex() == -1
                    ? candleData.getDataSetCount()
                    : (high.getDataSetIndex() + 1);
            if (maxDataSetIndex - minDataSetIndex < 1) 
                continue;

            for (int dataSetIndex = minDataSetIndex;
                 dataSetIndex < maxDataSetIndex;
                 dataSetIndex++) {

                int xIndex = high.getXIndex(); // get the
                // x-position

                ICandleDataSet set = mChart.getCandleData().getDataSetByIndex(dataSetIndex);

                if (set == null || !set.isHighlightEnabled())
                    continue;

                CandleEntry e = set.getEntryForXIndex(xIndex);

                if (e == null || e.getXIndex() != xIndex)
                    continue;

                float lowValue = e.getLow() * mAnimator.getPhaseY();
                float highValue = e.getHigh() * mAnimator.getPhaseY();
                float y = (lowValue + highValue) / 2f;

                /*float[] pts = new float[]{
                        xIndex, y
                };*/
                float[] pts = new float[]{
                        xIndex, high.getTouchYValue()
                };

                mChart.getTransformer(set.getAxisDependency()).pointValuesToPixel(pts);

                // draw the lines
                drawHighlightLines(c, pts, set);
            }
        }
    }

}
