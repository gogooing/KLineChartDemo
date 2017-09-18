package com.sanqius.loro.cjlc.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Float.NaN;

/**
 * Created by loro on 2017/3/7.
 */

public class DMIEntity {

    private ArrayList<Float> DI1s;
    private ArrayList<Float> DI2s;
    private ArrayList<Float> ADXs;
    private ArrayList<Float> ADXRs;

    private ArrayList<Float> DIHNs;
    private ArrayList<Float> DILNs;
    private ArrayList<Float> DIHNMAs;
    private ArrayList<Float> DILNMAs;
    private ArrayList<Float> DXNs;
    private ArrayList<Float> ADXNs;
    private ArrayList<Float> ADXRNs;

    /**
     * 计算DMI
     * @param kLineBeens
     * @param dIn DI的均线日单位
     * @param aDXn ADX的均线日单位
     * @param aDXRn ADXR的均线日单位
     * @param isDIMA 是否计算DI的均值，最好计算，不然太多数字为0了
     */
    public DMIEntity(List<KLineBean> kLineBeens, int dIn, int aDXn, int aDXRn, boolean isDIMA) {
        float tr = 0.0f;
        float tr1 = 0.0f;
        float tr2 = 0.0f;
        float tr3 = 0.0f;
        float nowC = 0.0f;
        float nowH = 0.0f;
        float nowL = 0.0f;

        float dmH = 0.0f;
        float dmL = 0.0f;

        float diH = 0.0f;
        float diL = 0.0f;

        float dx = 0.0f;
        float dx1 = 0.0f;
        float dx2 = 0.0f;

        float adxr = 0.0f;

        KLineBean lineBean;

        List<Float> trs = new ArrayList<>();
        DIHNs = new ArrayList<>();
        DILNs = new ArrayList<>();
        DIHNMAs = new ArrayList<>();
        DILNMAs = new ArrayList<>();
        DXNs = new ArrayList<>();
        ADXNs = new ArrayList<>();
        ADXRNs = new ArrayList<>();

        int index = aDXRn - 1;

        DI1s = new ArrayList<Float>();
        DI2s = new ArrayList<Float>();
        ADXs = new ArrayList<Float>();
        ADXRs = new ArrayList<Float>();
        //计算正常DI
        for (int i = 0; i < kLineBeens.size(); i++) {
            lineBean = kLineBeens.get(i);

            if (i == 0) {
                dmH = 0.0f;
                dmL = 0.0f;

                nowC = lineBean.close;
                tr = lineBean.high - lineBean.low;
            } else {

                dmH = lineBean.high - kLineBeens.get(i - 1).high;
                dmL = kLineBeens.get(i - 1).low - lineBean.low;

                dmH = dmH <= 0 ? 0 : dmH;
                dmL = dmL <= 0 ? 0 : dmL;

//                if (dmH >= dmL) {
//                    dmL = 0.0f;
//                } else {
//                    dmH = 0.0f;
//                }

                tr1 = lineBean.high - lineBean.low;
                tr2 = lineBean.high - nowC;
                tr3 = lineBean.low - nowC;
                nowC = lineBean.close;

                tr1 = Math.abs(tr1);
                tr2 = Math.abs(tr2);
                tr3 = Math.abs(tr3);

                trs.clear();
                trs.add(tr1);
                trs.add(tr2);
                trs.add(tr3);

                tr = Collections.max(trs);
            }


//            tr1 = lineBean.high - lineBean.low;
//            tr2 = lineBean.high - nowC;
//            tr3 = lineBean.low - nowC;
//            nowC = lineBean.close;
//
//            tr1 = Math.abs(tr1);
//            tr2 = Math.abs(tr2);
//            tr3 = Math.abs(tr3);
//
//            trs.clear();
//            trs.add(tr1);
//            trs.add(tr2);
//            trs.add(tr3);
//
//            tr = Collections.max(trs);
//            dmH = lineBean.high - nowH;
//            dmL = lineBean.low - nowL;
//            nowH = lineBean.high;
//            nowL = lineBean.low;
//
//            dmH = dmH <= 0 ? 0 : dmH;
//            dmL = dmL <= 0 ? 0 : dmL;

            diH = (dmH / tr) * 100;
            diL = (dmL / tr) * 100;

            DIHNs.add(diH);
            DILNs.add(diL);

        }

        if (isDIMA) {
            //计算均值DI
            DIHNMAs.addAll(getMAList(DIHNs, dIn, true));
            DILNMAs.addAll(getMAList(DILNs, dIn, true));
        } else {
            DIHNMAs.addAll(DIHNs);
            DILNMAs.addAll(DILNs);
        }

        //得到DX
        for (int i = 0; i < DIHNMAs.size(); i++) {
            diH = DIHNMAs.get(i);
            diL = DILNMAs.get(i);
            dx1 = Math.abs(diH - diL);
            dx2 = diH + diL;
            dx = (dx1 / dx2) * 100;

            DXNs.add(dx);
        }

        //得到ADX
        ADXNs.addAll(getMAList(DXNs, aDXn, false));

        //得到ADXR
        for (int i = 0; i < ADXNs.size(); i++) {
            if (i < index) {
                adxr = NaN;
            } else {
                adxr = (ADXNs.get(i) + ADXNs.get(i - index)) / 2;
            }
            ADXRNs.add(adxr);
        }

        DI1s.addAll(DIHNMAs);
        DI2s.addAll(DILNMAs);
        ADXs.addAll(ADXNs);
        ADXRs.addAll(ADXRNs);

    }

    /**
     * 求集合的N日均值
     *
     * @param list
     * @param n
     * @param isInclude true会计算不足N日的均值
     * @return
     */
    private List<Float> getMAList(ArrayList<Float> list, int n, boolean isInclude) {
        List<Float> mas = new ArrayList<>();
        int index = n - 1;
        float sum = 0.0f;
        float ma = 0.0f;
        int t = 0;
        for (int i = 0; i < list.size(); i++) {
            sum = getSum(i - n, i, list);
            if (i <= index) {
                t = i + 1;
            } else {
                t = n;
            }
            if (!isInclude && i <= index) {
                ma = NaN;
            } else {
                ma = sum / t;
            }
            mas.add(ma);
        }
        return mas;
    }

    /**
     * 得到数据的和
     *
     * @param a    开始位置
     * @param b    结束位置
     * @param list
     * @return
     */
    private float getSum(Integer a, Integer b, ArrayList<Float> list) {
        if (a < 0)
            a = 0;
        float sum = 0;
        for (int i = a; i <= b; i++) {
            sum += list.get(i);
        }
        return sum;
    }

    public ArrayList<Float> getDI1s() {
        return DI1s;
    }

    public ArrayList<Float> getDI2s() {
        return DI2s;
    }

    public ArrayList<Float> getADXs() {
        return ADXs;
    }

    public ArrayList<Float> getADXRs() {
        return ADXRs;
    }
}
