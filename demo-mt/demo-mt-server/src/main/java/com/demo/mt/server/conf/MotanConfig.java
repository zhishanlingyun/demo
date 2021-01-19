package com.demo.mt.server.conf;

import com.weibo.api.motan.config.ProtocolConfig;
import com.weibo.api.motan.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MotanConfig {

    @Bean
    public RegistryConfig registryConfigBean(){
        RegistryConfig bean = new RegistryConfig();
        bean.setName("weiboadRegistery");
        bean.setId("weiboadRegistery");
        bean.setRegProtocol("vintage");
        bean.setAddress("");
        return bean;
    }

    @Bean
    public ProtocolConfig protocolConfigBean(){
        ProtocolConfig bean = new ProtocolConfig();
        bean.setId("");
        bean.setName("");
        bean.setSerialization("hessian2");
        bean.setMinWorkerThread(5);
        bean.setMaxWorkerThread(10);
        return bean;
    }

}
