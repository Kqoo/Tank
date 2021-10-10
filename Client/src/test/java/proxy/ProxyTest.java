package proxy;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        Student student = new Student();
        
        Object o = Proxy.newProxyInstance(Student.class.getClassLoader(),
               Student.class.getInterfaces(),
                (proxy, method, args1) -> {
                    if (method.getName().equals("study")) {
                        System.out.println("method:" + method.getName());
                        System.out.println("open book!");
                        Object invoke = method.invoke(student, args1);
                        System.out.println("study finish!");
                        System.out.println();
                        return invoke;
                    } else if (method.getName().equals("eatRice")){
                        System.out.println("method:" + method.getName());
                        System.out.println("cook rice ");
                        Object invoke = method.invoke(student, args1);
                        System.out.println("eat finish!");
                        System.out.println();
                        return invoke;
                    }
                    return null;
                });

        ((Study) o).study();
        ((Eat) o).eatRice();


    }
}
