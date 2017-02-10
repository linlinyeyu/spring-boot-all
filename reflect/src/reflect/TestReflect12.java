package reflect;
/**
* @author ���� ybliu:
* @version ����ʱ�䣺2017��1��30�� ����10:13:03
* �������Ӧ���ڹ���ģʽ
*/
interface fruit{
	public abstract void eat();
}

class Apple implements fruit{
	public void eat(){
		System.out.println("Apple");
	}
}

class Orage implements fruit{
	public void eat(){
		System.out.println("Orange");
	}
}

class Factory{
	public static fruit getInstance(String ClassName){
		fruit f = null;
		try{
			f = (fruit)Class.forName(ClassName).newInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
		return f;
	}
}


/**
 * ������ͨ�Ĺ���ģʽ�����������һ�������ʱ�򣬾���Ҫ��Ӧ���޸Ĺ����ࡣ ��������Ӻܶ�������ʱ�򣬻���鷳��
 * Java ����ģʽ���Բο�
 * http://baike.xsoftlab.net/view/java-factory-pattern
 * 
 * �����������÷������ʵ�ֹ���ģʽ�������ڲ��޸Ĺ�����������������������ࡣ
 * 
 * ������һ����Ȼ���鷳��������Ҫ֪�������İ������������������ʹ��properties�����ļ�����ɡ�
 * 
 * java ��ȡ properties �����ļ� �ķ������Բο�
 * http://baike.xsoftlab.net/view/java-read-the-properties-configuration-file
 * 
 * @author ybliu
 */
public class TestReflect12 {

	public static void main(String[] args) {
		fruit f = Factory.getInstance("reflect.Apple");
		if(f!=null){
			f.eat();
		}

	}

}
