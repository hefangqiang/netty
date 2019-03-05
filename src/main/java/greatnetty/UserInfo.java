package greatnetty;


import org.msgpack.annotation.Message;

/**
 * @description： TODO
 * @author： Mr.He
 * @date： 2019-03-04 14:58
 **/
@Message
public class UserInfo {
   private  String name;
   private  String sex;
   private  int age;

    public UserInfo() { //默认构造参数一定要 不然messagePack报错
    }

    public UserInfo(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
