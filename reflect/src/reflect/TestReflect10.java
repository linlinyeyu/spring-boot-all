package reflect;

import java.lang.reflect.Array;

/**
* @author ���� ybliu:
* @version ����ʱ�䣺2017��1��30�� ����9:55:35
* ͨ������ȡ�ò��޸��������Ϣ
*/
public class TestReflect10 {

	public static void main(String[] args) {
		int[] temp = {1,2,3,4,5};
		Class<?> demo = temp.getClass().getComponentType();
		System.out.println("��������:"+demo.getName());
		System.out.println("���鳤��:"+Array.getLength(temp));
		System.out.println("����ĵ�һ��Ԫ��:"+Array.get(temp,0));
		Array.set(temp, 0,100);
		System.out.println("�޸ĺ��һ������Ԫ��:"+Array.get(temp,0));
	}

}
