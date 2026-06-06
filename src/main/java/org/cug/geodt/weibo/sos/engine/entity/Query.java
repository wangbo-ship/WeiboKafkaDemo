package org.cug.geodt.weibo.sos.engine.entity;

import lombok.NoArgsConstructor;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.dto
 * @Description
 * @date 2022/12/23 22:12
 */
@NoArgsConstructor
public class Query {
    private String baseDataIdentifier;

    private DeriveDataSpec deriveDataSpec;


    public Query(String baseDataIdentifier, DeriveDataSpec deriveDataSpec) {
        this.baseDataIdentifier = baseDataIdentifier;
        this.deriveDataSpec = deriveDataSpec;
    }

    public String getBaseDataIdentifier() {
        return baseDataIdentifier;
    }

    public void setBaseDataIdentifier(String baseDataIdentifier) {
        this.baseDataIdentifier = baseDataIdentifier;
    }

    public DeriveDataSpec getDeriveDataSpec() {
        return deriveDataSpec;
    }

    public void setDeriveDataSpec(DeriveDataSpec deriveDataSpec) {
        this.deriveDataSpec = deriveDataSpec;
    }

    @Override
    public String toString() {
        return "Query{" +
                "baseDataIdentifier='" + baseDataIdentifier + '\'' +
                ", deriveDataSpec=" + deriveDataSpec +
                '}';
    }
}

