package com.sanqius.loro.cjlc.mychart;

import android.util.SparseArray;

import com.github.mikephil.charting.components.XAxis;

/**
 * Created by loro on 2017/2/8.
 */
public class MyXAxis extends XAxis {
    private SparseArray<String> labels;
    public SparseArray<String> getXLabels() {
        return labels;
    }
    public void setXLabels(SparseArray<String> labels) {
        this.labels = labels;
    }
}
