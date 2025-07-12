package org.ruyisdk.cmakeimporter;  // 定义包名，组织代码结构

import org.eclipse.ui.plugin.AbstractUIPlugin;  // 导入Eclipse UI插件基类
import org.osgi.framework.BundleContext;  // 导入OSGI框架上下文类

/**
 * Eclipse插件激活器类
 * 负责管理插件的生命周期（启动和停止）
 */
public class Activator extends AbstractUIPlugin {  // 继承Eclipse插件基类，成为一个UI插件
    public static final String PLUGIN_ID = "org.ruyisdk.cmakeimporter";  // 定义插件的唯一标识符常量
    private static Activator plugin;  // 静态变量，保存插件实例（单例模式）

    public Activator() {}  // 默认构造函数，创建插件实例时调用

    @Override  // 重写父类方法
    public void start(BundleContext context) throws Exception {  // 插件启动时的回调方法
        super.start(context);  // 调用父类的启动方法，完成基础初始化
        plugin = this;  // 将当前实例保存到静态变量中，实现单例访问
    }

    @Override  // 重写父类方法
    public void stop(BundleContext context) throws Exception {  // 插件停止时的回调方法
        plugin = null;  // 清空静态实例引用，释放资源
        super.stop(context);  // 调用父类的停止方法，完成清理工作
    }

    public static Activator getDefault() {  // 静态方法，获取插件实例
        return plugin;  // 返回保存的插件实例，供其他类使用
    }
} 