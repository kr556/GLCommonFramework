package org.glcf3.gl;

import org.glcf3.Model;
import org.glcf3.ModelData;
import org.glcf3.Shader;
import org.glcf3.gl.Vertex;
import org.glcf3.gl.prg.DrawingMode;
import org.linear.main.matrix.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GLModel extends Model implements ModelData<Vertex> {
    private DrawingMode drawingMode;
    private Shader shader;
    private ModelData<Vertex> vertexModel;

    public GLModel() {
        drawingMode = DrawingMode.TRIANGLES;
    }

    public GLModel(Vertex... verticies) {
        vertexModel = new GLModelData(verticies);
    }

    public void setVerticies(Vertex[] vertex) {
        vertexModel.setData(vertex);
    }

    @Override
    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void setDrawingMode(DrawingMode drawingMode) {
        this.drawingMode = drawingMode;
    }

    public DrawingMode getDrawingMode() {
        return drawingMode;
    }

    public Shader getShader() {
        return shader;
    }

    private static class GLModelData implements ModelData<Vertex> {
        private List<Vertex> verticies;

        public GLModelData(int initializeCapacity) {
            verticies = new ArrayList<>(initializeCapacity);
        }

        public GLModelData(Vertex... vertices) {
            this((int) (vertices.length * 1.1));
            addAllData(vertices);
        }

        @Override
        public void setData(int index, Vertex data) {
            verticies.set(index, data);
        }

        @Override
        public void setData(Vertex[] data) {
            for (int i = 0; i < data.length; i++) {
                verticies.set(i, data[i]);
            }
        }

        @Override
        public void addData(Vertex data) {
            verticies.add(data);
        }

        @Override
        public void addAllData(Vertex[] data) {
            verticies.addAll(Arrays.asList(data));
        }

        @Override
        public int length() {
            return verticies.size();
        }

        @Override
        public Vertex[] getData() {
            return verticies.toArray(new Vertex[0]);
        }

        @Override
        public Vertex getData(int index) {
            return verticies.get(index);
        }

        @Override
        public List<Vertex> getDataList() {
            return verticies;
        }

        @Override
        public void transformation(SquareMatrix<?, ?> mat) {
            if (mat instanceof Matrix4f) {
                verticies.forEach(v -> MatrixUtil.transformation((Matrix4f) mat, v));
            } else {
                throw new UnsupportedOperationException(" : Mismatch vector and matrix dimension");
            }
        }
    }
}
