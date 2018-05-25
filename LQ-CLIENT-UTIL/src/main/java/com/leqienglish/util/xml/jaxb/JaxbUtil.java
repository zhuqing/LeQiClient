package com.leqienglish.util.xml.jaxb;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jaxb工具类，实现类与xml转换。
 *
 * @author duyi
 */
public class JaxbUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JaxbUtil.class);
    /**
     * xml默认编码
     */
    private static final String ENCODING = "UTF-8";

    /**
     * javaBean转换为xml字符串
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        try {
            return convertToXml(obj, ENCODING);
        } catch (IOException ex) {
            LOGGER.error("xml转换为javaBean失败！！！", ex);
        }
        return "";
    }

    public static String convertToXml(Object obj, String encoding) throws IOException {
        String result = null;
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(obj, writer);
            result = writer.toString();

        } catch (Exception ex) {
            LOGGER.error("xml转换为javaBean失败！！！", ex);
        } finally {
            writer.close();
        }
        return result;
    }

    /**
     * xml字符串转换为javaBean
     *
     * @param <T>
     * @param xml
     * @param c
     * @return
     */
    public static <T> T converyToJavaBean(String xml, Class<T> c) {
        return converyToJavaBean(xml, c, ENCODING);
    }

    public static <T> T converyToJavaBean(String xml, Class<T> c, String encoding) {
        T t = null;
        StringReader stringReader = new StringReader(xml);
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(stringReader);
        } catch (Exception ex) {
            LOGGER.error("javaBean转换为xml失败！！！", ex);
        } finally {
            stringReader.close();
        }
        return t;
    }

}
