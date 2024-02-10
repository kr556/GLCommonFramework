package org.glcf3;

import org.linear.main.matrix.SquareMatrix;
import org.linear.main.vector.Vector;

import java.util.List;

/**
 * This class store model verticies data of origin.
 * @param <V> Verticies type.
 */
public interface ModelData<V extends Vector<?, ?>> {
    void setData(int index, V data);

    void setData(V[] data);

    void addData(V data);

    void addAllData(V[] data);

    int length();

    V[] getData();

    V getData(int index);

    List<V> getDataList();

    void transformation(SquareMatrix<?, ?> mat);
}
