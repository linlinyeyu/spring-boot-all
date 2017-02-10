package reflect;
/**
* @author 作者 ybliu:
* @version 创建时间：2017年1月30日 下午10:13:03
* 反射机制应用于工厂模式
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
 * 对于普通的工厂模式当我们在添加一个子类的时候，就需要对应的修改工厂类。 当我们添加很多的子类的时候，会很麻烦。
 * Java 工厂模式可以参考
 * http://baike.xsoftlab.net/view/java-factory-pattern
 * 
 * 现在我们利用反射机制实现工厂模式，可以在不修改工厂类的情况下添加任意多个子类。
 * 
 * 但是有一点仍然很麻烦，就是需要知道完整的包名和类名，这里可以使用properties配置文件来完成。
 * 
 * java 读取 properties 配置文件 的方法可以参考
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
