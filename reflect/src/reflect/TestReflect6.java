package reflect;

import java.lang.reflect.Method;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年1月30日 下午9:24:27
* 通过反射机制调用某个类的方法
*/
public class TestReflect6 {
	public static void main(String args[]) throws Exception{
		Class<?> clazz = Class.forName("reflect.TestReflect6");
		Method method = clazz.getMethod("reflect1");
		method.invoke(clazz.newInstance());
		
		method = clazz.getMethod("reflect2", int.class,String.class);
		method.invoke(clazz.newInstance(), 20,"张三");
	}
	
	public void reflect1(){
		System.out.println("Java 反射机制	- 调用某个类的方法1");
	}
	
	public void reflect2(int age,String name){
		System.out.println("Java 反射机制 - 调用某个类的方法2");
		System.out.println("age -> "+age+".name -> "+name);
	}
}
