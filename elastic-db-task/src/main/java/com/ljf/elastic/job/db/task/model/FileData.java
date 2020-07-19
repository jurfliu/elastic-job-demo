package com.ljf.elastic.job.db.task.model;

import lombok.Data;

@Data
public class FileData {
    /**
     * 标识
     */
    private String id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件类型，如text、image、radio、vedio
     */
    private String type;

    /**
     * 文件内容
     */
    private String content;

    /**
     * 是否已备份
     */
    private Boolean backedUp = false;

    public FileData(String id, String name, String type, String content){
        this.id = id;
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getBackedUp() {
        return backedUp;
    }

    public void setBackedUp(Boolean backedUp) {
        this.backedUp = backedUp;
    }
}
