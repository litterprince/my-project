package com.spring.po;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "image")
public class FileBean {
    @Id
    private String id;
    private Binary imagecontent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Binary getImagecontent() {
        return imagecontent;
    }

    public void setImagecontent(Binary imagecontent) {
        this.imagecontent = imagecontent;
    }
}
