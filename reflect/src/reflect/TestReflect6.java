package reflect;

import java.lang.reflect.Method;

/**
* @author ���� ybliu:
* @version ����ʱ�䣺2017��1��30�� ����9:24:27
* ͨ��������Ƶ���ĳ����ķ���
*/
public class TestReflect6 {
	public static void main(String args[]) throws Exception{
		Class<?> clazz = Class.forName("reflect.TestReflect6");
		Method method = clazz.getMethod("reflect1");
		method.invoke(clazz.newInstance());
		
		method = clazz.getMethod("reflect2", int.class,String.class);
		method.invoke(clazz.newInstance(), 20,"����");
	}
	
	public void reflect1(){
		System.out.println("Java �������	- ����ĳ����ķ���1");
	}
	
	public void reflect2(int age,String name){
		System.out.println("Java ������� - ����ĳ����ķ���2");
		System.out.println("age -> "+age+".name -> "+name);
	}
}
