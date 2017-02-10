package reflect;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年1月30日 下午8:55:43
* 获取某个类的全部属性
*/
public class TestReflect4 implements Serializable{
	private static final long serialVersionUID = 2191202777173416986L;

	public static void main(String args[]) throws Exception{
		Class<?> clazz = Class.forName("reflect.TestReflect4");
		System.out.println("============本类属性=============");
		//取得本类的全部属性
		Field[] field = clazz.getDeclaredFields();
		for(int i =0;i<field.length;i++){
			//权限修饰符
			int mo = field[i].getModifiers();
			String priv = Modifier.toString(mo);
			//属性类型
			Class<?> type = field[i].getType();
			System.out.println(priv+""+type.getName()+""+field[i].getName()+";");
		}
		System.out.println("============实现的接口或者父类的属性=================");
		//取得实现的接口或者父类的属性
		Field[] field1 = clazz.getFields();
		for(int j=0;j<field1.length;j++){
			//权限修饰符
			int mo = field1[j].getModifiers();
			String priv = Modifier.toString(mo);
			
			//属性类型
			Class<?> type = field[j].getType();
			System.out.println(priv+""+type.getName()+""+field1[j].getName()+";");
		}
	}
}
