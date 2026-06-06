package org.cug.geodt.weibo.sos.utils;


import org.cug.geodt.weibo.sos.pojo.json.RenderType;
import org.cug.geodt.weibo.sos.pojo.json.Renders;

import java.util.LinkedList;
import java.util.List;

public class RendersEncoder {
    public static  List<Renders> rendersDataset(String type, String xAxis, String yAxis, Boolean defaultType){
        Renders renders = new Renders();
        List<Renders> rendersList = new LinkedList<Renders>();
        RenderType renderType = new RenderType();
        renderType.setType(type);
        renderType.setxAxis(xAxis);
        renderType.setyAxis(yAxis);
        renders.setRenderType(renderType);
        renders.setDefaultType(defaultType);
        rendersList.add(renders);
        return rendersList;
    }
}
