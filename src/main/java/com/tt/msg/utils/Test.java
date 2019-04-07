package com.tt.msg.utils;

import java.io.File;
import java.util.*;

/**
 * @ClassName Test
 * @Description TODO
 * @Author tanjiang
 * @CreateTime 2019/2/27 10:47
 * @Version 1.0
 **/

public class Test {
    public static void main(String[] args) {
//        Scanner s = new Scanner(System.in);
//        String str = s.next();
//        String[] arr = str.split(",");
//        List<String> lists = Arrays.asList(arr);
//        List<Integer> l = new ArrayList<Integer>();
//        for (String st : lists){
//            l.add(Integer.valueOf(st));
//        }
//        String s1 = "qw";
//        String s2 = "qya";
//        System.out.println(s1.compareTo(s2));
//
//        double d1 = 3.2;
//        double d2 = 3.1;
//        int i = 3;
//        System.out.println(d1%i);
//
//        Set set = new HashSet();
//        set.add("a");
//        set.add("b");
//        set.add("c");
//        set.add("a");
//        System.out.println(set);
//        System.out.println();
//
//        short s1 = 1;
//        s1 = (short) (s1 + 1);
//
//        short s2 = 1;
//        s2 += 1;
//
//        System.out.println(s1+"============"+s2);
//
//        System.out.println(Math.round(11.5)+"====================="+Math.round(-11.5));
//
//        String a = "aa";
//        String b = "aa";
//        String c = new String("aa");
//        System.out.println(a == b);
//        System.out.println(a == c);
//
//        //switch()变量类型只能是int、short、char、byte和enum类型
//        int i = 0;
//        switch(i){
//            case 0:
//                System.out.println("0");
//            case 1:
//                System.out.println("1");
//            case 2:
//                System.out.println("2");
//            default:
//                System.out.println("default");
//        }
//
//        Set<Character> characters = new LinkedHashSet<Character>();
//        characters.add('b');
//        characters.add('d');
//        characters.add('c');
//        characters.add('c');
//        characters.add('a');
//        System.out.println(characters);
//        ArrayList<Integer> list = new ArrayList<Integer>();
//        list.add(1);
//        list.add(2);
//        list.add(4);
//        list.add(-4);
//        list.add(4);
//        list.add(5);
//        list.add(-5);
//        list.add(5);
//        list.add(5);
//        list.add(7);
//        list.add(-7);
//        list.add(-8);
//        list.add(9);
//        int t = 0;
//        for(int i = 0;i<list.size();i++){
//            if(list.get(i)%2 == 0){
//                list.remove(i);
//                i--;
//            }else {
//                if(t != 0){
//                    System.out.print(",");
//                }
//                System.out.print(list.get(i));
//                t++;
//            }
//        }

//        HashMap<String, Object> map = new HashMap<String, Object>();
//        if (map.get("1") == null){
//            System.out.println("you are right!");
//        }else {
//            System.out.println("you are wrong!");
//            System.out.println(map);
//        }
//        Integer code = (Integer)map.get("RETCODE");
//        if(code == null){
//            System.out.println("哈哈哈哈哈哈哈哈哈哈哈哈哈");
//        }

        File f = new File("G:\\哈哈\\test\\20140729_185240\\20140729_185240.gdr.png");
        System.out.println(f.getName());
        System.out.println(f.getAbsolutePath());
        System.out.println(new Date());

    }

}
