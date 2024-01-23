package org.glcf2.vertex;

import org.glcf2.programobject.VBO;
import org.linear.main.matrix.Matrix4f;
import org.linear.main.vector.Vector3d;
import org.linear.main.vector.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ArrayModelFactory {
    private ArrayModelFactory() {}

    public static ArrayModel<Matrix4f, Vector4f> createVertexModel(Vector4f[] verticies) {
        ArrayModel4mf4vf re = new ArrayModel4mf4vf();
        re.addVertices(verticies);
        return re;
    }

    static class ArrayModel4mf4vf extends ArrayList<Vector4f> implements ArrayModel<Matrix4f, Vector4f>, Cloneable {
        private Vector4f anchor;
        private Matrix4f mat;

        private ArrayModel4mf4vf() {
            super();
            anchor = new Vector4f();
            mat = new Matrix4f(Matrix4f.DIAGONAL);
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> rotate(float radian) {
            mat.rotate(radian, anchor);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> rotateX(float value) {
            mat.rotateX(value);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> rotateY(float value) {
            mat.rotateY(value);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> rotateZ(float value) {
            mat.rotateZ(value);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> scale(float x, float y, float z) {
            mat.scale(x, y, z);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> scale(float value) {
            mat.scale(value);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> scaleX(float value) {
            mat.scaleX(value);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> scaleY(float value) {
            mat.scaleY(value);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> scaleZ(float value) {
            mat.scaleZ(value);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> shear(float x, float y, float z) {
            mat.shear(x, y, z);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> shearXY(float x, float y) {
            mat.shearXY(x, y);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> shearXZ(float x, float z) {
            mat.shearXZ(x, z);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> shearYZ(float y, float z) {
            mat.shearYZ(y, z);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> translate(float x, float y, float z) {
            mat.translate(x, y, z);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> translateX(float value) {
            mat.translateX(value);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> translateY(float value) {
            mat.translateY(value);
            return this;
        }

        @Override
        public ArrayModel<Matrix4f, Vector4f> translateZ(float value) {
            mat.translateZ(value);
            return this;
        }

        @Override
        public void setAnchor(Vector4f pos) {
            anchor.set(pos);
        }

        @Override
        public void addVertex(Vector4f vertex) {
            this.add(vertex);
        }

        @Override
        public void setVertex(int index, Vector4f vertex) {
            this.set(index, vertex);
        }

        @Override
        public void addVertices(Vector4f[] verticies) {
            this.addAll(Arrays.asList(verticies));
        }

        @Override
        public void set(ArrayModel<Matrix4f, Vector4f> copy) {
            this.clear();
            this.addAll(copy.getVerticiesList());
        }

        @Override
        public void setVertices(int start, Vector4f[] verticies) {
            for (int i = 0, len = verticies.length; i < len; i++) {
                this.set(i + start, verticies[i]);
            }
        }

        @Override
        public Matrix4f getMatrix() {
            return mat;
        }

        @Override
        public void setMatrix(Matrix4f mat) {
            this.mat.set(mat);
        }

        @Override
        public Vector4f getVertex(int index) {
            return this.get(index);
        }

        @Override
        public Vector4f[] getVerticies(int start, int offset) {
            Vector4f[] re = new Vector4f[offset - start];
            for (int i = 0, len = re.length; i < len; i++) {
                re[i] = this.get(start + i);
            }
            return re;
        }

        @Override
        public List<Vector4f> getVerticiesList(int start, int offset) {
            List<Vector4f> re = new ArrayList<>(Arrays.asList(new Vector4f[offset - start]));
            for (int i = 0, len = re.size(); i < len; i++) {
                re.set(i, this.get(start + i));
            }
            return re;
        }

        @Override
        public void transformation() {
            this.forEach(mat::transformation);
        }

        @Override
        public Vector3d[] cutArray() {
            Vector3d[] vs = new Vector3d[size()];
            Vector4f tmp;
            for (int i = 0, len = vs.length; i < len; i++) {
                tmp = this.get(i);
                vs[i] = new Vector3d(tmp.x, tmp.y, tmp.z);
            }
            return vs;
        }

        @Override
        public ArrayModel4mf4vf clone() {
            ArrayModel4mf4vf re = (ArrayModel4mf4vf) super.clone();
            re.anchor = this.anchor.clone();
            re.mat = this.mat.clone();

            return re;
        }
    }
}
