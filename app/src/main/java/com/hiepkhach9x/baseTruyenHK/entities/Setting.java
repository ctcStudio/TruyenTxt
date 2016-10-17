package com.hiepkhach9x.baseTruyenHK.entities;

import android.graphics.Typeface;
import android.text.TextPaint;

import com.hiepkhach9x.baseTruyenHK.BookApplication;
import com.hiepkhach9x.baseTruyenHK.utils.Config;
import com.hiepkhach9x.baseTruyenHK.utils.Constants;

import java.util.Set;

/**
 * Created by HungHN on 2/20/2016.
 */
public class Setting {

    private int width;

    private int height;

    private float textSize = 1.5f;

    private int textColor = 0xffA7573E;

    private int pageColor = 0xffFDF8A6;

    private int horizontalPading = 10;

    private int verticalPadding = 10;

    private float spacingAdd = Constants.SPACING_LINE_MAP.get(Constants.KEY_SPACING_LINE_NORMAL);

    private float spacingMult = Constants.SPACING_MUL_MAP.get(Constants.KEY_SPACING_MUL_NORMAL);

    private TextPaint textPaint;

    private Typeface textFont;

    private String font = Constants.FONT_MAP.get(Constants.KEY_FONT_ROBOTO);

    public Setting() {
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHorizontalPading() {
        return horizontalPading;
    }

    public void setHorizontalPading(int horizontalPading) {
        this.horizontalPading = horizontalPading;
    }

    public int getPageColor() {
        return pageColor;
    }

    public void setPageColor(int pageColor) {
        this.pageColor = pageColor;
    }

    public float getSpacingAdd() {
        return spacingAdd;
    }

    public void setSpacingAdd(float spacingAdd) {
        this.spacingAdd = spacingAdd;
    }

    public float getSpacingMult() {
        return spacingMult;
    }

    public void setSpacingMult(float spacingMult) {
        this.spacingMult = spacingMult;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public TextPaint getTextPaint() {
        if (textPaint == null) {
            textPaint = new TextPaint();
        }
        setTextPaint();
        return textPaint;
    }

    private void setTextPaint() {
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        try {
            Typeface tf = Typeface.createFromAsset(BookApplication.get().getAssets(), Config.FONT_FOLDER + Constants.SEPARATOR + font);
            textPaint.setTypeface(tf);
        } catch (Exception ex) {
        }
    }

    public void setTextPaint(TextPaint textPaint) {
        this.textPaint = textPaint;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getVerticalPadding() {
        return verticalPadding;
    }

    public void setVerticalPadding(int verticalPadding) {
        this.verticalPadding = verticalPadding;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Setting) {
            Setting setting = (Setting)o;

            if((setting.getWidth() * setting.getHeight()) == (this.width * this.height)
                    && (setting.getVerticalPadding() == this.verticalPadding)
                    && (setting.getHorizontalPading() == this.horizontalPading)
                    && (setting.getSpacingAdd() == this.spacingAdd)
                    && (setting.getSpacingMult() == this.spacingMult)
                    && (setting.getTextSize() == this.textSize)
                    && (setting.getFont().equals(this.font))) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
