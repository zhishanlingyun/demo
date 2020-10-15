package com.demo.mt.server;

import com.weibo.api.motan.registry.vintage.RegistryTask;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppListenser implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // 启动服务注册，将服务注册到Vintage注册中心
        MotanSwitcherUtil.setSwitcherValue(RegistryTask.STATIC_REGISTER_SWITCHER, true);
        // 打开心跳开关，将服务状态调整为working
        MotanSwitcherUtil.setSwitcherValue(RegistryTask.RPC_HEARTBEAT_SWITCHER, true);
        System.out.println("rpc server start...");



    }
}
