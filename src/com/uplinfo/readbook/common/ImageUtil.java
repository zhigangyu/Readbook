package com.uplinfo.readbook.common;

import java.io.File;
import java.io.FileInputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;

public class ImageUtil {
	
	/**
	 * 将彩色图转换为纯黑白二色
	 * 
	 * @param 位图
	 * @return 返回转换好的位图
	 */
	public static Bitmap convertToBlackWhite(Bitmap bmp) {
		int width = bmp.getWidth(); // 获取位图的宽
		int height = bmp.getHeight(); // 获取位图的高
		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				// 分离三原色
				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				// 转化成灰度像素
				grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		// 新建图片
		Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
		// 设置图片数据
		newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

		Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460);
		return resizeBmp;
	}

	/**
	 * 重新指定图片大小
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap newbmp;
		try {
			Matrix matrix = new Matrix();
			float scaleHeight = ((float) height / h);
			matrix.postScale(scaleHeight, scaleHeight);
			newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
			return newbmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapFromPath(String path) {  
		  
        if (!new File(path).exists()) {  
            System.err.println("getBitmapFromPath: file not exists");  
            return null;  
        }  
  
        byte[] buf = new byte[1024 * 1024];// 1M  
        Bitmap bitmap = null;  
  
        try {  
  
            FileInputStream fis = new FileInputStream(path);  
            int len = fis.read(buf, 0, buf.length);  
            bitmap = BitmapFactory.decodeByteArray(buf, 0, len);  
            if (bitmap == null) {  
                System.out.println("len= " + len);  
                System.err  
                        .println("path: " + path + "  could not be decode!!!");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
  
        return bitmap;  
    } 
}
