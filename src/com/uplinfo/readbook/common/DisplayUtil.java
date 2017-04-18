package com.uplinfo.readbook.common;

import android.content.Context;

/*
 * dp(dip): device independent pixels(�豸��������). ��ͬ�豸�в�ͬ����ʾЧ��,������豸Ӳ���йأ�һ������Ϊ��֧��WVGA��HVGA��QVGA �Ƽ�ʹ����������������ء�
 * dpҲ����dip�������sp�������ơ�������ñ�ʾ���ȡ��߶ȵ�����ʱ����ʹ��dp ��sp��������������壬��Ҫʹ��sp��dp�����ܶ��޹أ�sp�������ܶ��޹��⣬����scale�޹ء������Ļ�ܶ�Ϊ160����ʱdp��sp��px��һ ���ġ�1dp=1sp=1px�������ʹ��px����λ�������Ļ��С���䣨���軹��3.2�磩������Ļ�ܶȱ����320����ôԭ��TextView�Ŀ�� ���160px�����ܶ�Ϊ320��3.2����Ļ�￴Ҫ�����ܶ�Ϊ160��3.2����Ļ�Ͽ�����һ�롣��������ó�160dp��160sp�Ļ���ϵͳ���Զ� ��width����ֵ���ó�320px�ġ�Ҳ����160 * 320 / 160������320 / 160�ɳ�Ϊ�ܶȱ������ӡ�Ҳ����˵�����ʹ��dp��sp��ϵͳ�������Ļ�ܶȵı仯�Զ�����ת����
 * px: pixels(����). ��ͬ�豸��ʾЧ����ͬ��һ������HVGA����320x480���أ�����õıȽ϶ࡣ
 * pt: point����һ����׼�ĳ��ȵ�λ��1pt��1/72Ӣ�磬����ӡˢҵ���ǳ������ã�
 * sp: scaled pixels(�Ŵ�����). ��Ҫ����������ʾbest for textsize��
 */
/** 
 * dp��sp ת��Ϊ px �Ĺ����� 
 *  
 * @author fxsky 2012.11.12 
 * 
 */  
public class DisplayUtil {  
    /** 
     * ��pxֵת��Ϊdip��dpֵ����֤�ߴ��С���� 
     *  
     * @param pxValue 
     * @param scale 
     *            ��DisplayMetrics��������density�� 
     * @return 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
  
    /** 
     * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С���� 
     *  
     * @param dipValue 
     * @param scale 
     *            ��DisplayMetrics��������density�� 
     * @return 
     */  
    public static int dip2px(Context context, float dipValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dipValue * scale + 0.5f);  
    }  
  
    /** 
     * ��pxֵת��Ϊspֵ����֤���ִ�С���� 
     *  
     * @param pxValue 
     * @param fontScale 
     *            ��DisplayMetrics��������scaledDensity�� 
     * @return 
     */  
    public static int px2sp(Context context, float pxValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }  
  
    /** 
     * ��spֵת��Ϊpxֵ����֤���ִ�С���� 
     *  
     * @param spValue 
     * @param fontScale 
     *            ��DisplayMetrics��������scaledDensity�� 
     * @return 
     */  
    public static int sp2px(Context context, float spValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }  
}
