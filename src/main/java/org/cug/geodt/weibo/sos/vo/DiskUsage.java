package org.cug.geodt.weibo.sos.vo;


/**
 * @FileName DiskUsage
 * @Author WJW
 * @Date 2023/10/6 11:18
 * @Description
 */
public class DiskUsage {
    private String status;
    private Component components;

    public DiskUsage() {
    }

    public DiskUsage(String status, Component components) {
        this.status = status;
        this.components = components;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Component getComponents() {
        return components;
    }

    public void setComponents(Component components) {
        this.components = components;
    }
}

class Component {
    private Db db;
    private DiskSpace diskSpace;

    public Component() {
    }

    public Component(Db db, DiskSpace diskSpace) {
        this.db = db;
        this.diskSpace = diskSpace;
    }

    public Db getDb() {
        return db;
    }

    public void setDb(Db db) {
        this.db = db;
    }

    public DiskSpace getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(DiskSpace diskSpace) {
        this.diskSpace = diskSpace;
    }
}

class Db {
    private String status;
    private Details details;

    public Db() {
    }

    public Db(String status, Details details) {
        this.status = status;
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}

class Details {
    private String database;
    private String validationQuery;

    public Details() {
    }

    public Details(String database, String validationQuery) {
        this.database = database;
        this.validationQuery = validationQuery;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }
}

class DiskSpace {
    private String status;
    private Detail details;

    public DiskSpace() {
    }

    public DiskSpace(String status, Detail details) {
        this.status = status;
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Detail getDetails() {
        return details;
    }

    public void setDetails(Detail details) {
        this.details = details;
    }
}

class Detail {
    private Long total;
    private Long free;
    private Long threshold;
    private Boolean exists;

    public Detail() {
    }

    public Detail(Long total, Long free, Long threshold, Boolean exists) {
        this.total = total;
        this.free = free;
        this.threshold = threshold;
        this.exists = exists;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getFree() {
        return free;
    }

    public void setFree(Long free) {
        this.free = free;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }
}

class Ping {
    private String status;

    public Ping(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
