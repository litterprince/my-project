package com.spring.service;

import com.spring.dao.FileMongoDao;
import com.spring.po.FileBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fileService")
public class FileService {
    @Autowired
    FileMongoDao fileMongoDao;

    public List<FileBean> findAll(){
        return fileMongoDao.findAll();
    }

    public List<FileBean> findList(int skip, int limit){
        return fileMongoDao.findList(skip, limit);
    }

    public FileBean findOne(String id){
        return fileMongoDao.findOne(id);
    }

    public List<FileBean> findByParam(FileBean FileBean){
        return fileMongoDao.findByParam(FileBean);
    }

    public void insertOne(FileBean FileBean){
        fileMongoDao.store(FileBean);
    }

    public void updateOne(FileBean FileBean){
        fileMongoDao.updateFirst(FileBean);
    }

    public void delete(String... ids){
        fileMongoDao.delete(ids);
    }
}
