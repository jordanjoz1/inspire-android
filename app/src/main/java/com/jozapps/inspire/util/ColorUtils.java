package com.jozapps.inspire.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import com.jozapps.inspire.R;

import java.lang.reflect.Field;
import java.util.Random;

public class ColorUtils {

    private static final int BRIGHTNESS_THRESHOLD = 200;

    public static int getRandomColorRes(Context context, long seed) {
        try {
            Field[] fields = Class.forName(context.getPackageName() + ".R$color")
                    .getDeclaredFields();
            Random generator = new Random(seed);
            Field field = fields[generator.nextInt(fields.length)];
            int colorId = field.getInt(null);
            int color = context.getResources().getColor(colorId);
            return color;
        } catch (Exception e) {
            return context.getResources().getColor(R.color.material_blue_grey_800);
        }
    }

    public static int getAccentColor(Context context, int color) {
        int accentColor;
        if (isColorDark(color, BRIGHTNESS_THRESHOLD)) {
            accentColor = getLighterPrimaryColor(context, .6f, color);
        } else {
            accentColor = getDarkerPrimaryColor(context, .6f, color);
        }
        return accentColor;
    }

    public static boolean isColorDark(int color, int threshold) {
        return ((30 * Color.red(color) + 59 * Color.green(color) + 11 * Color
                .blue(color)) / 100) <= threshold;
    }

    public static int getDarkerPrimaryColor(@NonNull Context context,
                                            float scale, int colorRes) {
        try {
            float[] hsv = new float[3];
            Color.colorToHSV(colorRes, hsv);
            // scale color to make it darker
            hsv[2] *= scale;
            return Color.HSVToColor(hsv);
        } catch (Exception e) {
            return context.getResources().getColor(
                    R.color.md_black_1000);
        }
    }

    public static int getLighterPrimaryColor(Context context, float scale, int colorRes) {
        try {
            float[] hsv = new float[3];
            Color.colorToHSV(colorRes, hsv);
            // scale color to make it darker
            hsv[2] *= scale;
            return Color.HSVToColor(hsv);
        } catch (Exception e) {
            return context.getResources().getColor(
                    R.color.md_white_1000);
        }
    }

    public static ColorStateList getTintListForColor(@ColorRes int colorRes) {
        return new ColorStateList(new int[][]{new int[]{0}}, new int[]{colorRes});
    }

    public static int getWhiteBlackAccent(Context context, int color) {
        if (isColorDark(color, BRIGHTNESS_THRESHOLD)) {
            return context.getResources().getColor(R.color.md_white_1000);
        } else {
            return context.getResources().getColor(R.color.md_black_1000);
        }
    }

    public static int getRandomAccentColor(Context context, int color, long seed) {
        boolean isDark = isColorDark(color, BRIGHTNESS_THRESHOLD);
        try {
            Field[] fields = Class.forName(context.getPackageName() + ".R$color")
                    .getDeclaredFields();
            Random generator = new Random(seed);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[generator.nextInt(fields.length)];
                int colorId = field.getInt(null);
                int randomColor = context.getResources().getColor(colorId);
                if ((isDark && !isColorDark(randomColor, BRIGHTNESS_THRESHOLD)) || (!isDark &&
                        isColorDark(color, BRIGHTNESS_THRESHOLD))) {
                    return randomColor;
                }
            }
            return context.getResources().getColor(R.color.md_blue_grey_400);
        } catch (Exception e) {
            return context.getResources().getColor(R.color.md_blue_grey_400);
        }
    }
}
