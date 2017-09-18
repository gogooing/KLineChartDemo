package com.sanqius.loro.cjlc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.sanqius.loro.cjlc.R;
import com.sanqius.loro.cjlc.bean.DataParse;
import com.sanqius.loro.cjlc.bean.MinutesBean;
import com.sanqius.loro.cjlc.common.ConstantTest;
import com.sanqius.loro.cjlc.mychart.MyBottomMarkerView;
import com.sanqius.loro.cjlc.mychart.MyCombinedChartX;
import com.sanqius.loro.cjlc.mychart.MyHMarkerView;
import com.sanqius.loro.cjlc.mychart.MyLeftMarkerView;
import com.sanqius.loro.cjlc.mychart.MyXAxis;
import com.sanqius.loro.cjlc.mychart.MyYAxis;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 分时图
 *
 * @author loro
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected MyCombinedChartX mChartPrice;
    protected MyCombinedChartX mChartVolume;

    //开，收，高，低，量，换，额，查，比
    protected TextView mTvOpen, mTvClose, mTvMax, mTvMin, mTvNum, mTvExchange, mTvAmount, mTvSub, mTvPercent;

    protected RelativeLayout mRlRight;
    protected RelativeLayout mRlButtom;

    protected MyXAxis xAxisPrice;
    protected MyYAxis axisRightPrice;
    protected MyYAxis axisLeftPrice;

    protected MyXAxis xAxisVolume;
    protected MyYAxis axisRightVolume;
    protected MyYAxis axisLeftVolume;

    protected SparseArray<String> stringSparseArray;
    protected DataParse mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringSparseArray = setXLabels();

        initViews();
        initDatas();
        initListener();

        getOffLineData();

        initChartPrice();
        initChartVolume();
        initChartListener();

//        setOffset();
        setShowLabels(stringSparseArray);

        initChartPriceData(mChartPrice);
        initChartVolumeData(mChartVolume);

    }

    private void initViews() {
        mTvOpen = (TextView) findViewById(R.id.main_tv_open);
        mTvClose = (TextView) findViewById(R.id.main_tv_close);
        mTvMax = (TextView) findViewById(R.id.main_tv_max);
        mTvMin = (TextView) findViewById(R.id.main_tv_min);
        mTvNum = (TextView) findViewById(R.id.main_tv_num);
        mTvExchange = (TextView) findViewById(R.id.main_tv_exchange);
        mTvAmount = (TextView) findViewById(R.id.main_tv_amount);
        mTvSub = (TextView) findViewById(R.id.main_tv_sub);
        mTvPercent = (TextView) findViewById(R.id.main_tv_percent);

        mRlRight = (RelativeLayout) findViewById(R.id.main_rl_right);
        mRlButtom = (RelativeLayout) findViewById(R.id.main_rl_buttom);

        mChartPrice = (MyCombinedChartX) findViewById(R.id.main_chart_price);
        mChartVolume = (MyCombinedChartX) findViewById(R.id.main_chart_volume);
    }

    private void initDatas() {

    }

    private void initListener() {
        mRlRight.setOnClickListener(this);
        mRlButtom.setOnClickListener(this);
    }

    private void getOffLineData() {
           /*方便测试，加入假数据*/
        mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.MINUTESURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseMinutes(object);
    }

    private void initChartPrice() {
        mChartPrice.setScaleEnabled(false);//启用图表缩放事件
        mChartPrice.setDrawBorders(true);//是否绘制边线
        mChartPrice.setBorderWidth(1);//边线宽度，单位dp
        mChartPrice.setDragEnabled(true);//启用图表拖拽事件
        mChartPrice.setScaleYEnabled(false);//启用Y轴上的缩放
        mChartPrice.setBorderColor(getResources().getColor(R.color.border_color));//边线颜色
        mChartPrice.setDescription("");//右下角对图表的描述信息
        mChartPrice.setHardwareAccelerationEnabled(true);//是否不开启硬件加速
        mChartPrice.setMinOffset(0f);//设置上下内边距
//        mChartPrice.setMinOffsetLR(0f);//设置左右内边距
        mChartPrice.setExtraOffsets(0f, 0f, 0f, 3f);//图标周围格额外的偏移量

        Legend lineChartLegend = mChartPrice.getLegend();//主要控制左下方的图例的
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例

        //x轴
        xAxisPrice = mChartPrice.getXAxis();//控制X轴的
        xAxisPrice.setDrawLabels(true);//是否显示X坐标轴上的刻度，默认是true
        xAxisPrice.setDrawAxisLine(false);//是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisPrice.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisPrice.setPosition(XAxis.XAxisPosition.BOTTOM);//把坐标轴放在上下 参数有：TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE or BOTTOM_INSIDE.
        xAxisPrice.enableGridDashedLine(10f, 10f, 0f);//绘制成虚线，只有在关闭硬件加速的情况下才能使用
        xAxisPrice.setYOffset(7f);//设置X轴刻度在Y坐标上的偏移

        //左边y
        axisLeftPrice = mChartPrice.getAxisLeft();
        axisLeftPrice.setLabelCount(5, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftPrice.setDrawLabels(true);//是否显示Y坐标轴上的刻度，默认是true
        axisLeftPrice.setDrawGridLines(false);//是否显示Y坐标轴上的刻度竖线，默认是true
        /*轴不显示 避免和border冲突*/
        axisLeftPrice.setDrawAxisLine(true);//是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        axisLeftPrice.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART); //参数是INSIDE_CHART(Y轴坐标在内部) 或 OUTSIDE_CHART(在外部（默认是这个）)
//        axisLeftPrice.setStartAtZero(false); //设置Y轴坐标是否从0开始
        axisLeftPrice.setShowOnlyMinMax(true); //参数如果为true Y轴坐标只显示最大值和最小值
        axisLeftPrice.enableGridDashedLine(10f, 10f, 0f); //虚线表示Y轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标  当setDrawGridLines为true才有用

        //右边y
        axisRightPrice = mChartPrice.getAxisRight();
        axisRightPrice.setLabelCount(5, false);//参考上面
        axisRightPrice.setDrawLabels(false);//参考上面
//        axisRightPrice.setStartAtZero(false);//参考上面
        axisRightPrice.setDrawGridLines(false);//参考上面
        axisRightPrice.setDrawAxisLine(true);//参考上面

        //y轴样式
        this.axisLeftPrice.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00");
                return mFormat.format(value);
            }
        });

    }


    private void initChartVolume() {
        mChartVolume.setScaleEnabled(false);//启用图表缩放事件
        mChartVolume.setDrawBorders(true);//是否绘制边线
        mChartVolume.setBorderWidth(1);//边线宽度，单位dp
        mChartVolume.setDragEnabled(true);//启用图表拖拽事件
        mChartVolume.setScaleYEnabled(false);//启用Y轴上的缩放
        mChartVolume.setBorderColor(getResources().getColor(R.color.border_color));//边线颜色
        mChartVolume.setDescription("");//右下角对图表的描述信息
        mChartVolume.setHardwareAccelerationEnabled(true);//是否开启硬件加速
        mChartVolume.setMinOffset(0f);//设置上下内边距
        mChartVolume.setExtraOffsets(0f, 0f, 0f, 0f);//图标周围格额外的偏移量

        Legend lineChartLegend = mChartVolume.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例

        //x轴
        xAxisVolume = mChartVolume.getXAxis();
        xAxisVolume.setEnabled(false);//是否绘制X轴的数据
//        xAxisVolume.setDrawLabels(false);
//        xAxisVolume.setDrawAxisLine(false);
//        xAxisVolume.setDrawGridLines(false);
//        xAxisVolume.enableGridDashedLine(10f, 10f, 0f);//绘制成虚线，只有在关闭硬件加速的情况下才能使用

        //左边y
        axisLeftVolume = mChartVolume.getAxisLeft();
        axisLeftVolume.setAxisMinValue(0);//设置Y轴坐标最小为多少
        axisLeftVolume.setShowOnlyMinMax(true);//参考上面
        axisLeftVolume.setDrawLabels(false);//参考上面
        axisLeftVolume.setDrawGridLines(false);//参考上面
        /*轴不显示 避免和border冲突*/
        axisLeftVolume.setDrawAxisLine(false);//参考上面

        //右边y
        axisRightVolume = mChartVolume.getAxisRight();
        axisRightVolume.setAxisMinValue(0);//参考上面
        axisRightVolume.setShowOnlyMinMax(true);//参考上面
        axisRightVolume.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);//参考上面
        axisRightVolume.setDrawLabels(true);//参考上面
        axisRightVolume.setDrawGridLines(true);//参考上面
        axisRightVolume.enableGridDashedLine(10f, 10f, 0f);//参考上面
        axisRightVolume.setDrawAxisLine(false);//参考上面

        //y轴样式
        this.axisRightVolume.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00");
                return mFormat.format(value);
            }
        });

    }

    private void initChartListener() {
        // 将K线控的滑动事件传递给交易量控件
//        mChartPrice.setOnChartGestureListener(new CoupleChartGestureListener(mChartPrice, new Chart[]{mChartVolume}));
//        // 将交易量控件的滑动事件传递给K线控件
//        mChartVolume.setOnChartGestureListener(new CoupleChartGestureListener(mChartVolume, new Chart[]{mChartPrice}));
        mChartPrice.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Highlight highlight = new Highlight(h.getXIndex(), h.getValue(), h.getDataIndex(), h.getDataSetIndex());

                float touchY = h.getTouchY() - mChartPrice.getHeight();
                Highlight h1 = mChartVolume.getHighlightByTouchPoint(h.getXIndex(), touchY);
                highlight.setTouchY(touchY);
                if (null == h1) {
                    highlight.setTouchYValue(0);
                } else {
                    highlight.setTouchYValue(h1.getTouchYValue());
                }
                mChartVolume.highlightValues(new Highlight[]{highlight});
            }

            @Override
            public void onNothingSelected() {
                mChartVolume.highlightValue(null);
            }
        });

        mChartVolume.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Highlight highlight = new Highlight(h.getXIndex(), h.getValue(), h.getDataIndex(), h.getDataSetIndex());

                float touchY = h.getTouchY() + mChartPrice.getHeight();
                Highlight h1 = mChartPrice.getHighlightByTouchPoint(h.getXIndex(), touchY);
                highlight.setTouchY(touchY);
                if (null == h1) {
                    highlight.setTouchYValue(0);
                } else {
                    highlight.setTouchYValue(h1.getTouchYValue());
                }
                mChartPrice.highlightValues(new Highlight[]{highlight});
            }

            @Override
            public void onNothingSelected() {
                mChartPrice.highlightValue(null);
            }
        });


    }


    private void initChartPriceData(MyCombinedChartX combinedChartX) {
        setMarkerViewButtom(mData, combinedChartX);

        Log.e("###", mData.getDatas().size() + "ee");
        if (mData.getDatas().size() == 0) {
            combinedChartX.setNoDataText("暂无数据");
            return;
        }
        //设置y左右两轴最大最小值
        combinedChartX.getAxisLeft().setAxisMinValue(mData.getMin());
        combinedChartX.getAxisLeft().setAxisMaxValue(mData.getMax());
        combinedChartX.getAxisRight().setAxisMinValue(mData.getPercentMin());
        combinedChartX.getAxisRight().setAxisMaxValue(mData.getPercentMax());

        //基准线
        LimitLine ll = new LimitLine(0);
        ll.setLineWidth(1f);
        ll.setLineColor(getResources().getColor(R.color.minute_jizhun));
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setLineWidth(1);
        combinedChartX.getAxisRight().addLimitLine(ll);
        combinedChartX.getAxisRight().setBaseValue(0);

        ArrayList<Entry> lineCJEntries = new ArrayList<>();
        ArrayList<Entry> lineJJEntries = new ArrayList<>();

        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0, j = 0; i < mData.getDatas().size(); i++, j++) {
            MinutesBean t = mData.getDatas().get(j);

            if (t == null) {
                lineCJEntries.add(new Entry(Float.NaN, i));
                lineJJEntries.add(new Entry(Float.NaN, i));
                continue;
            }
            if (!TextUtils.isEmpty(stringSparseArray.get(i)) &&
                    stringSparseArray.get(i).contains("/")) {
                i++;
            }
            lineCJEntries.add(new Entry(mData.getDatas().get(i).cjprice, i));
            lineJJEntries.add(new Entry(mData.getDatas().get(i).avprice, i));

            barEntries.add(new BarEntry(mData.getDatas().get(i).cjprice, i));
        }

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setLine(0, getMinutesCount(), lineCJEntries));
        sets.add(setLine(1, getMinutesCount(), lineJJEntries));
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
        LineData lineData = new LineData(getMinutesCount(), sets);
        lineData.setHighlightEnabled(false);


        //需要添加一个假的bar，才能用使用自定义的高亮
        BarDataSet set = new BarDataSet(barEntries, "");
        set.setHighlightEnabled(true);
        set.setHighLightAlpha(255);
        set.setHighLightColor(getResources().getColor(R.color.marker_line_bg));
        set.setDrawValues(false);
        set.setColor(getResources().getColor(R.color.transparent));

        BarData barData = new BarData(getMinutesCount(), set);
        barData.setHighlightEnabled(true);

        CombinedData combinedData = new CombinedData(getMinutesCount());
        combinedData.setData(barData);
        combinedData.setData(lineData);
        combinedChartX.setData(combinedData);

        combinedChartX.invalidate();//刷新图
    }

    @NonNull
    private LineDataSet setLine(int type, String[] xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawValues(false);
        if (type == 0) {
//            lineDataSetMa.setDrawFilled(true);
            lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSetMa.setColor(getResources().getColor(R.color.minute_blue));
        } else if (type == 1) {
            lineDataSetMa.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSetMa.setColor(getResources().getColor(R.color.minute_yellow));
        } else {
            lineDataSetMa.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSetMa.setColor(getResources().getColor(R.color.transparent));
        }
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetMa.setLineWidth(1f);

        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSetMa;
    }


    private void initChartVolumeData(MyCombinedChartX combinedChartX) {
        setMarkerView(mData, combinedChartX);
        combinedChartX.getAxisLeft().setAxisMaxValue(mData.getVolmax()); /*单位*/
        combinedChartX.getAxisRight().setAxisMaxValue(mData.getVolmax());
//        String unit = MyUtils.getVolUnit(mData.getVolmax());
//        String wan = getString(R.string.wan_unit);
//        String yi = getString(R.string.yi_unit);
//        int u = 1;
//        if (wan.equals(unit)) {
//            u = 4;
//        } else if (yi.equals(unit)) {
//            u = 8;
//        }
//        /*次方*/
//        combinedChartX.getAxisRight().setValueFormatter(new VolFormatter((int) Math.pow(10, u)));

        ArrayList<Entry> lineJJEntries = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0, j = 0; i < mData.getDatas().size(); i++, j++) {
            MinutesBean t = mData.getDatas().get(j);

            if (t == null) {
                barEntries.add(new BarEntry(Float.NaN, i));
                continue;
            }
            if (!TextUtils.isEmpty(stringSparseArray.get(i)) &&
                    stringSparseArray.get(i).contains("/")) {
                i++;
            }
            lineJJEntries.add(new Entry(0, i));
            barEntries.add(new BarEntry(mData.getDatas().get(i).cjnum, i));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "成交量");
//        barDataSet.setBarSpacePercent(20); //bar空隙，可以控制树状图的大小，空隙越大，树状图越窄
        barDataSet.setHighLightColor(getResources().getColor(R.color.marker_line_bg));// 设置点击某个点时，横竖两条线的颜色，就是高亮线的颜色
        barDataSet.setHighLightAlpha(255);//设置高亮线的透明度
        barDataSet.setDrawValues(false);//是否在线上绘制数值
        barDataSet.setHighlightEnabled(true);//是否启用高亮线
        barDataSet.setColor(getResources().getColor(R.color.increasing_color));//设置树状图颜色
        List<Integer> list = new ArrayList<>();
        list.add(getResources().getColor(R.color.increasing_color));
        list.add(getResources().getColor(R.color.decreasing_color));
        barDataSet.setColors(list);//可以给树状图设置多个颜色，判断条件在BarChartRenderer 类的140行以下修改了判断条件
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);//设置这些值对应哪条轴
        BarData barData = new BarData(getMinutesCount(), barDataSet);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setLine(2, getMinutesCount(), lineJJEntries));
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
        LineData lineData = new LineData(getMinutesCount(), sets);
        lineData.setHighlightEnabled(false);

        CombinedData combinedData = new CombinedData(getMinutesCount());
        combinedData.setData(barData);
        combinedData.setData(lineData);
        combinedChartX.setData(combinedData);

        combinedChartX.invalidate();
    }

    private void setMarkerViewButtom(DataParse mData, MyCombinedChartX combinedChart) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(MainActivity.this, R.layout.mymarkerview);
        MyHMarkerView hMarkerView = new MyHMarkerView(MainActivity.this, R.layout.mymarkerview_line);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(MainActivity.this, R.layout.mymarkerview);
        combinedChart.setMarker(leftMarkerView, bottomMarkerView, hMarkerView, mData);
    }

    private void setMarkerView(DataParse mData, MyCombinedChartX combinedChart) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(MainActivity.this, R.layout.mymarkerview);
        MyHMarkerView hMarkerView = new MyHMarkerView(MainActivity.this, R.layout.mymarkerview_line);
        combinedChart.setMarker(leftMarkerView, hMarkerView, mData);
    }

    public void setShowLabels(SparseArray<String> labels) {
        xAxisPrice.setXLabels(labels);
        xAxisVolume.setXLabels(labels);
    }

    public String[] getMinutesCount() {
        return new String[242];
    }

    private SparseArray<String> setXLabels() {
        SparseArray<String> xLabels = new SparseArray<>();
        xLabels.put(0, "09:30");
        xLabels.put(60, "10:30");
        xLabels.put(121, "11:30/13:00");
        xLabels.put(182, "14:00");
        xLabels.put(241, "15:00");
        return xLabels;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.main_rl_right:
                intent = new Intent();
                intent.setClass(MainActivity.this, KLineActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.main_rl_buttom:
                initChartPriceData(mChartPrice);
                initChartVolumeData(mChartVolume);
                break;
        }
    }

    /**
     * 设置量表对齐，一般自己设置好了属性，保证对齐的话就可以不用这个方法
     */
    private void setOffset() {
        float lineLeft = mChartPrice.getViewPortHandler().offsetLeft();
        float barLeft = mChartVolume.getViewPortHandler().offsetLeft();
        float lineRight = mChartPrice.getViewPortHandler().offsetRight();
        float barRight = mChartVolume.getViewPortHandler().offsetRight();
        float barBottom = mChartVolume.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
            //offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            // barChart.setExtraLeftOffset(offsetLeft);
            transLeft = lineLeft;

        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            mChartPrice.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }

  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
            //offsetRight = Utils.convertPixelsToDp(lineRight);
            //barChart.setExtraRightOffset(offsetRight);
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            mChartPrice.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        mChartVolume.setViewPortOffsets(transLeft, 5, transRight, barBottom);
    }

}
