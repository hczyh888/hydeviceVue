package com.gz.common.objects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class CascaderObject implements Serializable {
    private static final long serialVersionUID = 369285298572941L;  //最好是显式声明ID
    private String value;
    private String label;
    private List<CascaderObject> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<CascaderObject> getChildren() {
        return children;
    }

    public void setChildren(List<CascaderObject> children) {
        this.children = children;
    }
    public CascaderObject myclone(){
          CascaderObject cascaderObject = null;
          try { // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
                  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  ObjectOutputStream oos = new ObjectOutputStream(baos);
                  oos.writeObject(this);
                  // 将流序列化成对象
                   ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                  ObjectInputStream ois = new ObjectInputStream(bais);
              cascaderObject = (CascaderObject) ois.readObject();
              } catch (IOException e) {
                   e.printStackTrace();
              } catch (ClassNotFoundException e) {
                  e.printStackTrace();
               }
           return cascaderObject;
    }



}
