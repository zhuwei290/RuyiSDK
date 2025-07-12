// 包声明：定义当前类所属的包路径，属于RuyiSDK CMake导入器的向导功能模块
package org.ruyisdk.cmakeimporter.wizard;

// 导入Eclipse JFace框架的向导基类，提供向导对话框的核心功能和生命周期管理
import org.eclipse.jface.wizard.Wizard;
// 导入向导页面接口，定义向导页面的标准行为（虽然此处未直接使用，但为扩展性保留）
import org.eclipse.jface.wizard.IWizardPage;
// 导入向导页面基类，提供页面UI控件和验证逻辑的基础实现（为扩展性保留）
import org.eclipse.jface.wizard.WizardPage;
// 导入结构化选择接口，用于处理Eclipse工作台中用户的选择操作（如文件、项目等）
import org.eclipse.jface.viewers.IStructuredSelection;
// 导入Eclipse UI框架的导入向导接口，标识此类为Eclipse导入功能的一部分
import org.eclipse.ui.IImportWizard;
// 导入Eclipse工作台接口，代表Eclipse IDE的主工作环境和上下文信息
import org.eclipse.ui.IWorkbench;

// CMake项目导入向导类：实现Eclipse标准的导入向导功能
// 继承Wizard基类获得向导框架支持，实现IImportWizard接口集成到Eclipse导入系统
public class CMakeProjectImportWizard extends Wizard implements IImportWizard {
    // 私有成员变量：存储向导的主要页面实例，负责用户交互和输入收集
    private CMakeProjectImportWizardPage mainPage;

    // 构造函数：创建CMake导入向导实例并进行基本初始化
    public CMakeProjectImportWizard() {
        // 设置向导窗口标题，显示在对话框顶部标题栏，向用户明确当前操作
        setWindowTitle("CMake 项目导入向导");
    }

    // 重写父类方法：向向导中添加页面组件
    // Eclipse向导框架在显示向导前调用此方法来构建页面结构
    @Override
    public void addPages() {
        // 创建CMake项目导入的主要页面实例，包含用户输入控件和验证逻辑
        mainPage = new CMakeProjectImportWizardPage();
        // 将主页面注册到向导框架中，使其在向导对话框中可见和可交互
        addPage(mainPage);
    }

    // 重写父类方法：处理用户点击"完成"按钮时的操作
    // 返回true表示操作成功并关闭向导；返回false表示操作失败，向导保持打开状态
    @Override
    public boolean performFinish() {
        // 委托给主页面的finish方法执行实际的CMake项目导入操作
        // 包括验证输入、创建项目、配置CMake环境等核心功能
        return mainPage.finish();
    }

    // 重写接口方法：初始化向导实例，接收Eclipse工作台上下文信息
    // 在向导创建时由Eclipse框架调用，传入当前工作环境和用户选择状态
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        // 初始化实现区域：可根据工作台环境和用户选择设置向导默认值
        // 例如：从选择中获取默认路径、检查前置条件、保存上下文等
        // 当前为空实现，表示使用向导页面的默认初始化逻辑
    }
} 