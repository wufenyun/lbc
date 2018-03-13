package com.lbc.refresh.event.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * 自定义序列化工具类
 * Date: 2018年3月13日 下午6:53:42
 * @author wufenyun 
 */
public class LbcZkSerializer implements ZkSerializer {

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        if(bytes==null) throw new NullPointerException();  
        
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);  
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(is);
            return in.readObject();  
        } catch (IOException | ClassNotFoundException e) {
            throw new ZkMarshallingError(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       
    }
    
    @Override
    public byte[] serialize(Object obj) throws ZkMarshallingError {
        if(obj==null) throw new NullPointerException();  
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        ObjectOutputStream out=null;
        try {
            out = new ObjectOutputStream(os);
            out.writeObject(obj);  
            return os.toByteArray(); 
        } catch (IOException e) {
            throw new ZkMarshallingError(e);
        }  finally {
            try {
                if(null != out) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         
    }
}