package reflect;

import java.lang.reflect.Field;

/**
* @author ���� ybliu:
* @version ����ʱ�䣺2017��1��30�� ����9:32:08
* ͨ��������Ʋ���ĳ���������
*/
public class TestReflect7 {
	private String property = null;
	public static void main(String args[]) throws Exception{
		Class<?> clazz = Class.forName("reflect.TestReflect7");
		Object obj = clazz.newInstance();
		
		Field field = clazz.getDeclaredField("property");
		field.setAccessible(true);
		field.set(obj, "java�������");
		System.out.println(field.get(obj));
	}
}
