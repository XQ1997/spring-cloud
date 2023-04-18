package com.x.test;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class Test {

    public static void main(String[] args) throws IllegalAccessException {
        Student student = new Student("小明", "男", "15011112222", "重庆");
        User user = new User("小明", "admin", "150111122221", "重庆");

        Map<String, String> diffMap = compareTwoObject(student, user, new String[]{"addr"});
        System.out.println(diffMap);
        System.out.println(diffMap.size());
        // 运行结果 {compare_object: =Student vs User, Student_field:phone=15011112222, User_field:phone=15811112222}
    }

    @SneakyThrows
    public static Map<String, String> compareTwoObject(Object obj1, Object obj2, String[] ignoreFields){
        Map<String, String> diffMap = new LinkedHashMap<>();
        List<String> ignoreFieldList = Arrays.asList(ignoreFields);
        Class<?> clazz1 = obj1.getClass();
        Class<?> clazz2 = obj2.getClass();
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();
        BiPredicate biPredicate = new BiPredicate() {
            @Override
            public boolean test(Object object1, Object object2) {
                if (object1 == null && object2 == null) {
                    return true;
                }
                if (object1 == null && object2 != null){
                    return false;
                }
                // 假如还有别的类型需要特殊判断 比如 BigDecimal, 演示，只写BigDecimal示例，其他都相似
                if (object1 instanceof BigDecimal && object2 instanceof BigDecimal) {
                    if (object1 == null && object2 == null) {
                        return true;
                    }
                    if (object1 == null ^ object2 == null) {
                        return false;
                    }
                    return ((BigDecimal) object1).compareTo((BigDecimal) object2) == 0;
                }

                if (object1.equals(object2)) {
                    return true;
                }
                return false;
            }
        };

        for (Field field1 : fields1) {
            for (Field field2 : fields2) {
                if (ignoreFieldList.contains(field1.getName()) || ignoreFieldList.contains(field2.getName())) {
                    continue;
                }
                if (field1.getName().equals(field2.getName())) {
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    if (!biPredicate.test(field1.get(obj1), field2.get(obj2))) {
                        diffMap.put("compare_object: ", obj1 + " vs " + obj2);
                        diffMap.put(obj1 + "_field:" + field1.getName(), field1.get(obj1).toString());
                        diffMap.put(obj2 + "_field:" + field2.getName(), field2.get(obj2).toString());
                    }
                }
            }
        }
        return diffMap;
    }
}

class Student {
    private String name;
    private String gender;
    private String phone;
    private String addr;

    public Student(String name, String gender, String phone, String addr) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "Student";
    }
}

class User {
    private String name;
    private String password;
    private String phone;
    private String addr;

    public User(String name, String password, String phone, String addr) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "User";
    }
}
