package reflect;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
* @author ���� ybliu:
* @version ����ʱ�䣺2017��1��30�� ����8:55:43
* ��ȡĳ�����ȫ������
*/
public class TestReflect4 implements Serializable{
	private static final long serialVersionUID = 2191202777173416986L;

	public static void main(String args[]) throws Exception{
		Class<?> clazz = Class.forName("reflect.TestReflect4");
		System.out.println("============��������=============");
		//ȡ�ñ����ȫ������
		Field[] field = clazz.getDeclaredFields();
		for(int i =0;i<field.length;i++){
			//Ȩ�����η�
			int mo = field[i].getModifiers();
			String priv = Modifier.toString(mo);
			//��������
			Class<?> type = field[i].getType();
			System.out.println(priv+""+type.getName()+""+field[i].getName()+";");
		}
		System.out.println("============ʵ�ֵĽӿڻ��߸��������=================");
		//ȡ��ʵ�ֵĽӿڻ��߸��������
		Field[] field1 = clazz.getFields();
		for(int j=0;j<field1.length;j++){
			//Ȩ�����η�
			int mo = field1[j].getModifiers();
			String priv = Modifier.toString(mo);
			
			//��������
			Class<?> type = field[j].getType();
			System.out.println(priv+""+type.getName()+""+field1[j].getName()+";");
		}
	}
}
