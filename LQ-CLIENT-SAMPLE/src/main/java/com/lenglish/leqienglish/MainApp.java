package com.lenglish.leqienglish;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.leqienglish.entity.Mapper;
import com.leqienglish.entity.WhereIf;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;
import xyz.tobebetter.entity.english.content.ContentAndCatalog;
import xyz.tobebetter.entity.english.content.ReciteContentVO;
import xyz.tobebetter.entity.english.segment.WordAndSegment;
import xyz.tobebetter.entity.english.sentence.Sentence;
import xyz.tobebetter.entity.english.sentence.SentenceAndWord;
import xyz.tobebetter.entity.english.shortword.SegmentAndShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentence;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentenceVO;
import xyz.tobebetter.entity.english.shortword.ShortWordAndWord;
import xyz.tobebetter.entity.english.word.user.UserAndSegment;
import xyz.tobebetter.entity.play.SegmentPlayEntity;
import xyz.tobebetter.entity.suggestion.Suggestion;
import xyz.tobebetter.entity.todo.TodoItem;
import xyz.tobebetter.entity.todo.UserTodo;
import xyz.tobebetter.entity.user.User;
import xyz.tobebetter.entity.user.UserHearted;
import xyz.tobebetter.entity.user.content.UserAndContent;
import xyz.tobebetter.entity.user.recite.UserReciteRecord;
import xyz.tobebetter.entity.user.word.UserAndWord;
import xyz.tobebetter.entity.word.ReciteWordConfig;
import xyz.tobebetter.entity.word.Word;
import xyz.tobebetter.version.Version;

/**
 * 使用freeMarker，生成mybatis配置xml
 *
 * @author zhuqing
 */
public class MainApp {

    public static void main(String[] args) throws IOException, TemplateException {
        createMaper(Segment.class, Segment.class, "SEGMENT", "Base_Column_List");
    }

    public static void createMaper(Class entityClaz, Class voClaz, String tableName, String selectField) throws IOException, TemplateException {
        Mapper mapper = new Mapper();

        mapper.setParameterType(entityClaz.getName());
        mapper.setClassName(entityClaz.getSimpleName());
        mapper.setTable(tableName);
        mapper.setSelectField(selectField);
        mapper.setDaoClassPath(entityClaz.getName() + "Dao");

         List<Field> fields = getFields(entityClaz);
        
        mapper.setResultMap("BaseResultMap");
        mapper.setWheres(create(fields));
        mapper.setSwiftTypes(swiftTypes(entityClaz,fields));
        //  mapper.setWheresVo(create(getFields(voClaz)));
        selectEntity(mapper, "/mapper");
      //  System.err.println("========================");
       selectEntity(mapper, "/swiftmodel");
    }

    private static void selectEntity(Mapper select, String path) throws IOException, TemplateException {

        URL url = MainApp.class.getResource(path);
        String controllerStr = read(url);

        Template template = new Template(null, new StringReader(controllerStr), null);
        template.process(select, new OutputStreamWriter(System.out));
    }

    /**
     * 获取实体属性
     *
     * @param claz
     * @return
     */
    public static List<Field> getFields(Class claz) {
        if (Objects.equals(claz.getSimpleName(), "Object")) {
            return Collections.EMPTY_LIST;
        }

        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(claz.getDeclaredFields()));
        fields.addAll(getFields(claz.getSuperclass()));
        return fields;

    }

    private static List<WhereIf> swiftTypes(Class entityClaz, List<Field> fields) {
        return fields.stream()
                .filter((f) -> !Modifier.isStatic(f.getModifiers()))
                .filter((f) -> f.getDeclaringClass().equals(entityClaz))
                .map((f) -> {
                    WhereIf w = new WhereIf();
                    w.setFiledName(f.getName());
                    w.setType(getSwiftType(f.getType().getSimpleName()));
                   
                    return w;
                })
                .filter((w) -> {
                    if (w.getType() == null) {
                        System.err.println(w.getPropertyName());
                    }
                    return w.getType() != null;
                })
                .collect(Collectors.toList());
    }

    private static List<WhereIf> create(List<Field> fields) {

        return fields.stream()
                .filter((f) -> !Modifier.isStatic(f.getModifiers()))
                .map((f) -> {
                    WhereIf w = new WhereIf();
                    w.setFiledName(f.getName());
                    w.setType(getDBType(f.getType().getSimpleName()));
                    w.setPropertyName(getPropertyName(f.getName()));
                    return w;
                })
                .filter((w) -> {
                    if (w.getType() == null) {
                        System.err.println(w.getPropertyName());
                    }
                    return w.getType() != null;
                })
                .collect(Collectors.toList());

    }

    /**
     *
     * @param name
     * @return
     */
    public static String getPropertyName(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {

            char c = name.charAt(i);
            if (i == 0) {
                sb.append(c);
                continue;
            }
            if (c >= 'A' && c <= 'Z') {
                sb.append("_");
            }

            sb.append(c);
        }
        return sb.toString().toUpperCase();
    }
    
     public static String getSwiftType(String type) {
        switch (type) {
            case "String":
                return "String";
            case "int":
            case "Integer":
                return "Int";
            case "long":
            case "Long":
                return "Int64";
           
            default:
                return "??";
        }

       
    }

    public static String getDBType(String type) {
        switch (type) {
            case "String":
                return "VARCHAR";
            case "int":
            case "Integer":
                return "INTEGER";
            case "long":
            case "Long":
                return "BIGINT";
            case "double":
            case "Double":
                return "DECIMAL";
            case "Date":

                return "TIMESTAMP";
        }

        return null;
    }

    public static String read(URL file) {
        String s = null;
        InputStream in = null;
        try {
            in = file.openStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (in == null) {
            System.err.println("文件不存在!");
        } else {
            try {
                return inputStream2String(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n = 0; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n, "UTF-8"));
        }
        return out.toString();
    }
}
