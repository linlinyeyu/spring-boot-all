package reflect;
//实例化class对象
public class TestReflect1 {
	public static void main(String args[]) throws Exception{
		Class<?> class1 = null;
		Class<?> class2 = null;
		Class<?> class3 = null;
		
		//一般采用此种方法
		class1 = Class.forName("reflect.TestReflect1");
		class2 = new TestReflect1().getClass();
		class3 = TestReflect1.class;
		
		System.out.println("类名称 "+class1.getName());
		System.out.println("类名称 "+class2.getName());
		System.out.println("类名称 "+class3.getName());
	}
}
