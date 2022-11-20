package ru.mipt.bit.platformer.graphics;

public class GraphicPointer {
    IGraphics source;

    public GraphicPointer() {
    }

    public GraphicPointer(IGraphics source) {
        this.source = source;
    }

    public IGraphics getSource() {
        return source;
    }

    public void setSource(IGraphics source) {
        this.source = source;
    }
}
