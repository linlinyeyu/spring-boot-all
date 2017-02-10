package reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年1月30日 下午9:51:37
* 类说明
*/
public class TestReflect9 {
	public static void main(String args[]) throws Exception{
		ArrayList<Integer> list = new ArrayList<Integer>();
		Method method = list.getClass().getMethod("add", Object.class);
		method.invoke(list, "java反射机制实例");
		System.out.println(list.get(0));
	}
}
