package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.entity.IEventListener;

import java.util.Collection;

public interface IGraphics extends IEventListener {
    public void setGraphics();
    public void renderObjects(Collection<RenderObject> renderObjects);
    public void dispose();
}
