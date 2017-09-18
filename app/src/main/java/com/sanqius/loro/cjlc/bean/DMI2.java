package com.sanqius.loro.cjlc.bean;

/**
 * Created by msdnet on 2017/5/3.
 */

public class DMI2 {
//
//    /**
//     * 计算 DMI
//     *
//     * @param datas
//     */
//    public static void calculateDMI(List<KLineBean> datas) {
//        //（1）计算真实波幅（TR）①当日最高价与当日最低价之间的价差。②当日最低价与上日收市价之间的价差。③当日最高价与上日收市价之间的价差。
//        //（2）计算当日动向值
//        //（3）计算14日的TR，＋DM和－DM
//        //（4）计算上升指标（＋DI）和下降指标（－DI）
//        //（5）计算动向指数（DX）
//        //（6）计算平均动向指数（ADX）
//        //（7）动向指数图的绘制
//        float tr = 0;
//        float zDm = 0;
//        float fDm = 0;
//        //计算tr,zdm,fdm
//        for (int i = 0; i < datas.size(); i++) {
//            KLineBean point = datas.get(i);
//            if (i == 0) {
//                tr = Math.abs(point.high - point.low);
//                point.tr = tr;
//                zDm = point.high;
//                fDm = point.low;
//                point.zDm = zDm;
//                point.fDm = fDm;
//
//            } else {
//                float hl = Math.abs(point.high - point.low);
//                float lc = Math.abs(point.low - datas.get(i - 1).close);
//                float hc = Math.abs(point.high - datas.get(i - 1).close);
//                tr = Math.max(hl, Math.max(hc, lc));
//                point.tr = tr;
//                float hh = point.high - datas.get(i - 1).high;
//                float ll = point.low - datas.get(i - 1).low;
//                float hhabs = Math.abs(hh);
//                float llabs = Math.abs(ll);
//                if (hh > llabs) {
//                    zDm = hh;
//                    point.zDm = zDm;
//                } else {
//                    zDm = 0;
//                    point.zDm = zDm;
//                }
//                if (ll > hhabs) {
//                    fDm = ll;
//                    point.fDm = fDm;
//                } else {
//                    fDm = 0;
//                    point.fDm = fDm;
//                }
//                if (hh > 0 && ll > 0 && hh == ll) {
//                    zDm = 0;
//                    fDm = 0;
//                    point.zDm = zDm;
//                    point.fDm = fDm;
//                }
//
//            }
//
//        }
//
//        float tr14 = 0;
//        float zDm14 = 0;
//        float fDm14 = 0;
//        //计算14日的TR，＋DM和－DM
//        for (int i = 0; i < datas.size(); i++) {
//            KLineBean point = datas.get(i);
//            tr14 += point.tr;
//            zDm14 += point.zDm;
//            fDm14 += point.fDm;
//            if (i >= 14) {
//                tr14 -= datas.get(i - 14).tr;
//                zDm14 -= datas.get(i - 14).zDm;
//                fDm14 -= datas.get(i - 14).fDm;
//                point.tr14 = tr14 / 14f;
//                point.zDm14 = zDm14 / 14f;
//                point.fDm14 = fDm14 / 14f;
//            } else {
//               /* point.tr14 = tr14/(i +1f);
//                point.zDm14 = zDm14/(i +1f);
//                point.fDm14 = fDm14/(i +1f);*/
//                point.tr14 = 6;
//                point.zDm14 = 6;
//                point.fDm14 = 6;
//
//            }
//        }
//
//        //计算上升指标（＋DI）和下降指标（－DI）
//        float zDi14 = 0;
//        float fDi14 = 0;
//        for (int i = 0; i < datas.size(); i++) {
//            KLineBean point = datas.get(i);
//            zDi14 = (point.zDm14 / point.tr14) * 100;
//            fDi14 = (point.fDm14 / point.tr14) * 100;
//            point.zDi14 = zDi14;
//            point.fDi14 = fDi14;
//        }
//        //计算动向指数（DX）
//        float dx = 0;
//        for (int i = 0; i < datas.size(); i++) {
//            KLineBean point = datas.get(i);
//            dx = (Math.abs(point.zDi14 - point.fDi14)) / (point.zDi14 + point.fDi14) * 100;
//            point.dx = dx;
//        }
//        //计算平均动向指数（ADX）
//        float adx = 0;
//        for (int i = 0; i < datas.size(); i++) {
//            KLineBean point = datas.get(i);
//            adx += point.dx;
//            if (i >= 14) {
//                adx -= datas.get(i - 14).dx;
//                point.adx = adx / 14f;
//            } else {
//                point.adx = adx / (i + 1f);
//            }
//
//        }
//        //计算平均动向指数（ADXR）
//        float adxr = 0;
//        for (int i = 0; i < datas.size(); i++) {
//            KLineBean point = datas.get(i);
//            if (i == 0) {
//                point.adxr = point.adx;
//            } else {
//                adxr = (point.adx + datas.get(i - 1).adx) / 2;
//                point.adxr = adxr;
//            }
//        }
//
//
//    }

}
