package org.glcf2;

/**
 * 32bitまでのフラグを管理します。
 */
public class FragUtil32 {
    /**
     * int型のフラグが指定のフラグに対してtrueかどうかを返します。
     * @param src 比較元
     * @param frag 比較対象
     * @return srcで、fragがtrueの場合にtrue
     */
    public static boolean fragIs(int src, int frag) {
        return (src & frag) != 0;
    }

    /**
     * 指定のフラグをtrueにします
     * @param src 変えたいフラグ
     * @param frag trueにしたいフラグ
     * @return fragがtrueになったsrc
     */
    public static int fragSetTrue(int src, int frag) {
        return src | frag;
    }

    /**
     * 指定のフラグをfalseにします
     * @param src 変えたいフラグ
     * @param frag falseにしたいフラグ
     * @return fragがfalseになったsrc
     */
    public static int fragSetFalse(int src, int frag) {
        return src & ~frag;
    }
}
