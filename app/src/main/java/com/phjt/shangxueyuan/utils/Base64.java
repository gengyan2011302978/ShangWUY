package com.phjt.shangxueyuan.utils;

/**
 * @author: gengyan
 * date:    2021/6/4 11:08
 * company: 普华集团
 * description: 描述
 */
public class Base64 {
    private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static byte[] codes = new byte[256];

    public Base64() {
    }

    public static byte[] decode(byte[] var0) {
        int var1 = (var0.length + 3) / 4 * 3;
        if (var0.length > 0 && var0[var0.length - 1] == 61) {
            --var1;
        }

        if (var0.length > 1 && var0[var0.length - 2] == 61) {
            --var1;
        }

        byte[] var2 = new byte[var1];
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;

        for(int var6 = 0; var6 < var0.length; ++var6) {
            byte var7 = codes[var0[var6] & 255];
            if (var7 >= 0) {
                var4 <<= 6;
                var3 += 6;
                var4 |= var7;
                if (var3 >= 8) {
                    var3 -= 8;
                    var2[var5++] = (byte)(var4 >> var3 & 255);
                }
            }
        }

        if (var5 != var2.length) {
            throw new RuntimeException("miscalculated data length!");
        } else {
            return var2;
        }
    }

    public static char[] encode(byte[] var0) {
        char[] var1 = new char[(var0.length + 2) / 3 * 4];
        int var2 = 0;

        for(int var3 = 0; var2 < var0.length; var3 += 4) {
            boolean var4 = false;
            boolean var5 = false;
            int var6 = 255 & var0[var2];
            var6 <<= 8;
            if (var2 + 1 < var0.length) {
                var6 |= 255 & var0[var2 + 1];
                var5 = true;
            }

            var6 <<= 8;
            if (var2 + 2 < var0.length) {
                var6 |= 255 & var0[var2 + 2];
                var4 = true;
            }

            var1[var3 + 3] = alphabet[var4 ? var6 & 63 : 64];
            var6 >>= 6;
            var1[var3 + 2] = alphabet[var5 ? var6 & 63 : 64];
            var6 >>= 6;
            var1[var3 + 1] = alphabet[var6 & 63];
            var6 >>= 6;
            var1[var3 + 0] = alphabet[var6 & 63];
            var2 += 3;
        }

        return var1;
    }

    public static byte[] encodebyte(byte[] var0) {
        byte[] var1 = new byte[(var0.length + 2) / 3 * 4];
        int var2 = 0;

        for(int var3 = 0; var2 < var0.length; var3 += 4) {
            boolean var4 = false;
            boolean var5 = false;
            int var6 = 255 & var0[var2];
            var6 <<= 8;
            if (var2 + 1 < var0.length) {
                var6 |= 255 & var0[var2 + 1];
                var5 = true;
            }

            var6 <<= 8;
            if (var2 + 2 < var0.length) {
                var6 |= 255 & var0[var2 + 2];
                var4 = true;
            }

            var1[var3 + 3] = (byte)alphabet[var4 ? var6 & 63 : 64];
            var6 >>= 6;
            var1[var3 + 2] = (byte)alphabet[var5 ? var6 & 63 : 64];
            var6 >>= 6;
            var1[var3 + 1] = (byte)alphabet[var6 & 63];
            var6 >>= 6;
            var1[var3 + 0] = (byte)alphabet[var6 & 63];
            var2 += 3;
        }

        return var1;
    }

    static {
        int var0;
        for(var0 = 0; var0 < 256; ++var0) {
            codes[var0] = -1;
        }

        for(var0 = 65; var0 <= 90; ++var0) {
            codes[var0] = (byte)(var0 - 65);
        }

        for(var0 = 97; var0 <= 122; ++var0) {
            codes[var0] = (byte)(26 + var0 - 97);
        }

        for(var0 = 48; var0 <= 57; ++var0) {
            codes[var0] = (byte)(52 + var0 - 48);
        }

        codes[43] = 62;
        codes[47] = 63;
    }
}
