package reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
* @author ���� ybliu:
* @version ����ʱ�䣺2017��1��30�� ����9:51:37
* ��˵��
*/
public class TestReflect9 {
	public static void main(String args[]) throws Exception{
		ArrayList<Integer> list = new ArrayList<Integer>();
		Method method = list.getClass().getMethod("add", Object.class);
		method.invoke(list, "java�������ʵ��");
		System.out.println(list.get(0));
	}
}
