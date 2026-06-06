package org.cug.geodt.weibo.sos.vo;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * @FileName CswMetaDataOnSos
 * @Author WJW
 * @Date 2023/9/25 20:30
 * @Description
 */
@Component
public class CswMetaDataOnSos {
    private int entryNumbers;
    private String entrySize;
    private List<SchemaInfo> schemaInfoList;
    private String dataChunkInfo;
    private String spatialResolutionInfo;
    private String crs;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date uploadDate;

    public int getEntryNumbers() {
        return entryNumbers;
    }

    public void setEntryNumbers(int entryNumbers) {
        this.entryNumbers = entryNumbers;
    }


    public List<SchemaInfo> getSchemaInfoList() {
        return schemaInfoList;
    }

    public void setSchemaInfoList(List<SchemaInfo> schemaInfoList) {
        this.schemaInfoList = schemaInfoList;
    }

    public String getDataChunkInfo() {
        return dataChunkInfo;
    }

    public void setDataChunkInfo(String dataChunkInfo) {
        this.dataChunkInfo = dataChunkInfo;
    }

    public String getSpatialResolutionInfo() {
        return spatialResolutionInfo;
    }

    public void setSpatialResolutionInfo(String spatialResolutionInfo) {
        this.spatialResolutionInfo = spatialResolutionInfo;
    }

    public String getCrs() {
        return crs;
    }

    public void setCrs(String crs) {
        this.crs = crs;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public CswMetaDataOnSos() {
    }

    public String getEntrySize() {
        return entrySize;
    }

    public void setEntrySize(String entrySize) {
        this.entrySize = entrySize;
    }

    public CswMetaDataOnSos(int entryNumbers, String entrySize, List<SchemaInfo> schemaInfoList, String dataChunkInfo, String spatialResolutionInfo, String crs, Date uploadDate) {
        this.entryNumbers = entryNumbers;
        this.entrySize = entrySize;
        this.schemaInfoList = schemaInfoList;
        this.dataChunkInfo = dataChunkInfo;
        this.spatialResolutionInfo = spatialResolutionInfo;
        this.crs = crs;
        this.uploadDate = uploadDate;
    }
}
