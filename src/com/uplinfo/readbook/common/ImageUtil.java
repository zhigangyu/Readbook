package com.uplinfo.readbook.common;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;

public class ImageUtil {
	
	/**
	 * ����ɫͼת��Ϊ���ڰ׶�ɫ
	 * 
	 * @param λͼ
	 * @return ����ת���õ�λͼ
	 */
	public static Bitmap convertToBlackWhite(Bitmap bmp) {
		int width = bmp.getWidth(); // ��ȡλͼ�Ŀ�
		int height = bmp.getHeight(); // ��ȡλͼ�ĸ�
		int[] pixels = new int[width * height]; // ͨ��λͼ�Ĵ�С�������ص�����

		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				// ������ԭɫ
				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				// ת���ɻҶ�����
				grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		// �½�ͼƬ
		Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
		// ����ͼƬ����
		newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

		Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460);
		return resizeBmp;
	}

	/**
	 * ����ָ��ͼƬ��С
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
}
