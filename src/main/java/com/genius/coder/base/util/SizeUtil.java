package com.genius.coder.base.util;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/6
 */
public final class SizeUtil {
    public static String getSizeStr(long size) {
        String sizeStr;
        if (size != 0L) {
            long kb = 1024L;
            long mb = kb * 1024L;
            long gb = mb * 1024L;
            if (size >= gb) {
                sizeStr = String.format("%.1fGB", (float)size / (float)gb);
            } else {
                float f;
                if (size >= mb) {
                    f = (float)size / (float)mb;
                    sizeStr = String.format(f > 100.0F ? "%.0fMB" : "%.1fMB", f);
                } else if (size >= kb) {
                    f = (float)size / (float)kb;
                    sizeStr = String.format(f > 100.0F ? "%.0fKB" : "%.1fKB", f);
                } else {
                    sizeStr = String.format("%dB", size);
                }
            }
        } else {
            sizeStr = "0";
        }

        return sizeStr;
    }

    private SizeUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
