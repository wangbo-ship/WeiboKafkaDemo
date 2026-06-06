package org.cug.geodt.weibo.sos.pojo.gml;

import net.opengis.gml.x32.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WJW
 * Date 2023/5/24 21:35
 */
public class Polygon {

    private PolygonDocument polygonDocument;
    private PolygonType polygonType;
//    private AbstractRingPropertyType abstractRingPropertyType;  //设置观测区域的范围
//    private AbstractRingType abstractRingType;
//    private LinearRingType linearRingType;


    public Polygon() {
        polygonDocument = PolygonDocument.Factory.newInstance();
        polygonType = PolygonType.Factory.newInstance();

    }

    public void setExterior(List<Double> posList) {
        AbstractRingPropertyType abstractRingPropertyType = polygonType.addNewExterior();
        LinearRingType linearRingType = LinearRingType.Factory.newInstance();
        DirectPositionListType directPositionListType = DirectPositionListType.Factory.newInstance();
        directPositionListType.setListValue(posList);
        linearRingType.setDirectPosList(directPositionListType);
        abstractRingPropertyType.set(linearRingType);
        polygonType.setExterior(abstractRingPropertyType);
    }

    public void setInteriorArray(ArrayList<List<Double>> posListArrayList) {
        AbstractRingPropertyType abstractRingPropertyType = polygonType.addNewExterior();
        LinearRingType linearRingType = LinearRingType.Factory.newInstance();
        ArrayList<AbstractRingPropertyType> abstractRingPropertyTypeArrayList = new ArrayList<>();
        posListArrayList.forEach((posList) ->
        {DirectPositionListType directPositionListType = DirectPositionListType.Factory.newInstance();
            directPositionListType.setListValue(posList);
            linearRingType.setDirectPosList(directPositionListType);
            abstractRingPropertyType.setAbstractRing(linearRingType);
            abstractRingPropertyTypeArrayList.add(abstractRingPropertyType);});
        AbstractRingPropertyType[] abstractRingPropertyTypeArray = abstractRingPropertyTypeArrayList.toArray(new AbstractRingPropertyType[abstractRingPropertyTypeArrayList.size()]);
        polygonType.setInteriorArray(abstractRingPropertyTypeArray);
    }

    public PolygonDocument getPolygonDocument() {
        polygonDocument.setPolygon(polygonType);
        return polygonDocument;
    }

    public void setPolygonDocument(PolygonDocument polygonDocument) {
        this.polygonDocument = polygonDocument;
    }

    public PolygonType getPolygonType() {
        return polygonType;
    }

    public void setPolygonType(PolygonType polygonType) {
        this.polygonType = polygonType;
    }

//    public AbstractRingPropertyType getAbstractRingPropertyType() {
//        return abstractRingPropertyType;
//    }
//
//    public void setAbstractRingPropertyType(AbstractRingPropertyType abstractRingPropertyType) {
//        this.abstractRingPropertyType = abstractRingPropertyType;
//    }
//
//    public AbstractRingType getAbstractRingType() {
//        return abstractRingType;
//    }
//
//    public void setAbstractRingType(AbstractRingType abstractRingType) {
//        this.abstractRingType = abstractRingType;
//    }
//
//    public LinearRingType getLinearRingType() {
//        return linearRingType;
//    }
//
//    public void setLinearRingType(LinearRingType linearRingType) {
//        this.linearRingType = linearRingType;
//    }
}
