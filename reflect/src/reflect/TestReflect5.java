package reflect;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年1月30日 下午9:06:35
* 获取某个类全部方法
*/
public class TestReflect5 implements Serializable{
	private static final long serialVersionUID = 2780914914974328672L;
	public static void main(String args[]) throws Exception{
		Class<?> clazz = Class.forName("reflect.TestReflect5");
		Method method[] = clazz.getMethods();
		for(int i = 0;i<method.length;i++){
			Class<?> returnType = method[i].getReturnType();
			Class<?> para[] = method[i].getParameterTypes();
			int temp = method[i].getModifiers();
			System.out.println(Modifier.toString(temp)+"");
			System.out.println(returnType.getName()+"");
			System.out.println(method[i].getName()+"");
			System.out.println("(");
			for(int j=0;j<para.length;j++){
				System.out.println(para[j].getName()+""+"arg"+j);
				if(j<para.length-1){
					System.out.println(",");
				}
			}
			Class<?> exce[] = method[i].getExceptionTypes();
			if(exce.length > 0){
				System.out.println(") throws");
				for(int k = 0;k<exce.length;k++){
					System.out.println(exce[k].getName()+"");
					if(k<exce.length-1){
						System.out.println(",");
					}
				}
			}else{
				System.out.println(")");
			}
			System.out.println();
		}
	}
}
