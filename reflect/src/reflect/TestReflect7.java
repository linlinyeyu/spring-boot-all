package reflect;

import java.lang.reflect.Field;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年1月30日 下午9:32:08
* 通过反射机制操作某个类的属性
*/
public class TestReflect7 {
	private String property = null;
	public static void main(String args[]) throws Exception{
		Class<?> clazz = Class.forName("reflect.TestReflect7");
		Object obj = clazz.newInstance();
		
		Field field = clazz.getDeclaredField("property");
		field.setAccessible(true);
		field.set(obj, "java反射机制");
		System.out.println(field.get(obj));
	}
}
