package com.nibiru.programframe.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v4.provider.DocumentFile;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import okhttp3.ResponseBody;
import x.core.ui.XActor;
import x.core.ui.animation.XAnimation;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/15
 * 描述:
 */

public class FileUtils {
    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtils.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtils.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldname 原来的文件名
     * @param newname 新文件名
     */
    public void renameFile(String path, String oldname, String newname) {
        if (!oldname.equals(newname)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path + "/" + oldname);
            File newfile = new File(path + "/" + newname);
            if (!oldfile.exists()) {
                return;//重命名文件不存在
            }
            if (newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname + "已经存在！");
            else {
                oldfile.renameTo(newfile);
            }
        } else {
            System.out.println("新文件名和旧文件名相同...");
        }
    }

    /**
     * 从文件中读取文件内容
     * @param filePath
     * @param charsetName
     * @return 返回字符串
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        if (TextUtils.isEmpty(charsetName)) {
            charsetName = "utf-8";
        }
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            IOUtils.close(reader);
        }
    }

    /**
     * 从sd card文件中读取数据
     *
     * @return
     */
    public static String readExternal(String path) throws IOException {
        StringBuilder sb = new StringBuilder("");
        //打开文件输入流
        FileInputStream inputStream = new FileInputStream(path);
        byte[] buffer = new byte[1024 * 1024];
        int len = inputStream.read(buffer);
        //读取文件内容
        while (len > 0) {
            sb.append(new String(buffer, 0, len));
            //继续将数据放到buffer中
            len = inputStream.read(buffer);
        }
        //关闭输入流
        inputStream.close();
        return sb.toString();
    }
    public static void saveAssext(Context context, String fileName) {
        String pathForVisual_Acutity = Environment.getExternalStorageDirectory() + "/Account";
        int len;
        byte[] buf = new byte[2048];
        try {
            InputStream in = context.getAssets().open(fileName);
            File file = new File(pathForVisual_Acutity, fileName);

            FileOutputStream out = new FileOutputStream(file);
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static <T> boolean saveAppList(Context context, List<T> list, String fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> List<T> readAppList(Context context, String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(fileName);
            ois = new ObjectInputStream(fis);
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //文件缓存时间 30分钟
    public static final long CACHE_TIME = 60 * 60 * 1000;

    public static boolean isCacheExpired(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (!file.exists()) {
            return true;
        }
//        else {
//            if (System.currentTimeMillis() - file.lastModified() > CACHE_TIME) {
//                return true;
//            }
//        }
        return false;
    }

    /**
     * @des 检查本地是不是用这个应用
     */
    public static boolean checkAppIsExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            if (context != null) {
                context.getPackageManager().getApplicationInfo(packageName,
                        PackageManager.GET_UNINSTALLED_PACKAGES);
                return true;
            } else {
                return false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void setScaleAnim(XActor actor, float factor) {
        actor.setEnableGazeAnimation(true);
        actor.setGazeAnimation(XAnimation.AnimationType.SCALE, factor, 150);
    }

    public static void setScaleAnim(XActor actor) {
        actor.setEnableGazeAnimation(true);
        actor.setGazeAnimation(XAnimation.AnimationType.SCALE, 0.15f, 150);
    }

    public static Bitmap getBackgroundBitmap(int width, int height, int alaph, int r, int g, int b) {
        Paint paint = new Paint();
        paint.setARGB(alaph, r, g, b);
        paint.setTypeface(null);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(new Rect(0, 0, width, height), paint);
        return bitmap;
    }

    /**
     * 根据包名打开应用
     *
     * @param context
     * @param packageName
     */
    public static void startApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void uninstallApk(Context context, String packageName) {
        if (isNibiruOS()) {
            MLog.d("unistallApk");
            Intent mIntent = new Intent();
            mIntent.setAction("com.nibiru.action.UNINSTALL_NODIPLAY");
            mIntent.putExtra("packagename", packageName);
            context.sendBroadcast(mIntent);
        } else {
            try {
                PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                        packageName, 0);
                String packagename = pInfo.packageName;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:" + packagename));
                context.startActivity(intent);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isNibiruOS() {
        if (android.os.Build.BRAND == null) {
            return false;
        }
        //获取设备的品牌
        String brand = android.os.Build.BRAND.toLowerCase(Locale.US);
        if (brand.contains("nibiru")) {
            return true;
        }
        return false;
    }

    public static String getAppBgName() {
        Random random = new Random();
        int randomIndex = random.nextInt(15) + 1;
        String imageName = "app_bg" + randomIndex + ".png";
        return imageName;
    }

    public static boolean saveObject(Context context, String jsonObj, String fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(jsonObj);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readObject(Context context, String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(fileName);
            ois = new ObjectInputStream(fis);
            return (String) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Create File
     * @param context
     */
    public static void createFile(Context context, String filename) {
        String pathForVisual_Acutity = Environment.getExternalStorageDirectory() + "/" + filename;
        insertCR(context, pathForVisual_Acutity);
    }

    public static final void insertCR(Context context, String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
            file.setExecutable(true);
            file.setReadable(true);
            file.setWritable(true);
        }
        ContentValues values = new ContentValues();
        values.put(MediaStore.Files.FileColumns.DATA, path);
        //参考/data/data/com.android.providers.media/databases/external.db中的字段对应值，6.0系统
        values.put("format", 12289);//假如是文件夹必需加上这个，否则format默认的是12288，在电脑上就不能被识别为文件夹
        try {
            context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
            MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("insertCR error", file.getAbsolutePath() + "show errors,cause of error" + e.toString());
        }
    }


    public static void saveAssext(Context context) {
        String pathForVisual_Acutity = Environment.getExternalStorageDirectory() + "/Account";
        int len;
        byte[] buf = new byte[2048];
        try {
            InputStream in = context.getAssets().open("medal.nsm");
            File file = new File(pathForVisual_Acutity, "medal" + ".nsm");

            FileOutputStream out = new FileOutputStream(file);
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void saveAssextTwo(Context context) {
        String pathForVisual_Acutity = Environment.getExternalStorageDirectory() + "/Account";
        int len;
        byte[] buf = new byte[2048];
        try {
            InputStream in = context.getAssets().open("xunzhang.nsm");
            File file = new File(pathForVisual_Acutity, "xunzhang" + ".nsm");

            FileOutputStream out = new FileOutputStream(file);
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap, String url) {
        String savePath = Environment.getExternalStorageDirectory() + "/ImageFile/" + MD5(url);
        File filePic;

        try {
            filePic = new File(savePath, "medal" + ".png");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

//        return filePic.getAbsolutePath();
        return savePath;
    }

    /**
     * md5加密
     *
     * @param s
     * @return
     */
    public static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    //验证改文件是否存在
    public static boolean isExit(String name) {
        String path = Environment.getExternalStorageDirectory() + "/ImageFile/" + MD5(name);
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }


    /**
     * 保存下载的图片流写入SD卡文件
     *
     * @param url  xxx.jpg
     * @param body image stream
     */
    public static void writeResponseBodyToDisk(String url, ResponseBody body) {
        if (body == null) {

            return;
        }
        try {
            InputStream is = body.byteStream();
            File fileDr = new File(url);
            if (!fileDr.exists()) {
                fileDr.mkdir();
            }
            File file = new File(Environment.getExternalStorageDirectory() + "/ImageFile/" + MD5(url), "medal.png");
            if (file.exists()) {
                file.delete();
                file = new File(Environment.getExternalStorageDirectory() + "/ImageFile/" + MD5(url), "medal.png");
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取png文件名
     */
    public static String getJpgFileName(String url) {
        String[] split = url.split("/");
        String s = split[split.length - 1];
        String[] split1 = s.split("\\.");
        String result = split1[0];
        return result;
    }
    //防止更名失败
    public static boolean renameTarget(String filePath, String newName) {
        File src = new File(filePath);
        if (!src.exists()) {
            return false;
        }

        String temp = filePath.substring(0, filePath.lastIndexOf("/"));
        File dest = new File(temp + "/" + newName);
        if (src.renameTo(dest)) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                DocumentFile document = DocumentFile.fromFile(src);

                if (document.renameTo(dest.getAbsolutePath())) {

                    return true;
                }
            }
        }
        return false;
    }

    public static void moveToDirectory(File oldFile, File target, Context c) {
        if (!oldFile.renameTo(target) && copyFile(oldFile, target, c)) {
            deleteTarget(oldFile.getAbsolutePath());
        }
    }

    private static final int BUFFER = 16384;

    // TODO: fix copy to sdcard root
    public static boolean copyFile(final File source, final File target, Context context) {
        FileInputStream inStream = null;
        OutputStream outStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            File tempDir = target.getParentFile();

            if (source.isFile())
                inStream = new FileInputStream(source);

            if (source.canRead() && tempDir.isDirectory()) {
                if (source.isFile()) {
                    outStream = new FileOutputStream(target);
                    inChannel = inStream.getChannel();
                    outChannel = ((FileOutputStream) outStream).getChannel();
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                } else if (source.isDirectory()) {

                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DocumentFile targetDocument = DocumentFile.fromFile(tempDir);
                    outStream = context.getContentResolver().openOutputStream(targetDocument.getUri());
                } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                    Uri uri = getUriFromFile(target.getAbsolutePath(), context);
                    outStream = context.getContentResolver().openOutputStream(uri);
                } else {
                    return false;
                }

                if (outStream != null && inStream != null) {
                    byte[] buffer = new byte[BUFFER];
                    int bytesRead;
                    while ((bytesRead = inStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                } else {
//                    RootCommands.moveCopyRoot(source.getAbsolutePath(), target.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (inStream != null && outStream != null && inChannel != null && outChannel != null) {
                    inStream.close();
                    outStream.close();
                    inChannel.close();
                    outChannel.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public static void deleteTarget(String path) {
        File target = new File(path);

        if (target.isFile() && target.canWrite()) {
            target.delete();
        } else if (target.isDirectory() && target.canRead() && target.canWrite()) {
            String[] fileList = target.list();

            if (fileList != null && fileList.length == 0) {
                target.delete();
                return;
            } else if (fileList != null && fileList.length > 0) {
                for (String aFile_list : fileList) {
                    File tempF = new File(target.getAbsolutePath() + "/"
                            + aFile_list);

                    if (tempF.isDirectory())
                        deleteTarget(tempF.getAbsolutePath());
                    else if (tempF.isFile()) {
                        tempF.delete();
                    }
                }
            }

            if (target.exists())
                target.delete();
        } else if (!target.delete() ) {
//            RootCommands.deleteRootFileOrDir(target);
        }
    }



    public static Uri getUriFromFile(final String path, Context context) {
        ContentResolver resolver = context.getContentResolver();

        Cursor filecursor = resolver.query(MediaStore.Files.getContentUri("external"),
                new String[]{BaseColumns._ID}, MediaStore.MediaColumns.DATA + " = ?",
                new String[]{path}, MediaStore.MediaColumns.DATE_ADDED + " desc");
        filecursor.moveToFirst();

        if (filecursor.isAfterLast()) {
            filecursor.close();
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DATA, path);
            return resolver.insert(MediaStore.Files.getContentUri("external"), values);
        } else {
            int imageId = filecursor.getInt(filecursor.getColumnIndex(BaseColumns._ID));
            Uri uri = MediaStore.Files.getContentUri("external").buildUpon().appendPath(
                    Integer.toString(imageId)).build();
            filecursor.close();
            return uri;
        }
    }

}
