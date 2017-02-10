package reflect;

import java.io.Serializable;

/**
* @author ���� ybliu:
* @version ����ʱ�䣺2017��1��30�� ����8:22:52
* ��ȡһ������ĸ�����ʵ�ֵĽӿ�
*/
public class TestReflect2 implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) throws Exception{
		Class<?> clazz = Class.forName("reflect.TestReflect2");
		
		//ȡ�ø���
		Class<?> parentClass = clazz.getSuperclass();
		System.out.println("clazz�ĸ�����:"+parentClass.getName());
		
		//��ȡ���нӿ�
		Class<?> intes[] = clazz.getInterfaces();
		System.out.println("clazzʵ�ֽӿ���:");
		for(int i = 0;i<intes.length;i++){
			System.out.println((i+1)+":"+intes[i].getName());
		}
	}

}
