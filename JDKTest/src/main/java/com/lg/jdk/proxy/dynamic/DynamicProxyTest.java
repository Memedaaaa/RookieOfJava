package com.lg.jdk.proxy.dynamic;

import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    public static void main(String[] args) {

        IUserService target = new IUserServiceImpl();
        MyInvocationHandler handler = new MyInvocationHandler(target);
        IUserService proxyObject = (IUserService) Proxy.newProxyInstance(
                //第一个参数是指定代理类的类加载器（我们传入当前测试类的类加载器）
                IUserServiceImpl.class.getClassLoader(),
                //第二个参数是代理类需要实现的接口（我们传入被代理类实现的接口，这样生成的代理类和被代理类就实现了相同的接口）
                IUserServiceImpl.class.getInterfaces(),
                //第三个参数是invocation handler，用来处理方法的调用。这里传入我们自己实现的handler
                handler);
        proxyObject.add("陈粒");
    }
}
