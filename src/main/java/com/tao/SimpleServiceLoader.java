package com.tao;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author TAO
 * @Date 2018/4/27 11:30
 */
public class SimpleServiceLoader<T> {
    private static final String PREFIX = "/META-INF/services/";

    public static <T> List<T> load(Class<T> clazz) throws IOException {
        //读取文件 返回集合类
        String name = clazz.getName();
        System.out.println("全类名"+name);
//        List<String> subClazz = readFile(clazz);
        List<String> subClazz = readFile(name);
        List<T> list = new ArrayList<T>();
        for(String iml:subClazz) {
            try {
                Class<T> aClass = (Class<T>) Class.forName(iml);
                try {
                    list.add(aClass.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<String> readFile(Class<?> clazz) throws IOException {
        String file = PREFIX+ clazz.getCanonicalName();
        System.out.println("file:"+file);
        //获取路径
        URL resource = clazz.getResource(file);
        String path = resource.getPath();
        System.out.println("path:"+path);
        List<String> strings = new ArrayList<>();
        try(FileReader fileReader = new FileReader(new File(path));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
                ){
            String s;
            while ((s = bufferedReader.readLine()) != null){
                strings.add(s);
            }
        }
        return strings;
    }

    private static final String PREFIX2 = "META-INF/services/";

    public static List<String> readFile(String name) throws IOException {
        //获取路径
        URL resource = Thread.currentThread().getContextClassLoader().getResource(PREFIX2+name);
        String path = resource.getPath();
        List<String> strings = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path));){
                String s;
                while ( (s =bufferedReader.readLine())!=null){
                    strings.add(s);
                }
        }
        return strings;
    }

    public static void main(String[] args) throws IOException {
        List<PrefixMatcher> implList = load(PrefixMatcher.class);
        if (implList != null && implList.size() >0) {
            for (PrefixMatcher matcher: implList) {
                matcher.hello("sh");
            }
        }
    }

}
