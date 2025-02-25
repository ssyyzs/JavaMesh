package com.huawei.argus.flow.core

import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.AfterProcess
import net.grinder.scriptengine.groovy.junit.annotation.AfterThread
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(GrinderRunner)
class FlowRunner {
    @BeforeProcess
    // 在每个进程启动前执行
    public static void beforeProcess() {
        // 加载资源文件、初始化 GTest 等
    }

    @BeforeThread
    // 在每个线程执行前执行
    public void beforeThread() {
        // 登录、设置 cookie 之类
    }

    @Before
    // 在每个 @Test 注解的方法执行前执行
    public void before() {
        // 设置变量、多个 @Test 方法共用的逻辑等
    }

    @Test
    // 在测试结束前不断运行。各个 @Test 注解的方法异步执行。
    public void foo() {
        // ...
        new PreFlowExecutor();
        System.out.println("test");
    }

    @After
    // 在每个 @Test 注解的方法执行后执行
    public void after() {
        // 很少用到
    }

    @AfterThread
    public void afterThread() {
        // 登出之类
    }

    @AfterProcess
    // 在每个进程结束后执行
    public static void afterProcess() {
        // 关闭资源
    }
}
