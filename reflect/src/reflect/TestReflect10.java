package reflect;

import java.lang.reflect.Array;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年1月30日 下午9:55:35
* 通过反射取得并修改数组的信息
*/
public class TestReflect10 {

	public static void main(String[] args) {
		int[] temp = {1,2,3,4,5};
		Class<?> demo = temp.getClass().getComponentType();
		System.out.println("数组类型:"+demo.getName());
		System.out.println("数组长度:"+Array.getLength(temp));
		System.out.println("数组的第一个元素:"+Array.get(temp,0));
		Array.set(temp, 0,100);
		System.out.println("修改后第一个数组元素:"+Array.get(temp,0));
	}

}
