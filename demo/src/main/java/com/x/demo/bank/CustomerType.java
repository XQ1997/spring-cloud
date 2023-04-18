package com.x.demo.bank;

/**
 * @ClassName CustomerType
 * @Description 系统中有三种类型的客户，所以用定义一个枚举类，
 * 其中定义三个成员分别表示三种类 型的客户。 重写toString方法，返回类型的中文名称。
 * 这是在后面编码时重构出来的，刚开始不用 考虑。
 * @Author X
 * @Date 2022/8/12 11:20
 * @Version 1.0
 */
public  enum  CustomerType  {
    COMMON,EXPRESS,VIP;
    public  String  toString(){
        String  name  =  null;
        switch(this){
            case  COMMON:
                name  =  "普通";
                break;
            case  EXPRESS:
                name  =  "快速";
                break;
            case  VIP:
                name  =  name();
                break;
        }
        return  name;
    }
}
