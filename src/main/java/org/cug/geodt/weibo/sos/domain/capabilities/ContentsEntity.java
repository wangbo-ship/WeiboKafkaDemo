package org.cug.geodt.weibo.sos.domain.capabilities;


import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 9:56
 */
public class ContentsEntity {
    private List<OfferingEntity> offeringEntityList;

    public ContentsEntity() {
    }

    public ContentsEntity(List<OfferingEntity> offeringEntityList) {
        this.offeringEntityList = offeringEntityList;
    }

    public List<OfferingEntity> getOfferingEntityList() {
        return offeringEntityList;
    }

    public void setOfferingEntityList(List<OfferingEntity> offeringEntityList) {
        this.offeringEntityList = offeringEntityList;
    }
}
