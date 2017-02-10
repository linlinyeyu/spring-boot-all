package reflect;

import java.lang.reflect.Constructor;

/**
* @author ���� ybliu:
* @version ����ʱ�䣺2017��1��30�� ����8:28:53
* ��ȡĳ����ȫ�����캯��
*/
public class TestReflect3 {
	public static void main(String args[]) throws Exception{
		Class<?> class1 = null;
		class1 = Class.forName("reflect.User");
		
		//ʵ����Ĭ�Ϲ��췽��
		User user = (User)class1.newInstance();
		user.setAge(20);
		user.setName("jack");
		System.out.println(user);
		
		//ȡ��ȫ�����캯����ʹ�ù��캯����ֵ
		Constructor<?> cons[] = class1.getConstructors();
		//�鿴ÿ�����췽����Ҫ�Ĳ���
		for(int i = 0;i<cons.length;i++){
			Class<?> clazzs[] = cons[i].getParameterTypes();
			System.out.println("cons["+i+"] (");
			for(int j = 0;j<clazzs.length;j++){
				if(j == clazzs.length-1)
					System.out.println(clazzs[j].getName());
				else
					System.out.println(clazzs[j].getName()+",");
			}
			System.out.println(")");
		}
		user = (User)cons[1].newInstance("jack");
		System.out.println(user);
		user = (User)cons[0].newInstance(20,"jack");
		System.out.println(user);
	}
}

class User{
	private int age;
	private String name;
	
	public User(){
		super();
	}
	
	public User(String name){
		super();
		this.name = name;
	}
	
	public User(int age,String name){
		super();
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "User [age="+age+",name="+name+"]";
	}
}