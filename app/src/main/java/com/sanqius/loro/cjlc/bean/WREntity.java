package com.sanqius.loro.cjlc.bean;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.NaN;

/**
 * Created by loro on 2017/3/2.
 */

public class WREntity {
    private ArrayList<Float> WRs;

    /**
     * @param kLineBeens
     * @param n          几日
     */
    public WREntity(List<KLineBean> kLineBeens, int n) {
        this(kLineBeens, n, NaN);
    }

    /**
     * @param kLineBeens
     * @param n          几日
     * @param defult     不足N日时的默认值
     */
    public WREntity(List<KLineBean> kLineBeens, int n, float defult) {
        WRs = new ArrayList<Float>();
        ArrayList<Float> wRs = new ArrayList<Float>();
        float wms = 0.0f;
        int index = n - 1;
        if (kLineBeens != null && kLineBeens.size() > 0) {
            KLineBean kLineBean = kLineBeens.get(0);
            float high = kLineBean.high;
            float low = kLineBean.low;

            for (int i = 0; i < kLineBeens.size(); i++) {
                if (i > 0) {
                    if (n == 0) {
                        kLineBean = kLineBeens.get(i);
                        high = high > kLineBean.high ? high : kLineBean.high;
                        low = low < kLineBean.low ? low : kLineBean.low;
                    } else {
                        kLineBean = kLineBeens.get(i);
                        int k = i - n + 1;
                        Float[] wrs = getHighAndLowByK(k, i, (ArrayList<KLineBean>) kLineBeens);
                        high = wrs[0];
                        low = wrs[1];
                    }
                }
                if (i >= index) {
                    if (high != low) {
                        wms = ((high - kLineBean.close) / (high - low)) * 100;
                    }
                } else {
                    wms = defult;
                }
                wRs.add(wms);
            }
            for (int i = 0; i < wRs.size(); i++) {
                WRs.add(wRs.get(i));
            }
        }
    }

    private Float[] getHighAndLowByK(Integer a, Integer b, ArrayList<KLineBean> kLineBeens) {
        if (a < 0)
            a = 0;

        KLineBean kLineBean = kLineBeens.get(a);
        float high = kLineBean.high;
        float low = kLineBean.low;
        Float[] wrs = new Float[2];
        for (int i = a; i <= b; i++) {
            kLineBean = kLineBeens.get(i);
            high = high > kLineBean.high ? high : kLineBean.high;
            low = low < kLineBean.low ? low : kLineBean.low;
        }

        wrs[0] = high;
        wrs[1] = low;
        return wrs;
    }

    public ArrayList<Float> getWRs() {
        return WRs;
    }

}
