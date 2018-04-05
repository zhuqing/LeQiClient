/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjgoodwill.hip.util.xml;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 * @author zhuqing
 */
public class XMLUtil {

    public static Element getFirstElement(String xml, String... tagNames) {
        try {
            XMLFactory xmlFactory = new XMLFactory(xml);
            List<Element> elements = xmlFactory.getElements(tagNames);
            if (elements == null || elements.isEmpty()) {
                return null;
            }

            return elements.iterator().next();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static List<Element> getAllChildren(List<Element> elements) {
        List<Element> list = new ArrayList<>();
        for (Element element : elements) {
            list.addAll(element.elements());
        }

        return list;
    }

    public static List<Element> getElements(String tagName, List<Element> elements) {
        elements = getAllChildren(elements);
        List<Element> filtered = new ArrayList<>();
        for (Element element : elements) {
            if (Objects.equals(element.getName(), tagName)) {
                filtered.add(element);
            }
        }
        return filtered;
    }

    public static List<Element> getElements(String childTagName, Element element) {
        return getElements(childTagName, element.elements());
    }

    public static String getValue(Element element, String attribute) {
        if (element == null || element.attribute(attribute) == null) {
            return null;
        }
        return element.attribute(attribute).getValue();
    }

    public static Element createElement(String tagName, Map<String, String> attributes) {
        Document document = DocumentHelper.createDocument();
        Element element = document.addElement(tagName);
        if (attributes == null || attributes.isEmpty()) {
            return element;
        }
        for (String key : attributes.keySet()) {
            element.addAttribute(key, attributes.get(key));
        }

        return element;
    }

    /**
     * 替换或者新加子节点
     *
     * @param parentElement
     * @param attributes
     * @param subElement
     */
    public static void replaceOrAddElement(Element parentElement, Map<String, String> attributes, Element subElement) {
        int index = removeElement(parentElement, attributes);
        if (index < 0) {
            parentElement.add(subElement);
            return;
        } else {
            List<Element> lists = parentElement.elements();
            lists.add(index, subElement);

        }

    }

    /**
     * 根据条件删除子节点
     *
     * @param parentElement
     * @param attributes
     * @return
     */
    public static int removeElement(Element parentElement, Map<String, String> attributes) {
        List<Element> lists = parentElement.elements();

        int index = -1;
        Element removedElement = null;
        for (Element element : lists) {
            index++;
            boolean isfind = true;
            for (String key : attributes.keySet()) {
                String value = XMLUtil.getValue(element, key);
                if (value == null) {
                    isfind = false;
                    break;
                }

                if (!Objects.equals(value, attributes.get(key))) {
                    isfind = false;
                    break;
                }
            }

            if (isfind) {
                removedElement = element;
                break;
            }

        }

        if (removedElement != null) {
            parentElement.remove(removedElement);
            return index;
        } else {
            return -1;
        }
    }
}
