package org.glcf2.vertex;

import org.glcf2.programobject.VBO;
import org.linear.main.matrix.FloatMatrix;
import org.linear.main.vector.DoubleVector;
import org.linear.main.vector.FloatVector;

import java.util.List;

public interface ArrayModel<MAT extends FloatMatrix<MAT, MAT>, VEC extends FloatVector<VEC>> extends Cloneable {
    /**
     * このオブジェクトを任意の軸を中心に回転します。{@link ArrayModel#setAnchor}によって回転の中心を指定できます。
     * @param radian radian
     * @return this
     */
    ArrayModel<MAT, VEC> rotate(float radian);

    /**
     * このオブジェクトをx軸を基準に回転します。{@link ArrayModel#setAnchor}によって回転の中心を指定できます。
     * @param value radian
     * @return this
     */
    ArrayModel<MAT, VEC> rotateX(float value);

    /**
     * このオブジェクトをy軸を基準に回転します。{@link ArrayModel#setAnchor}によって回転の中心を指定できます。
     * @param value radian
     * @return this
     */
    ArrayModel<MAT, VEC> rotateY(float value);

    /**
     * このオブジェクトをz軸を基準に回転します。{@link ArrayModel#setAnchor}によって回転の中心を指定できます。
     * @param value radian
     * @return this
     */
    ArrayModel<MAT, VEC> rotateZ(float value);

    /**
     * このオブジェクトをスケーリング変形します。{@link ArrayModel#setAnchor}によって変形の中心を指定できます。
     * @param x magnification
     * @param y magnification
     * @param z magnification
     * @return this
     */
    ArrayModel<MAT, VEC> scale(float x, float y, float z);

    /**
     * このオブジェクトをスケーリング変形します。{@link ArrayModel#setAnchor}によって変形の中心を指定できます。
     * @param value magnification
     * @return this
     */
    ArrayModel<MAT, VEC> scale(float value);

    /**
     * このオブジェクトをx軸方向にスケーリング変形します。{@link ArrayModel#setAnchor}によって変形の中心を指定できます。
     * @param value magnification
     * @return this
     */
    ArrayModel<MAT, VEC> scaleX(float value);

    /**
     * このオブジェクトをy軸方向にスケーリング変形します。{@link ArrayModel#setAnchor}によって変形の中心を指定できます。
     * @param value magnification
     * @return this
     */
    ArrayModel<MAT, VEC> scaleY(float value);

    /**
     * このオブジェクトをz軸方向にスケーリング変形します。{@link ArrayModel#setAnchor}によって変形の中心を指定できます。
     * @param value magnification
     * @return this
     */
    ArrayModel<MAT, VEC> scaleZ(float value);

    /**
     * このオブジェクトをシャーリング変形します。{@link ArrayModel#setAnchor}によって変形の中心を指定できます。
     * @param x magnification
     * @param y magnification
     * @param z magnification
     * @return this
     */
    ArrayModel<MAT, VEC> shear(float x, float y, float z);

    /**
     * このオブジェクトをxy平面を基準にシャーリング変形します。{@link ArrayModel#setAnchor}によって変形の中心を指定できます。
     * @return this
     */
    ArrayModel<MAT, VEC> shearXY(float x, float y);

    /**
     * このオブジェクトをxz平面を基準にシャーリング変形します。{@link ArrayModel#setAnchor}によって変形の中心を指定できます。
     * @return this
     */
    ArrayModel<MAT, VEC> shearXZ(float x, float z);

    /**
     * このオブジェクトをyz平面を基準にシャーリング変形します。{@link ArrayModel#setAnchor}によって変形の中心を指定できます。
     * @return this
     */
    ArrayModel<MAT, VEC> shearYZ(float y, float z);

    /**
     * このオブジェクトを並行移動します
     * @param x distance of translate this object.
     * @param y distance of translate this object.
     * @param z distance of translate this object.
     * @return this
     */
    ArrayModel<MAT, VEC> translate(float x, float y, float z);

    /**
     * このオブジェクトをx軸に沿って並行移動します
     * @param value distance of translate this object.
     * @return this
     */
    ArrayModel<MAT, VEC> translateX(float value);

    /**
     * このオブジェクトをy軸に沿って並行移動します
     * @param value distance of translate this object.
     * @return this
     */
    ArrayModel<MAT, VEC> translateY(float value);

    /**
     * このオブジェクトをz軸に沿って並行移動します
     * @param value distance of translate this object.
     * @return this
     */
    ArrayModel<MAT, VEC> translateZ(float value);

    /**
     * このオブジェクトの中心を設定します。
     * @param pos magnification
     */
    void setAnchor(VEC pos);

    /**
     * オブジェクトの頂点を追加します。
     * @param vertex vertex point
     */
    void addVertex(VEC vertex);

    /**
     * オブジェクトの頂点を設定します。
     * @param index vertex index
     * @param vertex vertex point
     */
    void setVertex(int index, VEC vertex);

    /**
     * オブジェクトの頂点をまとめて追加。
     * @param vertices vertices
     */
    void addVertices(VEC[] vertices);

    /**
     * コピーします。
     * @param copy
     */
    void set(ArrayModel<MAT, VEC> copy);

    /**
     * オブジェクトの頂点を指定した位置にまとめて追加。
     * @param start from this pos
     * @param vertices vertec
     */
    void setVertices(int start, VEC[] vertices);

    /**
     * 指定した頂点を削除します。
     * @param index index
     */
    void remove(int index);

    /**
     * 指定した頂点をまとめて削除します。
     * @param start from this pos
     * @param offset up to this pos
     */
    void removes(int start, int offset);

    MAT getMatrix();

    void setMatrix(MAT mat);

    /**
     * 頂点を取得
     * @param index index
     * @return vertex
     */
    VEC getVertex(int index);

    /**
     * 頂点をまとめて配列で取得
     * @param start from this pos
     * @param offset up to this pos
     * @return vertices
     */
    VEC[] getVerticies(int start, int offset);

    /**
     * 頂点をまとめてListで取得
     * @param start from this pos
     * @param offset up to this pos
     * @return vertices
     */
    List<VEC> getVerticiesList(int start, int offset);

    /**
     * 頂点の数
     * @return size of vertices
     */
    int size();

    /**
     * 全頂点に変換を適用します。
     */
    void transformation();

    /**
     * 頂点をまとめて配列で取得
     * @return vertices
     */
    default VEC[] getVerticies() {
        return getVerticies(0, size());
    }

    /**
     * 頂点をまとめてListで取得
     * @return vertices
     */
    default List<VEC> getVerticiesList() {
        return getVerticiesList(0, size());
    }

    /**
     * 頂点を{@link VBO}に変換します
     * @return array of vertices
     */
    default VBO.Float toVBO() {
        VEC[] arrV = getVerticies();
        VEC vec;
        final int dim = arrV[0].dimension();
        float[] arr = new float[size() * dim];
        float[] _fa = new float[dim];

        for (int vi = 0; vi < arrV.length; vi++) {
            vec = arrV[vi];
            vec.toArray(_fa);

            for (int i = 0; i < dim; i++) {
                arr[vi * dim + i] = vec.get(i);
            }
        }

        return VBO.create(arr);
    }

    /**
     * 与えられた型が、使用可能か調べます。
     * @param mat matrix of transformation
     * @param vec vector of vertex
     * @return can use this types.
     */
    default boolean canCreateArrayModel(MAT mat, VEC vec) {
        return mat.rowDimension() == vec.dimension();
    }

    /**
     * 異なる次元のベクトルにします。例えば3次元のベクトルのz要素を削って2次元平面上に投影できるようなベクトルにします。
     * <pre>A(1, 2, 3) -> A'(1, 2)</pre>
     * @return vectors
     */
    DoubleVector<?>[] cutArray();
}
