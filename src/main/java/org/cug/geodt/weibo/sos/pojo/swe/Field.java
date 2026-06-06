package org.cug.geodt.weibo.sos.pojo.swe;

import net.opengis.swe.x20.*;
import org.apache.xmlbeans.XmlObject;

/**
 * Author WJW
 * Date 2023/5/25 15:09
 */
public class Field {

    private AnyScalarPropertyType anyScalarPropertyType;
    private QuantityType quantityType;
    private UnitReference unitReference;
    private DataRecordType dataRecordType;
    private DataRecordType.Field field;
    private AbstractDataComponentType abstractDataComponentType;

    public Field() {
        dataRecordType = DataRecordType.Factory.newInstance();
        field = dataRecordType.addNewField();
        anyScalarPropertyType = AnyScalarPropertyType.Factory.newInstance();
        quantityType = QuantityType.Factory.newInstance();
        unitReference = UnitReference.Factory.newInstance();
        abstractDataComponentType = field.addNewAbstractDataComponent();
        XmlObject xmlObject = this.abstractDataComponentType.addNewExtension();
    }

    public void setName(String name) {
        field.setName(name);
    }

    public void setQuantityType(String definition, String code) {
        quantityType.setDefinition(definition);
        unitReference.setCode(code);
        quantityType.setUom(unitReference);
        anyScalarPropertyType.setQuantity(quantityType);


    }

    public void setTime(String name) {
        TimeType timeType = TimeType.Factory.newInstance();
        timeType.setDefinition(name);
        timeType.setDefinition(name);
        anyScalarPropertyType.setTime(timeType);
    }


    public DataRecordType.Field getField() {
        abstractDataComponentType.set(anyScalarPropertyType);
        field.setAbstractDataComponent(abstractDataComponentType);
        return field;
    }

    public void setField(DataRecordType.Field field) {
        this.field = field;
    }
    public AnyScalarPropertyType getAnyScalarPropertyType() {
        return anyScalarPropertyType;
    }

    public void setAnyScalarPropertyType(AnyScalarPropertyType anyScalarPropertyType) {
        this.anyScalarPropertyType = anyScalarPropertyType;
    }

    public QuantityType getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(QuantityType quantityType) {
        this.quantityType = quantityType;
    }

    public UnitReference getUnitReference() {
        return unitReference;
    }

    public void setUnitReference(UnitReference unitReference) {
        this.unitReference = unitReference;
    }

}
