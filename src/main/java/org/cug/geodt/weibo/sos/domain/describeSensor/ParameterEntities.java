package org.cug.geodt.weibo.sos.domain.describeSensor;

import org.cug.geodt.weibo.sos.domain.observation.QuantityEntity;

/**
 * Author WJW
 * Date 2023/6/12 16:36
 */
public class ParameterEntities {
    private String name;
    private BooleanEntity booleanEntity;
    private QuantityEntity quantityEntity;
    private CountEntity countEntity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BooleanEntity getBooleanEntity() {
        return booleanEntity;
    }

    public void setBooleanEntity(BooleanEntity booleanEntity) {
        this.booleanEntity = booleanEntity;
    }

    public QuantityEntity getQuantityEntity() {
        return quantityEntity;
    }

    public void setQuantityEntity(QuantityEntity quantityEntity) {
        this.quantityEntity = quantityEntity;
    }

    public CountEntity getCountEntity() {
        return countEntity;
    }

    public void setCountEntity(CountEntity countEntity) {
        this.countEntity = countEntity;
    }

    public ParameterEntities() {
    }

    public ParameterEntities(String name, BooleanEntity booleanEntity, QuantityEntity quantityEntity, CountEntity countEntity) {
        this.name = name;
        this.booleanEntity = booleanEntity;
        this.quantityEntity = quantityEntity;
        this.countEntity = countEntity;
    }
}
