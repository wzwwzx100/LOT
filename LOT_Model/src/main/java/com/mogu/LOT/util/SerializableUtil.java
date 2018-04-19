package com.mogu.LOT.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by chang on 2016/12/20.
 */
public class SerializableUtil implements Serializable {

    private static final long serialVersionUID = 6101019361439897575L;

    private static Logger logger = LoggerFactory.getLogger(SerializableUtil.class);

    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (Exception e) {
            if (logger.isDebugEnabled())
                logger.error("Convert Object To ByteArray Failed!", e);
        }
        return bytes;
    }

    public static Object toObject(byte[] bytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object obj = ois.readObject();
            ois.close();
            bis.close();
            return obj;
        } catch (Exception e) {
            if (logger.isDebugEnabled())
                logger.error("Convert ByteArray To Object Failed!", e);
            return null;
        }
    }


}