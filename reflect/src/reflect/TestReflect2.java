package reflect;

import java.io.Serializable;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年1月30日 下午8:22:52
* 获取一个对象的父类与实现的接口
*/
public class TestReflect2 implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) throws Exception{
		Class<?> clazz = Class.forName("reflect.TestReflect2");
		
		//取得父类
		Class<?> parentClass = clazz.getSuperclass();
		System.out.println("clazz的父类是:"+parentClass.getName());
		
		//获取所有接口
		Class<?> intes[] = clazz.getInterfaces();
		System.out.println("clazz实现接口有:");
		for(int i = 0;i<intes.length;i++){
			System.out.println((i+1)+":"+intes[i].getName());
		}
	}

}
