package com.smallsoho.mcplugin.image;

public class Config {
    public static final String OPTIMIZE_WEBP_CONVERT = "webp"; //webp化
    public static final String OPTIMIZE_COMPRESS_PICTURE = "compress"; //压缩图片

    public float maxStroageSize = 1024 * 1024;
    public boolean isCheckStorageSize = true; //是否检查大体积图片
    public String optimizeWay = OPTIMIZE_WEBP_CONVERT; //优化方式，webp化、压缩图片
    public boolean enableWhenDebug = true;
    public boolean isCheckPixelSize = true; //是否检查大像素图片
    public int maxWidth = 500;
    public int maxHeight = 500;
    public String[] whiteList = new String[]{};
    public String mctoolsDir = "";
    public boolean isSupportAlphaWebp = true; //是否支持webp化透明通道的图片

    public void maxSize(float maxSize) {
        this.maxStroageSize = maxSize;
    }

    public void isCheckStorageSize(boolean check) {
        isCheckStorageSize = check;
    }

    public void optimizeWay(String optimizeWay) {
        this.optimizeWay = optimizeWay;
    }

    public void isSupportAlphaWebp(boolean isSupportAlphaWebp) {
        this.isSupportAlphaWebp = isSupportAlphaWebp;
    }

    public void enableWhenDebug(boolean enableWhenDebug) {
        this.enableWhenDebug = enableWhenDebug;
    }

    public void isCheckPixelSize(boolean checkSize) {
        isCheckPixelSize = checkSize;
    }

    public void maxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void whiteList(String[] whiteList) {
        this.whiteList = whiteList;
    }

    public void mctoolsDir(String mctoolsDir) {
        this.mctoolsDir = mctoolsDir;
    }

    public void maxStroageSize(float maxStroageSize) {
        this.maxStroageSize = maxStroageSize;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("---McConfig---" + "\n");
        result.append("maxStroageSize :" + maxStroageSize + "\n"
            + "isCheckStorageSize: " + isCheckStorageSize + "\n"
            + "optimizeWay: " + optimizeWay + "\n"
            + "enableWhenDebug: " + enableWhenDebug + "\n"
            + "isCheckPixelSize: " + isCheckPixelSize + "\n"
            + "maxWidth: " + maxWidth + ", maxHeight: "  + maxHeight + "\n"
            + "mctoolsDir: " + mctoolsDir + "\n"
            + "isSupportAlphaWebp: " + isSupportAlphaWebp
            + "whiteList : ");
        for(String file : whiteList) {
            result.append("-> : " + file + "\n");
        }
        result.append("-----------");
        return result.toString();
    }
}
