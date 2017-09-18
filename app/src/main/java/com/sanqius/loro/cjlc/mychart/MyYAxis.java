package com.sanqius.loro.cjlc.mychart;

import com.github.mikephil.charting.components.YAxis;

/**
 * Created by loro on 2017/2/8.
 */
public class MyYAxis extends YAxis {
    private float baseValue=Float.NaN;
    private String minValue;
    public MyYAxis() {
        super();
    }
    public MyYAxis(AxisDependency axis) {
        super(axis);
    }
    public void setShowMaxAndUnit(String minValue) {
        setShowOnlyMinMax(true);
        this.minValue = minValue;
    }
    public float getBaseValue() {
        return baseValue;
    }

    public String getMinValue(){
        return minValue;
    }
    public void setBaseValue(float baseValue) {
        this.baseValue = baseValue;
    }
}
