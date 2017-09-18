package com.sanqius.loro.cjlc.mychart;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.sanqius.loro.cjlc.R;

import java.text.DecimalFormat;

/**
 * Created by loro on 2017/2/8.
 */
public class MyHMarkerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private ImageView markerTv;
    private float num;
    private  DecimalFormat mFormat;
    public MyHMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mFormat=new DecimalFormat("#0.00");
        markerTv = (ImageView) findViewById(R.id.marker_tv);
    }

    public void setData(float num){
        this.num=num;
    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
    }

    public void setTvWidth(int width){
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) markerTv.getLayoutParams();
        params.width=width;
        markerTv.setLayoutParams(params);
    }

    @Override
    public int getXOffset(float xpos) {
        return 0;
    }

    @Override
    public int getYOffset(float ypos) {
        return 0;
    }
}
