//package com.shengjuntech.im.utils;
//
//import android.app.ActivityManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.graphics.drawable.AnimationDrawable;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.media.MediaPlayer;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.telephony.TelephonyManager;
//import android.text.TextUtils;
//import android.util.Log;
//import android.util.Patterns;
//import android.widget.Toast;
//
//import com.shengjuntech.im.BuildConfig;
//import com.shengjuntech.im.Entity.BubbleEntity;
//import com.shengjuntech.im.Entity.EmotionDyna;
//import com.shengjuntech.im.Entity.FLoopShareItem;
//import com.shengjuntech.im.Entity.FontEntity;
//import com.shengjuntech.im.Entity.IMMapInfo;
//import com.shengjuntech.im.Entity.Login;
//import com.shengjuntech.im.Entity.MessageInfo;
//import com.shengjuntech.im.Entity.MessageType;
//import com.shengjuntech.im.Entity.MorePicture;
//import com.shengjuntech.im.Entity.WidgetEntity;
//import com.shengjuntech.im.R;
//import com.shengjuntech.im.global.FeatureFunction;
//import com.shengjuntech.im.global.IMCommon;
//import com.shengjuntech.im.global.IMVAR;
//import com.shengjuntech.im.interfaces.IFileDownload;
//import com.shengjuntech.im.interfaces.IUnzipFinish;
//import com.shengjuntech.im.map.IMApp;
//import com.shengjuntech.im.map.ScreenManager;
//import com.shengjuntech.im.view.VEmotionDownLoadActivity;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.UUID;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static com.shengjuntech.im.R.raw.msg;
//
////import com.lidroid.xutils.HttpUtils;
//
///**
// * Created by msdnet on 2016/2/24.
// */
//public class CommonUtil extends BaseUtil {
//
//    public static final int SENSOR_CLOSE = 0;
//    public static final int SENSOR_LOCK_SCREEN = 1;
//    public static final int SENSOR_EXIT = 2;
//
//
//    /**
//     * 防止快速点击的变量
//     */
//
//
//    private static long lastClickTime;
//
//    /**
//     * 判断SD卡是否存在
//     *
//     * @return
//     */
//    public static String getSDPath() {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//            //sdDir = Environment.getExternalStorageState();//获取跟目录
//        }
//        return sdDir.getPath();
//    }
//
//    /**
//     * 得到byScreents的值
//     *
//     * @param datas
//     * @return
//     */
//    public static int getByScreensInt(Integer[] datas) {
//        int max = getMax(datas);
//        if (max >= 32) {
//            return -1;
//        }
//
//        int byScreens = 0;
//        if (null != datas && datas.length > 0) {
//            for (int i = 0; i < datas.length; i++) {
//                if (datas[i] == 1) {
//                    byScreens += 1;
//                    continue;
//                }
//                int screen = datas[i];
//                byScreens += Math.pow(2, screen - 1);
//            }
//        }
//        return byScreens;
//    }
//
//    /**
//     * 得到byScreents的值
//     *
//     * @param scrIndex
//     * @return
//     */
//    public static int getByScreensInt(int scrIndex) {
//        if (scrIndex >= 32) {
//            return -1;
//        }
//
//        int byScreens = 0;
//        if (scrIndex == 1) {
//            byScreens += 1;
//        }else{
//            byScreens += Math.pow(2, scrIndex - 1);
//        }
//
//        return byScreens;
//    }
//
//    /**
//     * 得到byScreents的值
//     *
//     * @return
//     */
//    public static int getByScreensByAll() {
//        return getByScreensInt(ScreenManager.getInstance().getAllScreens());
//
//    }
//    /**
//     * 得到byScreents的值
//     *
//     * @return
//     */
//    public static int getByScreensInt() {
//        int scrIndex=ScreenManager.getInstance().getCurrentScreenIndex();
//        if (scrIndex >= 32) {
//            return -1;
//        }
//
//        int byScreens = 0;
//        if (scrIndex == 1) {
//            byScreens += 1;
//        }else{
//            byScreens += Math.pow(2, scrIndex - 1);
//        }
//
//        return byScreens;
//    }
//
//    /**
//     * 得到byScreents包含的屏
//     */
//    public static Integer[] getByScreensList(int byScreens, Integer[] allDatas) {
//        int max = getMax(allDatas);
//        if (max >= 32) {
//            return new Integer[0];
//        }
//
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < allDatas.length; i++) {
//            int scrIndex = allDatas[i];
//            int index = 0;
//            if (scrIndex == 1) {
//                index = 1;
//            } else {
//                index = (int) Math.pow(2, scrIndex - 1);
//            }
//            if ((byScreens & index) != 0) {
//                list.add(scrIndex);
//            }
//        }
//
//        Integer[] screens = new Integer[list.size()];
//        for (int j = 0; j < list.size(); j++) {
//            screens[j] = list.get(j);
//        }
//        return screens;
//    }
//
//    /**
//     * 判断byScreents是否包含scrIndex
//     */
//    public static boolean isByScreens(int byScreens, int scrIndex) {
//        int index;
//        if (scrIndex < 1) {
//            index = 1;
//        } else {
//            index = (int) Math.pow(2, scrIndex - 1);
//        }
//
//        if ((byScreens & index) != 0) {
//            return true;
//        }
//        return false;
//    }
//    /**
//     * 修改byScreens值，存在就删除，不存在就不管
//     * @param byScreens
//     * @param scrIndex
//     * @return
//     */
//    public static int updateByScreensDeleteScrIndex(int byScreens,int scrIndex){
//        Integer[] integers=getByScreensList(byScreens, ScreenManager.getInstance().getAllScreens());
//        List<Integer> lists=new ArrayList<>();
//        for(int i=0;i<integers.length;i++){
//            if(integers[i]!=scrIndex){
//                lists.add(integers[i]);
//            }
//        }
//        integers=lists.toArray(new Integer[lists.size()]);
//
//        int byScr=getByScreensInt(integers);
//        return byScr;
//    }
//
//    /**
//     * 修改byScreens值，不存在就添加上，存在就不管
//     * @param allByScreens
//     * @param byScreen
//     * @return
//     */
//    public static int updateByScreens(int allByScreens,int byScreen){
//        Integer[] integers=getByScreensList(allByScreens, ScreenManager.getInstance().getAllScreens());
//        Integer[] byIntegers=getByScreensList(byScreen, ScreenManager.getInstance().getAllScreens());
//        List<Integer> lists=new ArrayList<>();
//
//        boolean isHave=false;
//        for(int i=0;i<integers.length;i++){
//            lists.add(integers[i]);
//            for(int j=0;j<byIntegers.length;j++){
//
//            }
//
//            if(integers[i]==byScreen){
//                isHave=true;
//            }
//        }
//        if(!isHave){
//            //原本的byScreens值不包含scrIndex，就添加上
//            lists.add(byScreen);
//        }
//        integers=lists.toArray(new Integer[lists.size()]);
//
//        int byScr=getByScreensInt(integers);
//        return byScr;
//    }
//
//    /**
//     * 取出数组中的最大值
//     */
//    public static int getMax(Integer[] arr) {
//        if(null==arr||arr.length<1){
//            return 0;
//        }
//        int max = arr[0];
//        for (int i = 1; i < arr.length; i++) {
//            if (arr[i] > max) {
//                max = arr[i];
//            }
//        }
//        return max;
//    }
//
//    /**
//     * 文件，文件夹删除操作
//     *
//     * @param filepath
//     */
//    public static void delete(String filepath) {
//        File file = new File(filepath);
//        delete(file);
//    }
//
//    public static void delete(File file) {
//        if (file.isFile()) {
//            file.delete();
//            return;
//        }
//
//        if (file.isDirectory()) {
//            File[] childFiles = file.listFiles();
//            if (childFiles == null || childFiles.length == 0) {
//                file.delete();
//                return;
//            }
//
//            for (int i = 0; i < childFiles.length; i++) {
//                delete(childFiles[i]);
//            }
//            file.delete();
//        }
//    }
//
//    //读取文本文件中的内容
//    public static String ReadTxtFile(String strFilePath) {
//        String path = strFilePath;
//        String content = ""; //文件内容字符串
//        //打开文件
//        File file = new File(path);
//        //如果path是传递过来的参数，可以做一个非目录的判断
//        if (file.isDirectory()) {
//            Log.d("TestFile", "The File doesn't not exist.");
//        } else {
//            try {
//                InputStream instream = new FileInputStream(file);
//                if (instream != null) {
//                    InputStreamReader inputreader = new InputStreamReader(instream);
//                    BufferedReader buffreader = new BufferedReader(inputreader);
//                    String line;
//                    //分行读取
//                    while ((line = buffreader.readLine()) != null) {
//                        content += line + "\n";
//                    }
//                    instream.close();
//                }
//            } catch (java.io.FileNotFoundException e) {
//                Log.d("TestFile", "The File doesn't not exist.");
//            } catch (IOException e) {
//                Log.d("TestFile", e.getMessage());
//            }
//        }
//        return content;
//    }
//
//
//    /**
//     * 防止快速点击按钮
//     *
//     * @return
//     */
//    public static boolean isFastDoubleClick() {
//        long time = System.currentTimeMillis();
//        long timeD = time - lastClickTime;
//        if (0 < timeD && timeD < 800) {
//            return true;
//        }
//        lastClickTime = time;
//        return false;
//    }
//
//    /**
//     * 防止快速点击按钮
//     *
//     * @param desTime 目标时间 （毫秒）
//     * @return
//     */
//    public static boolean isFastDoubleClick(long desTime) {
//        long time = System.currentTimeMillis();
//        long timeD = time - lastClickTime;
//        if (0 < timeD && timeD < desTime) {
//            return true;
//        }
//        lastClickTime = time;
//        return false;
//    }
//
//    /*
// * Java文件操作 获取文件扩展名
// *
// */
//    public static String getExtensionName(String filename) {
//        if ((filename != null) && (filename.length() > 0)) {
//            int dot = filename.lastIndexOf('.');
//            if ((dot > -1) && (dot < (filename.length() - 1))) {
//                return filename.substring(dot + 1);
//            }
//        }
//        return filename;
//    }
//
//    /*
//     * Java文件操作 获取不带扩展名的文件名
//     */
//    public static String getFileNameNoEx(String filename) {
//        if ((filename != null) && (filename.length() > 0)) {
//            int dot = filename.lastIndexOf('.');
//            if ((dot > -1) && (dot < (filename.length()))) {
//                return filename.substring(0, dot);
//            }
//        }
//        return filename;
//    }
//
//
//    /**
//     * 获取URL文件的名称
//     *
//     * @param url
//     * @return
//     */
//    public static String getFileNameFromUrl(String url) {
//        if ((url != null) && (url.length() > 0)) {
//            int dot = url.lastIndexOf('/');
//            if ((dot > -1) && (dot < (url.length()))) {
//                return url.substring(dot + 1, url.length());
//            }
//        }
//        return url;
//    }
//
//    /**
//     * 获取网络路径的MD5
//     *
//     * @param path
//     * @return
//     */
//    public static String getFileMD5NAME(String path) {
//        if (path != null && !path.equals("")) {
//            String filename = getFileNameFromUrl(path);
//            filename = getFileNameNoEx(filename);
//            return filename;
//        }
//        return path;
//    }
//
//    /**
//     * 去除s_匹配本地缓存图片
//     *
//     * @param name
//     * @return
//     */
//    public static String getLocalFileName(String name) {
//        if ((name != null) && (name.length() > 0)) {
//            int dot = name.lastIndexOf('_');
//            if ((dot > -1) && (dot < (name.length()))) {
//                return name.substring(dot + 1, name.length());
//            }
//        }
//        return name;
//    }
//
//    /**
//     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
//     */
//    public static int px2dip(Context context, float pxValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }
//
//    /**
//     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
//     */
//    public static int dip2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    /***
//     * 获取气泡文件中的配置文件
//     *
//     * @param id
//     * @return
//     */
//    public static String getBubblePath(String id) {
//        String path = "";
//        if (checkSDCard()) {
//            //path=getSDPath()+FeatureFunction.SHARE_BUBBLE+"/"+id+"/"+"config.json";
//            path = IMVAR.getBubblePath() + id + "/" + "config.json";
//        }
//        return path;
//    }
//
//    /***
//     * 获取字体样式文件中的配置文件
//     *
//     * @param id
//     * @return
//     */
//    public static String getfontPath(String id) {
//        String path = "";
//        if (checkSDCard()) {
////            path=getSDPath()+FeatureFunction.SHARE_FONT+"/"+id+"/"+"config.json";
//            path = IMVAR.getFontPath() + id + "/" + "config.json";
//        }
//
//        return path;
//    }
//
//    /***
//     * 获取字体样式文件中的配置文件
//     *
//     * @param id
//     * @return
//     */
//    public static String getAdPath(String id) {
//        String path = "";
//        if (checkSDCard()) {
////            path=getSDPath()+FeatureFunction.SHARE_FONT+"/"+id+"/"+"config.json";
//            path = IMVAR.getAdPath() + id + "/" + id + "/" + "config.json";
//        }
//
//        return path;
//    }
//
//
//    /**
//     * 根据id来获取字体样式包中的ttf文件路径
//     *
//     * @param id
//     * @return
//     */
//    public static String getFontTTFPath(String id) {
//        String ttfPath = "";
//        if (checkSDCard()) {
////            String path=getSDPath()+FeatureFunction.SHARE_FONT+"/"+id+"/"+id+".ttf";
//            String path = IMVAR.getFontPath() + id + "/" + id + ".ttf";
//            if (new File(path).isFile()) {
//                ttfPath = path;
//            }
//        }
//        return ttfPath;
//    }
//
//    /**
//     * 获取挂件动画的文件夹
//     *
//     * @param id
//     * @return
//     */
//    public static String getWidgetAnimalPath(String id) {
//        String path = "";
//        if (checkSDCard()) {
//            //path=getSDPath()+FeatureFunction.SHARE_WIDGET+"/"+id+"/"+"aio_file";
//            path = IMVAR.getWidgetPath() + "/" + id + "/" + "aio_file";
//        }
//        return path;
//    }
//
//    /**
//     * 获取挂件静态图片
//     *
//     * @param id
//     * @return
//     */
//    public static Drawable getWidgetStaticPath(String id, int staDensityDpi) {
//        String path = "";
//        Drawable drawable = null;
//        if (checkSDCard()) {
////            path=getSDPath()+FeatureFunction.SHARE_WIDGET+"/"+id+"/"+"aio_50.png";
//            path = IMVAR.getWidgetPath() + "/" + id + "/" + "aio_50.png";
//            if (isPic(path)) {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inTargetDensity = staDensityDpi;
//                options.inPreferredConfig = Bitmap.Config.RGB_565;
//                options.inScaled = true;
//                options.inDensity = staDensityDpi;
//                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
//                if (bitmap != null) {
//                    drawable = new BitmapDrawable(bitmap);
//                    bitmap = null;
//                }
//            }
//        }
//        return drawable;
//    }
//
//    /**
//     * 获取挂件静态图片
//     *
//     * @param id
//     * @return
//     */
//    public static String getWidgetStaticPath(String id) {
//        String path = "";
//        Drawable drawable = null;
//        if (checkSDCard()) {
//            //path=getSDPath()+FeatureFunction.SHARE_WIDGET+"/"+id+"/"+"aio_50.png";
//            path = IMVAR.getWidgetPath() + id + "/" + "aio_50.png";
//
//        }
//        return path;
//    }
//
//
//    /***
//     * 获取气泡文件文字中的图片
//     *
//     * @param id
//     * @return
//     */
//    public static String getBubblePicPath(String id) {
//        String path = "";
//        if (checkSDCard()) {
////            path=getSDPath()+FeatureFunction.SHARE_BUBBLE+"/"+id+"/"+"static/aio_user_bg_nor.9.png";
//            path = IMVAR.getBubblePath() + id + "/" + "static/aio_user_bg_nor.9.png";
//        }
//        return path;
//    }
//
//    /***
//     * 获取气泡文件图片中的图片
//     *
//     * @param id
//     * @return
//     */
//    public static String getBubblePicPathForPic(String id) {
//        String path = "";
//        if (checkSDCard()) {
//            path = IMVAR.getBubblePath() + id + "/" + "static/aio_user_pic_nor.9.png";
//        }
//        return path;
//    }
//
//    /***
//     * 获取广告文件图片中的图片
//     *
//     * @param id
//     * @return
//     */
//    public static String getAdPicPathForPic(String id, String file) {
//        String path = "";
//        if (checkSDCard()) {
//            path = IMVAR.getAdPath() + id + "/" + id + "/" + file;
//        }
//        return path;
//    }
//
//    /***
//     * 获取广告文件图片中的图片是否存在
//     *
//     * @param id
//     * @return
//     */
//    public static boolean getAdPicPathForPicExists(String id, String file) {
//        String path = "";
//        if (checkSDCard()) {
//            path = IMVAR.getAdPath() + id + "/" + id + "/" + file;
//        }
//        if (new File(path).exists()) {
//            return true;
//        }
//        return false;
//    }
//
//
//    /**
//     * 根据id获取气泡文件中静态图
//     *
//     * @param id
//     * @return
//     */
//    public static String getBubbleStaticPath(String id) {
//        String path = "";
//        if (checkSDCard()) {
//            path = IMVAR.getBubblePath() + id + "/" + "static/chat_bubble_thumbnail.png";
//        }
//        return path;
//    }
//
//    /**
//     * 判断SD卡是否存在
//     *
//     * @return
//     */
//    public static boolean checkSDCard() {
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
//            return true;
//        else
//            return false;
//    }
//
//
//    public interface AnimationDrawableCallBack {
//
//        void onResourceReady(AnimationDrawable animationDrawable);
//    }
//
//    /**
//     * @param id
//     * @param childFileName
//     * @param time
//     * @param staDensityDpi 自定义标准dpi
//     * @return
//     */
//    public static void getBullerAnimation(final String id, final String childFileName, final int time,
//                                          final int staDensityDpi, final AnimationDrawableCallBack callBack) {
//
//        new Thread() {
//            public void run() {
//                AnimationDrawable animationDrawable = new AnimationDrawable();
//                Drawable drawable = null;
//                if (checkSDCard()) {
//                    String path = IMVAR.getBubblePath() + id + "/" + childFileName;
//                    File file = new File(path);
//                    if (file.isDirectory()) {
//                        File[] files = file.listFiles();
//                        for (int i = 0; i < files.length; i++) {
//                            String childPath = files[i].getAbsolutePath();
//                            if (isPic(childPath)) {
//                                Bitmap bitmap = BitmapFactory.decodeFile(childPath);
//                                if (bitmap != null) {
//                                    bitmap.setDensity(staDensityDpi);
//                                    drawable = new BitmapDrawable(bitmap);
//                                }
//                                if (drawable != null) animationDrawable.addFrame(drawable, time);
//                            }
//                        }
//                    }
//                }
//                if (drawable != null) drawable = null;
//                callBack.onResourceReady(animationDrawable);
//            }
//
//            ;
//        }.start();
//
//    }
//
//    /***
//     * 获取挂件的动画
//     *
//     * @param fid
//     * @param time
//     * @param staDensityDpi
//     * @return
//     */
//    private static HashMap<String, List<Bitmap>> mWidgetDrawableHis = new HashMap<String, List<Bitmap>>();
//
//    public static void getPandentAnimation(final String fid, final int time, final int staDensityDpi,
//                                           final AnimationDrawableCallBack callBack) {
//        if (fid == null || fid.equals("0")) {
//            callBack.onResourceReady(null);
//            return;
//        }
//        new Thread() {
//            public void run() {
//                List<Bitmap> drawList = mWidgetDrawableHis.get(fid);
//                AnimationDrawable animationDrawable = new AnimationDrawable();
//                Drawable drawable = null;
//                Bitmap bitmap = null;
//                if (drawList == null) {
//                    drawList = new ArrayList<Bitmap>();
//                    if (checkSDCard()) {
//                        String path = IMVAR.getWidgetPath() + fid + "/" + "aio_file";
//                        File file = new File(path);
//                        if (file.isDirectory()) {
//                            File[] files = file.listFiles();
//
//                            for (int i = 0; i < files.length; i++) {
//                                String childPath = files[i].getAbsolutePath();
//                                if (isPic(childPath)) {
//                                    BitmapFactory.Options options = new BitmapFactory.Options();
//                                    options.inTargetDensity = staDensityDpi;
//                                    options.inPreferredConfig = Bitmap.Config.RGB_565;
//                                    options.inScaled = true;
//                                    options.inDensity = staDensityDpi;
//                                    bitmap = BitmapFactory.decodeFile(childPath, options);
//                                    if (bitmap != null) {
//                                        drawable = new BitmapDrawable(bitmap);
//                                        //bitmap = null;
//                                        drawList.add(bitmap);
//                                    }
//                                    if (drawable != null) {
//                                        animationDrawable.addFrame(drawable, time);
//                                    }
//                                }
//                            }
//                            mWidgetDrawableHis.put(fid, drawList);
//                        }
//                    }
//                    if (drawable != null) drawable = null;
//                } else {
//                    for (int i = 0; i < drawList.size(); i++) {
//                        bitmap = drawList.get(i);
//                        if (bitmap != null) {
//                            drawable = new BitmapDrawable(bitmap);
//                        }
//                        if (drawable != null) {
//                            animationDrawable.addFrame(drawable, time);
//                        }
//                    }
//                }
//
//                callBack.onResourceReady(animationDrawable);
//            }
//
//            ;
//        }.start();
//    }
//
//
//    private static Bitmap toScale(Bitmap bitmap, float size) {
//        Matrix matrix = new Matrix();
//        matrix.postScale(size, size); //长和宽放大缩小的比例
//        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        return resizeBmp;
//    }
//
//    /**
//     * 判断是否属于图片格式
//     *
//     * @param path
//     * @return
//     */
//    public static boolean isPic(String path) {
//        boolean result = false;
//        if (path != null && !path.equals("")) {
//            String rex = getExtensionName(path);
//            String extension = "";
//            if (rex.length() > 3) {
//                extension = rex.substring(0, 3);
//            } else {
//                extension = rex;
//            }
//            if (extension.equalsIgnoreCase("PNG") || extension.equalsIgnoreCase("JPG") || extension.equalsIgnoreCase("GIF")) {
//                return true;
//            }
//            if (rex.length() > 4) {
//                extension = rex.substring(0, 4);
//            }
//            if (extension.equalsIgnoreCase("JPEG")) {
//                return true;
//            }
//        }
//        return result;
//
//    }
//
//    /**
//     * 判断是否属于gif后缀
//     *
//     * @param path
//     * @return
//     */
//    public static boolean isGif(String path) {
//        boolean result = false;
//        if (path != null && !path.equals("")) {
//            String rex = getExtensionName(path);
//            if (rex.length() > 3) {
//                rex = rex.substring(0, 3);
//            }
//            if (rex.equalsIgnoreCase("GIF")) {
//                result = true;
//            }
//        }
//        return result;
//
//    }
//
//    /**
//     * 判断是否属于gif后缀
//     *
//     * @param path
//     * @return
//     */
//    public static boolean isPNG(String path) {
//        boolean result = false;
//        if (path != null && !path.equals("")) {
//            String rex = getExtensionName(path);
//            if (rex.length() > 3) {
//                rex = rex.substring(0, 3);
//            }
//            if (rex.equalsIgnoreCase("PNG")) {
//                result = true;
//            }
//        }
//        return result;
//
//    }
//
//
//    /**
//     * 获取当前SDK的版本
//     *
//     * @return
//     */
//    public static int getVersionSDK() {
//        return Integer.parseInt(Build.VERSION.SDK);
//    }
//
//
//    /**
//     * 当前SDK版本是否大于19
//     *
//     * @return
//     */
//    public static boolean isSDK_KITKAT() {
//        if (getVersionSDK() >= Build.VERSION_CODES.KITKAT) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 异步下载挂件zip文件
//     *
//     * @param fid
//     */
//    public static List<String> widgetDownLoadList = new ArrayList<String>();
//
//    public static boolean hasWidget(final String widgetID) {
//        if (widgetID != null && !widgetID.equals("") && !widgetID.equals("0")) {
//            if (!new File(CommonUtil.getWidgetAnimalPath(widgetID)).isDirectory()) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    //下載掛件
//    public static boolean loadWidgetZip(final String dwUrl, final String widgetID, final IFileDownload callback) {
//        if (widgetID == null || widgetID.equals("")) {
//            //不合法
//            return false;
//        }
//        if (widgetDownLoadList.contains(widgetID)) {
//            //正在下載
//            return false;
//        }
//        widgetDownLoadList.add(widgetID);
//        //HttpUtils httpUtils = new HttpUtils();
//        String tmpURl = dwUrl;
//        if (tmpURl == null || tmpURl.equals("")) {
//            tmpURl = IMVAR.getWidgetZipDownUrl(widgetID);
//        }
//        final String downURL = tmpURl;//
//
//        final String saveDir = IMVAR.getWidgetPath();
//        final String saveFile = saveDir + widgetID + ".zip";
//        final ZipExtractorTask task = new ZipExtractorTask(saveFile, saveDir, getContext(), true, new WidgetEntity(widgetID));
//        final String key = widgetID;
//        final DownloadHandler downloadHandler = new DownloadHandler() {
//            @Override
//            public void handlerSuccess(String weburl, String filepath, Object... params) {
//                String sKey = (String) params[0];
//                if (widgetDownLoadList.contains(widgetID))
//                    widgetDownLoadList.remove(widgetID);
//                task.execute();
//            }
//
//            @Override
//            public void handlerFailure(String weburl, String filepath, Object... params) {
//                String sKey = (String) params[0];
//                if (widgetDownLoadList.contains(widgetID))
//                    widgetDownLoadList.remove(widgetID);
//            }
//        };
//        DownloadUtil.downFile(downloadHandler, key, downURL, saveFile, callback);
//
////        final Handler h = new Handler();
////        httpUtils.download(downUrl,
////                path,
////                false, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
////                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
////                new RequestCallBack<File>() {
////
////                    @Override
////                    public void onStart() {
////                        if (callback != null) {
////                            h.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    callback.loadstart(downUrl, path,widgetID);
////                                }
////                            });
////                        }
////                    }
////
////                    @Override
////                    public void onLoading(final long total, final long current, final boolean isUploading) {
////                        if (callback != null) {
////
////                            h.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    callback.loadstart(downUrl, path,widgetID,total,current,isUploading);
////                                }
////                            });
////                        }
////                    }
////
////                    @Override
////                    public void onSuccess(ResponseInfo<File> responseInfo) {
////                        if(widgetDownLoadList.contains(widgetID))
////                            widgetDownLoadList.remove(widgetID);
////                        task.execute();
////                        if (callback != null) {
////
////                            h.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    callback.loadsucc(downUrl, path,widgetID);
////                                }
////                            });
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(HttpException error, String msg)
////                    {
////                        if(widgetDownLoadList.contains(widgetID))
////                            widgetDownLoadList.remove(widgetID);
////                        if (callback != null) {
////                            h.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    callback.loadfail(downUrl, path,widgetID);
////                                }
////                            });
////                        }
////                    }
////                });
//        return true;
//    }
//
//
//    public static List<String> bubbleDownLoadList = new ArrayList<String>();
//    public static List<String> adDownLoadList = new ArrayList<String>();
//
//    public static boolean hasBubble(final String bubbleID) {
//        if (bubbleID != null && !bubbleID.equals("") && !bubbleID.equals("0")) {
//            if (!new File(CommonUtil.getBubblePath(bubbleID)).isFile()) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    //下載氣泡
//    public static boolean loadBubbleZip(final String dwUrl, final String bubbleID, final IFileDownload callback) {
//        if (bubbleID == null || bubbleID.equals("")) {
//            //不合法
//            return false;
//        }
//        if (bubbleDownLoadList.contains(bubbleID)) {
//            //正在下載
//            return false;
//        }
//        bubbleDownLoadList.add(bubbleID);
//        String tmpURl = dwUrl;
//        if (tmpURl == null || tmpURl.equals("")) {
//            tmpURl = IMVAR.getBubbleZipDownUrl(bubbleID);
//        }
//
//        final String downURL = tmpURl;//
//        final String saveDir = IMVAR.getBubblePath();
//        final String saveFile = saveDir + bubbleID + ".zip";
//        final ZipExtractorTask task = new ZipExtractorTask(saveFile, saveDir, getContext(), true, new BubbleEntity(bubbleID));
//
//        final String key = bubbleID;
//        final DownloadHandler downloadHandler = new DownloadHandler() {
//            @Override
//            public void handlerSuccess(String weburl, String filepath, Object... params) {
//                String sKey = (String) params[0];
//                if (bubbleDownLoadList.contains(bubbleID))
//                    bubbleDownLoadList.remove(bubbleID);
//                task.execute();
//            }
//
//            @Override
//            public void handlerFailure(String weburl, String filepath, Object... params) {
//                String sKey = (String) params[0];
//                if (bubbleDownLoadList.contains(bubbleID))
//                    bubbleDownLoadList.remove(bubbleID);
//            }
//        };
//        DownloadUtil.downFile(downloadHandler, key, downURL, saveFile, callback);
////        final Handler h = new Handler();
////            HttpUtils httpUtils=new HttpUtils();
////        httpUtils.download(downUrl,
////                path,
////                false, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
////                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
////                new RequestCallBack<File>() {
////
////                    @Override
////                    public void onStart() {
////                        if (callback != null) {
////
////                            h.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    callback.loadstart(downUrl, path,bubbleID);
////                                }
////                            });
////                        }
////                    }
////
////                    @Override
////                    public void onLoading(final long total, final long current, final boolean isUploading) {
////
////                        if (callback != null) {
////
////                            h.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    callback.loadstart(downUrl, path,bubbleID,total,current,isUploading);
////                                }
////                            });
////                        }
////                    }
////
////                    @Override
////                    public void onSuccess(ResponseInfo<File> responseInfo) {
////                        if(bubbleDownLoadList.contains(bubbleID))
////                            bubbleDownLoadList.remove(bubbleID);
////                        task.execute();
////                        if (callback != null) {
////
////                            h.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    callback.loadsucc(downUrl, path,bubbleID);
////                                }
////                            });
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(HttpException error, String msg)
////                    {
////                        if(bubbleDownLoadList.contains(bubbleID))
////                            bubbleDownLoadList.remove(bubbleID);
////                        if (callback != null) {
////                            h.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    callback.loadfail(downUrl, path,bubbleID);
////                                }
////                            });
////                        }
////                    }
////                });
//        return true;
//    }
//
//
//    public static List<String> fontDownLoadList = new ArrayList<String>();
//
//    public static boolean hasFont(final String fontID) {
//
//        if (fontID != null && !fontID.equals("") && !fontID.equals("0")) {
//            if (!new File(CommonUtil.getfontPath(fontID)).isFile()) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    //下載字體
//    public static boolean loadFontZip(final String dwUrl, final String fontID, final IFileDownload callback) {
//        if (fontID == null || fontID.equals("")) {
//            //不合法
//            return false;
//        }
//        final String key = fontID;
//        String tmpURl = dwUrl;
//        if (tmpURl == null || tmpURl.equals("")) {
//            tmpURl = IMVAR.getFontZipDownUrl(fontID);
//        }
//        final String downURL = tmpURl;//
//        final String saveDir = IMVAR.getFontPath();
//        final String saveFile = saveDir + fontID + ".zip";
//
//        if (!fontDownLoadList.contains(fontID)) {
//            fontDownLoadList.add(fontID);
//            final ZipExtractorTask task = new ZipExtractorTask(saveFile, saveDir, getContext(), true, new FontEntity(fontID));
//
//
//            final DownloadHandler downloadHandler = new DownloadHandler() {
//                @Override
//                public void handlerSuccess(String weburl, String filepath, Object... params) {
//                    String sKey = (String) params[0];
//                    if (fontDownLoadList.contains(fontID))
//                        fontDownLoadList.remove(fontID);
//                    task.execute();
//                }
//
//                @Override
//                public void handlerFailure(String weburl, String filepath, Object... params) {
//                    String sKey = (String) params[0];
//                    if (fontDownLoadList.contains(fontID))
//                        fontDownLoadList.remove(fontID);
//                }
//            };
//            DownloadUtil.downFile(downloadHandler, key, downURL, saveFile, callback);
//        } else {
//            DownloadUtil.downFile(null, key, downURL, saveFile, callback);
//        }
//
//        return true;
//    }
//
//    public static List<String> EmotionDownLoadList = new ArrayList<String>();
//
//    private static class unzipEmotionComplete implements IUnzipFinish {
//        private EmotionDyna emotionDyna;
//
//        public unzipEmotionComplete(EmotionDyna emotionDyna) {
//            this.emotionDyna = emotionDyna;
//        }
//
//        @Override
//        public void uzipfinished(String key, Object... params) {
//            IMCommon.sendBroadcast(VEmotionDownLoadActivity.ACTION_EMOTION_DOWN_COMPLETE, this.emotionDyna);
//        }
//    }
//
//
//    /**
//     * 我的动态表情下载
//     *
//     * @param context
//     * @param dwUrl
//     * @param entity
//     * @param callback
//     * @return
//     */
//    public static boolean loadEmotionZip(Context context, final String dwUrl, final EmotionDyna entity, final IFileDownload callback) {
//
//        final String zipPath = IMVAR.getEmotionPath() + entity.getId();
//        final String path = IMVAR.getEmotionPath() + entity.getId() + ".zip";
//
//        if (!EmotionDownLoadList.contains(entity.getId())) {
//            final ZipExtractorTask task = new ZipExtractorTask(context, path, zipPath, dwUrl, new unzipEmotionComplete(entity));
//
//
//            final DownloadHandler downloadHandler = new DownloadHandler() {
//                @Override
//                public void handlerSuccess(String weburl, String filepath, Object... params) {
//                    if (EmotionDownLoadList.contains(entity.getId()))
//                        EmotionDownLoadList.remove(entity.getId());
//                    task.execute();
//                }
//
//                @Override
//                public void handlerFailure(String weburl, String filepath, Object... params) {
//                    if (EmotionDownLoadList.contains(entity.getId()))
//                        EmotionDownLoadList.remove(entity.getId());
//                }
//            };
//            EmotionDownLoadList.add(entity.getId());
//            DownloadUtil.downFile(downloadHandler, entity.getId(), dwUrl, path, callback);
//        } else {
//            DownloadUtil.downFile(null, entity.getId(), dwUrl, path, callback);
//        }
//
//
//        return true;
//    }
//
//    //下載广告
//    public static boolean loadAdZip(Context context, final String dwUrl, final String adID, final IFileDownload callback, IUnzipFinish unzip) {
//        final String zipPath = IMVAR.getAdPath() + adID;
//        final String path = IMVAR.getAdPath() + adID + ".zip";
//
//        if (!EmotionDownLoadList.contains(adID)) {
//            final ZipExtractorTask task = new ZipExtractorTask(context, path, zipPath, dwUrl, unzip);
//
//
//            final DownloadHandler downloadHandler = new DownloadHandler() {
//                @Override
//                public void handlerSuccess(String weburl, String filepath, Object... params) {
//                    if (EmotionDownLoadList.contains(adID))
//                        EmotionDownLoadList.remove(adID);
//                    task.execute();
//                }
//
//                @Override
//                public void handlerFailure(String weburl, String filepath, Object... params) {
//                    if (EmotionDownLoadList.contains(adID))
//                        EmotionDownLoadList.remove(adID);
//                }
//            };
//            EmotionDownLoadList.add(adID);
//            DownloadUtil.downFile(downloadHandler, adID, dwUrl, path, callback);
//        } else {
//            DownloadUtil.downFile(null, adID, dwUrl, path, callback);
//        }
//
//
//        return true;
//    }
//
//
//    public static boolean isLoadingEmotionZip(final EmotionDyna entity) {
//
//        final String zipPath = IMVAR.getEmotionPath() + entity.getId();
//        final String path = IMVAR.getEmotionPath() + entity.getId() + ".zip";
//
//        if (EmotionDownLoadList.contains(entity.getId())) {
//            return true;
//        }
//
//
//        return false;
//    }
//
//    /**
//     * 相册下载
//     *
//     * @param context
//     * @param dwUrl
//     * @param downloadHandler
//     * @param callback
//     */
//    public static void downLoadCameraPic(Context context, final String dwUrl, DownloadHandler downloadHandler, IFileDownload callback) {
//        String picName = getFileNameFromUrl(dwUrl);
//        String path = IMVAR.getCaneraPath() + "/" + picName;
//        if (new File(path).isFile()) {
//            Toast.makeText(context, context.getString(R.string.save_pic_exist), Toast.LENGTH_SHORT).show();
//        } else {
//            DownloadUtil.downFile(downloadHandler, null, dwUrl, path, callback);
//        }
//    }
//
////    public static void showTextEmotionPNG(Context context,TextView textView, String emotionText) {
////        SpannableStringBuilder sb = getSPBuilderPNG(context, emotionText);
////        textView.setText(sb);
////    }
////
////    public static void showTextEmotionGIF(Context context,TextView textView, String emotionText, int maxGifCount) {
////        SpannableStringBuilder sb = getSPBuilderGIF(context, textView, emotionText, maxGifCount);
////        textView.setText(sb);
////    }
//
////    /**
////     * @param textContent
////     * @return SpannableStringBuilder sb
////     */
////    public static SpannableStringBuilder getSPBuilderPNG(Context context, final String textContent) {
////
////        SpannableStringBuilder sb = new SpannableStringBuilder(textContent);
////        EmotionList mEmotionList = IMApp.getInstance().getEmotionList();
////        if (mEmotionList == null) {
////            return sb;
////        }else{
////
////        }
////        //String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
////        String regex = "(\\[)[\u4e00-\u9fa5a-zA-Z]{1,}(\\])";//(\\[(.)\\])";
////        Pattern p = Pattern.compile(regex);
////        Matcher m = p.matcher(textContent);
////        while (m.find()) {
////            String tempText = m.group();
////            Emotion emotion = mEmotionList.GetEmotion(tempText);
////            try {
////                //PNG
////                String png = emotion.GetFilePath();
////                sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open(png))), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////
////            } catch (Exception e) {
////
////                e.printStackTrace();
////            }
////        }
////        return sb;
////    }
//
////    public static SpannableStringBuilder getSPBuilderGIF(Context context, final TextView gifTextView, String textContent, int maxGifCount) {
////        SpannableStringBuilder sb = new SpannableStringBuilder(textContent);
////        EmotionList mEmotionList = IMApp.getInstance().getEmotionList();
////        if (mEmotionList == null) {
////            return sb;
////        }
////        //String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
////        String regex = "(\\[)[\u4e00-\u9fa5a-zA-Z]{1,}(\\])";//(\\[(.)\\])";
////        Pattern p = Pattern.compile(regex);
////        Matcher m = p.matcher(textContent);
////        Matcher mm = p.matcher(textContent);
////        Boolean beShowGifFlag = true;
////        int nCount = 0;
////        while (mm.find()) {
////            nCount += 1;
////        }
////        if (nCount > maxGifCount) {
////            return getSPBuilderPNG(context, textContent);
////        } else {
////            while (m.find()) {
////                String tempText = m.group();
////                Emotion emotion = mEmotionList.GetEmotion(tempText);
////                if (emotion != null) {
////                    try {
////                        String gif = emotion.GetFileGif();
////                        InputStream is = context.getAssets().open(gif);
////                        sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is, new AnimatedGifDrawable.UpdateListener() {
////                                    @Override
////                                    public void update() {
////                                        gifTextView.postInvalidate();
////                                    }
////                                })), m.start(), m.end(),
////                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                        is.close();
////                    } catch (Exception e) {
////                        String png = emotion.GetFilePath();
////                        try {
////                            sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open(png))), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                        } catch (IOException e1) {
////                            // TODO Auto-generated catch block
////                            e1.printStackTrace();
////                        }
////                        e.printStackTrace();
////                    }
////                }
////
////            }
////        }
////
////        return sb;
////    }
//
//    public static String getMapImageURL(IMMapInfo imMap) {
//        if (imMap == null)
//            return "";
//        String googleImageURL = "https://maps.googleapis.com/maps/api/staticmap?center="
//                + imMap.lat
//                + ","
//                + imMap.lng
//                + "&size=200x140&zoom=14&markers="
//                + imMap.lat
//                + ","
//                + imMap.lng
//                + "&key=AIzaSyClJPEVoXASfsh9X_dpkLeTPvXTLLS1-YI&markerStyles=s";
//        return googleImageURL;
//    }
//
////    /**
////     * 获取百度图片
////     *
////     * @param imMap
////     * @return
////     */
////    public static String getBaiduMapImageURL(IMMapInfo imMap) {
////        String baiduImageURL = "http://api.map.baidu.com/staticimage?center="
////                + imMap.lat
////                + ","
////                + imMap.lng
////                + "&width=200&height=140&zoom=16&markers="
////                + imMap.lat
////                + ","
////                + imMap.lng
////                + "&markerStyles=s";
////        return baiduImageURL;
////    }
//
//
////
//
//    /**
//     * 获取uuid
//     *
//     * @return
//     */
//    public static String getUUId() {
//        UUID uuid = UUID.randomUUID();
//        return uuid.toString();
//    }
//
//    public static String getMD5(MessageInfo messageInfo) {
//        String md5 = "";
//        switch (messageInfo.typefile) {
//            case MessageType.PICTURE:
//                md5 = messageInfo.imFile.getSmallimage();
//                break;
//            case MessageType.EMOTION:
//                md5 = messageInfo.imEmotion.imageUrlS;
//                break;
//        }
//        return md5;
//    }
//
//    /**
//     * 获取表情图片路径
//     * @param emotion
//     * @param isGif
//     *      是否是gif图片
//     * @return
//     */
////    public static String getEmotionPicURL(IMEmotion emotion,boolean isGif)
////    {
////        String URL="";
////        if (emotion != null) {
////            URL=isGif==false? emotion.GetFilePng():emotion.GetFileGif();
////            File file = new File(URL);
////            if (file == null || !file.isFile())
////            {
////                URL=isGif==false?
////                        IMVAR.getEmotionWebUrlForPng(emotion):IMVAR.getEmotionWebUrl(emotion);
////            }
////        }
////        return URL;
////    }
//
//    /**
//     * 判断是否为URL地址
//     *
//     * @return
//     */
//    public static boolean isUrl(String text) {
//        if (text == null || text.equals(""))
//            return false;
//        else {
//            Pattern p = Patterns.WEB_URL;
//            Matcher matcher = p.matcher(text);
//            return matcher.find();
//        }
//    }
//
////    /**
////     * 取相册中gif图(原图)
////     * @param pic
////     * @return
////     */
////    public static String  getPicPathForPath(Picture pic)
////    {
////        String result="";
////        if(pic==null)
////            return result;
////
////
////        result=pic.smallUrl;
////        if(!CommonUtil.isPic(pic.smallUrl))
////        {
////            result=pic.originUrl;
////        }
////        return result;
////    }
//
//    /**
//     * 获取发送消息和接收消息的音频
//     *
//     * @param context
//     * @param isSend  是否为发送消息
//     */
//    public static void getMessageMediaPlayer(Context context, boolean isSend) {
//        MediaPlayer mMediaPlayer = isSend ? MediaPlayer.create(context, R.raw.sendmsg) : MediaPlayer.create(context, msg);
//        mMediaPlayer.setLooping(false);
//        try {
//            mMediaPlayer.start();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 推送消息聲音
//     *
//     * @param context
//     */
//    public static void playMessagePushMP3(Context context) {
//        Login login = IMCommon.getLoginResult(context);
//
//        /*int resId;
//        if (!login.new_msg_tip_sound.equals("")){
//            String fileCode=login.new_msg_tip_sound.split("\\.")[0];
//            resId= context.getResources().getIdentifier(fileCode,"raw",context.getPackageName());
//        }else{
//            resId=R.raw.push_default;
//        }
//
//        MediaPlayer mMediaPlayer=MediaPlayer.create(context,resId);*/
//        //MediaPlayer mMediaPlayer=MediaPlayer.create(context,R.raw.msg_rev_push);
//
//        MediaPlayer mMediaPlayer;
//
//        if (!login.new_msg_tip_sound.equals("")) {
//            String fileCode = login.new_msg_tip_sound.split("\\.")[0];
//            int resId = context.getResources().getIdentifier(fileCode, "raw", context.getPackageName());
//            mMediaPlayer = MediaPlayer.create(context, resId);
//        } else {
//            mMediaPlayer = MediaPlayer.create(context, getSystemDefultRingtoneUri(context));
//        }
//
//        mMediaPlayer.setLooping(false);
//        try {
//            mMediaPlayer.start();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    //获取系统默认铃声的Uri
//    public static Uri getSystemDefultRingtoneUri(Context context) {
//        return RingtoneManager.getActualDefaultRingtoneUri(context,
//                RingtoneManager.TYPE_NOTIFICATION);
//    }
//
//    /**
//     * 判断图片长宽是否都小于200
//     *
//     * @return
//     */
//    public static boolean isPicSizeLess(int width, int height) {
//        if (width >= 200 || height >= 200) {
//            return false;
//        } else return true;
//    }
//
//    /**
//     * 发送短信
//     *
//     * @param PhoneNumber
//     * @param msg
//     */
//
//    public static void sendSMS(Context context, String PhoneNumber, String msg) {
//
//        Uri smsToUri = Uri.parse("smsto:" + PhoneNumber);
//
//        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
//
//        intent.putExtra("sms_body", msg);
//
//        context.startActivity(intent);
//
//    }
//
//    /**
//     * 文件复制
//     *
//     * @param fromFile
//     * @param toFile
//     */
//    public static void copyfile(File fromFile, File toFile) {
//
//        if (!fromFile.exists()) {
//            return;
//        }
//
//        if (!fromFile.isFile()) {
//            return;
//        }
//        if (!fromFile.canRead()) {
//            return;
//        }
//
//        try {
//            FileInputStream fosfrom = new FileInputStream(fromFile);
//            FileOutputStream fosto = new FileOutputStream(toFile);
//
//            byte[] bt = new byte[1024 * 10];
//            int c;
//            while ((c = fosfrom.read(bt)) > 0) {
//                fosto.write(bt, 0, c);
//            }
//            //关闭输入、输出流
//            fosfrom.close();
//            fosto.close();
//
//
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public static boolean moveFile(String oldPath, String newPath) {
//        return moveFile(oldPath, newPath, null, null);
//    }
//
//    public static boolean moveFile(String oldPath, String newPath, String oldName, String newName) {
//        String oldfullpath = oldPath + ((oldName != null) ? ("/" + oldName) : "");
//        String newfullpath = newPath + ((newName != null) ? ("/" + newName) : "");
//        if (!oldfullpath.equals(newfullpath)) {
//
//            File oldfile = new File(oldfullpath);
//            File newfile = new File(newfullpath);
//            if (!oldfile.exists()) {
//                return false;
//            }
//
//            if (!newfile.exists()) {
//                return oldfile.renameTo(newfile);
//            }
//        }
//        return false;
//    }
//
//    public static boolean copyFile(String oldPath, String newPath) {
//        try {
//            int bytesum = 0;
//            int byteread = 0;
//            File oldfile = new File(oldPath);
//            if (oldfile.isFile()) {
//                FileInputStream input = new FileInputStream(oldfile);
//                FileOutputStream output = new FileOutputStream(newPath);
//                byte[] b = new byte[1024 * 5];
//                int len;
//                while ((len = input.read(b)) != -1) {
//                    output.write(b, 0, len);
//                }
//                output.flush();
//                output.close();
//                input.close();
//                return true;
//            }
//            return false;
//
//        } catch (Exception e) {
//            System.out.println("复制单个文件操作出错");
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    //上傳聊天文件成功后處理,將文件轉入到相應本地目錄
//    public static void uploadChatFileComplete(Context context, MessageInfo msg, boolean deleteSurfile) {
//        if (msg == null || msg.imFile == null || msg.imFile.getOrigin() == null || msg.imFile.getOrigin().equals(""))
//            return;
//        String webfileURL = msg.imFile.getOrigin();
//        String localPath = msg.localPath;
//        switch (msg.typefile) {
//            case MessageType.PICTURE:
//                break;
//            case MessageType.VOICE:
//                File cookieVoiceFile = AudioUtil.getVoiceLocalPath(context, msg.getSessionID(), webfileURL);
//                String desVoiceFilePath = cookieVoiceFile.getAbsolutePath();
//                //msg.localPath = desVoiceFilePath;
//                boolean moveVoiceOk = copyFile(localPath, desVoiceFilePath);
//
//                if (moveVoiceOk && deleteSurfile) {
//                    delete(localPath);
//                    msg.localPath = desVoiceFilePath;
//                }
//
//                break;
//            case MessageType.VIDEO: {
//                File cookieFile = VideoUtil.getVideoLocalPathCaht(context, msg.getSessionID(), webfileURL);
//                String desfilePath = cookieFile.getAbsolutePath();
////                boolean moveok = moveFile(localPath,desfilePath);
//                boolean moveok = copyFile(localPath, desfilePath);
//                if (moveok && deleteSurfile) {
//                    delete(localPath);
//                    msg.localPath = desfilePath;
//                }
//                break;
//            }
//        }
//    }
//
//    //上傳朋友圈文件成功后處理,將文件轉入到相應本地目錄
//    public static void uploadLoopFileComplete(Context context, FLoopShareItem item, boolean deleteSurfile, List<MorePicture> picList) {
//        if (item == null || item.piclst == null || item.piclst.picList == null || item.piclst.picList.size() < 1)
//            return;
//        if (item.piclst.picList.get(0).key.equals("picture")) {
//            return;
//        }
//        if (item.piclst.picList.get(0).key.equals("video")) {
//            int size = picList.size() / 2;
//            int j = 0;
//            if (item.piclst.picList.size() == size) {
//                for (int i = 0; i < item.piclst.picList.size(); i++) {
//                    String webfileURL = item.piclst.picList.get(i).originUrl;
//                    String localPath = picList.get(j).filePath;
//                    File cookieFile = VideoUtil.getVideoLocalPath(context, webfileURL);
//                    String desfilePath = cookieFile.getAbsolutePath();
//                    boolean moveok = copyFile(localPath, desfilePath);
//                    if (moveok && deleteSurfile) {
//                        delete(localPath);
//                    }
//
//                    j = i + 2;
//                }
//            }
//
//        }
//
//
//    }
//
//    /**
//     * 获取本机的机器唯一编码
//     *
//     * @return
//     */
//    public static String getPhoneUDID() {
//        Context appContext = IMApp.getContext();
//        final TelephonyManager tm = (TelephonyManager) IMApp.getContext().getSystemService(Context.TELEPHONY_SERVICE);
//        final String tmDevice, tmSerial, androidId;
//        tmDevice = "" + tm.getDeviceId();
////		tmSerial = "" + tm.getSimSerialNumber();
//        tmSerial = "" + "sim";
//        androidId = "" + android.provider.Settings.Secure.getString(appContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//        String phoneUDID = deviceUuid.toString();
//        return phoneUDID;
//    }
//
//    /**
//     * 开启震动
//     */
//    public static void Vibration() {
//        android.os.Vibrator vibrator = (android.os.Vibrator) IMApp.getContext().getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(new long[]{1000, 0, 500}, 0);
//        vibrator.vibrate(1000);
//
//    }
//
//    public static String getVersion(Context context) {
//        PackageInfo info;
//        String packageName = context.getPackageName();
//        try {
//            info = context.getPackageManager().getPackageInfo(packageName, 0);
//            return info.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    //返回對應會話sessionID 的图片存取目錄
//    public static File getUserCachePath(Context context, String sessionID) {
//        Login mLogin = IMCommon.getLoginResult(context);
//        String id = "";
//        if (null != mLogin)
//            id = mLogin.uid;
//        String userId = id;
//        String dir = IMVAR.getGlideResourcePath();
//        if (null != userId && !userId.equals("")) {
//            dir = dir + userId + "/";
//        } else {
//            dir = dir + "user/";
//        }
//        if (null != sessionID && !sessionID.equals("")) {
//            dir = dir + sessionID + "/";
//        } else {
//            dir = dir + "public/";
//        }
//        File file = new File(dir);
//        if (!FeatureFunction.createWholePermissionFolder(dir)) {
//            return null;
//        }
//        return file;
//    }
//
//    /*
//     * 检查手机上是否安装了指定的软件
//     * @param context
//     * @param packageName：应用包名
//     * @return
//             */
//    public static boolean isAvilible(Context context, String packageName) {
//        //获取packagemanager
//        final PackageManager packageManager = context.getPackageManager();
//        //获取所有已安装程序的包信息
//        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
//        //用于存储所有已安装程序的包名
//        List<String> packageNames = new ArrayList<String>();
//        //从pinfo中将包名字逐一取出，压入pName list中
//        if (packageInfos != null) {
//            for (int i = 0; i < packageInfos.size(); i++) {
//                String packName = packageInfos.get(i).packageName;
//                packageNames.add(packName);
//            }
//        }
//        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
//        return packageNames.contains(packageName);
//    }
//
//    /**
//     * 判断某个界面是否在前台
//     *
//     * @param context
//     * @param className 某个界面名称
//     */
//    public static boolean isForeground(Context context, String className) {
//        if (context == null || TextUtils.isEmpty(className)) {
//            return false;
//        }
//
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
//        if (list != null && list.size() > 0) {
//            ComponentName cpn = list.get(0).topActivity;
//            if (className.equals(cpn.getClassName())) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * 得到是不是debug版本
//     *
//     * @return
//     */
//    public static boolean getIsDebug() {
//        ApplicationInfo appInfo = null;
//        try {
//            appInfo = IMApp.getInstance().getPackageManager()
//                    .getApplicationInfo(IMApp.getInstance().getPackageName(),
//                            PackageManager.GET_META_DATA);
//            boolean isDebug = appInfo.metaData.getBoolean("IsDebug", false);
//            return isDebug;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * 发布版本
//     */
//    public static final int APP_RELEASE = 2;
//
//    /**
//     * 得到是不是debug版本
//     *
//     * @return
//     */
//    public static boolean isDebug() {
//        return APP_RELEASE != BuildConfig.APP_STATUS;
//    }
//}
