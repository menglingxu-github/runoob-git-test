package dubbo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import dubbo.service.DubboHelloService;


/**
 * api的方式调用
 * api的方式调用不需要其他的配置，只需要下面的代码即可。
 * 但是需要注意，官方建议：
 * Api方式用于测试用例使用，推荐xml的方式
 */
public class DubboConsumerAppApi {
        public static void main( String[] args ) throws IOException {
             // 当前应用配置
            ApplicationConfig application = new ApplicationConfig();
            application.setName("consumer");
            application.setOwner("dubbo");

            // 连接注册中心配置
            RegistryConfig registry = new RegistryConfig();
            registry.setAddress("zookeeper://172.22.200.12:2189");
            RegistryConfig registry1 = new RegistryConfig();
            registry1.setAddress("zookeeper://172.22.200.12:2187");
            RegistryConfig registry2 = new RegistryConfig();
            registry2.setAddress("zookeeper://172.22.200.12:2183");
            //registry.setAddress("zookeeper://localhost:2181");
            //registry.setAddress("172.22.200.12:2181,172.22.200.12:2182,172.22.200.12:2183");
            // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
            List<RegistryConfig> list=new ArrayList<RegistryConfig>();
            list.add(registry);
            list.add(registry1);
            list.add(registry2);
            // 引用远程服务
            ReferenceConfig<DubboHelloService> reference = new ReferenceConfig<DubboHelloService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
            reference.setApplication(application);
            //单注册中心
            //reference.setRegistry(registry);
            // 多个注册中心可以用setRegistries()
            reference.setRegistries(list);
            reference.setInterface(DubboHelloService.class);

            // 和本地bean一样使用xxxService
            DubboHelloService providerService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
            providerService.sayHello("hello dubbo! I am mlx!");

    }
}

