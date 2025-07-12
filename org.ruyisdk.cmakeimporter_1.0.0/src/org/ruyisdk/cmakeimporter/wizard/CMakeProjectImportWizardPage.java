// 包声明：定义当前类所属的包路径，属于RuyiSDK CMake导入器的向导页面模块
package org.ruyisdk.cmakeimporter.wizard;

// 导入Eclipse JFace框架的向导页面基类，提供向导页面的基础功能和生命周期管理
import org.eclipse.jface.wizard.WizardPage;
// 导入SWT常量类，定义了所有SWT控件的样式和行为常量
import org.eclipse.swt.SWT;
// 导入SWT网格布局管理器，用于在容器中按网格方式排列控件
import org.eclipse.swt.layout.GridLayout;
// 导入SWT复合控件，作为其他控件的容器，支持布局管理
import org.eclipse.swt.widgets.Composite;
// 导入SWT标签控件，用于显示静态文本信息给用户
import org.eclipse.swt.widgets.Label;
// 导入SWT文本输入控件，提供单行文本输入功能
import org.eclipse.swt.widgets.Text;
// 导入Eclipse JFace消息对话框，用于向用户显示信息、警告或错误消息
import org.eclipse.jface.dialogs.MessageDialog;
// 导入SWT显示器类，代表GUI显示设备，用于UI线程操作和事件调度
import org.eclipse.swt.widgets.Display;
// 导入自定义CMake项目扫描器，负责在目录中查找和识别CMake项目结构
import org.ruyisdk.cmakeimporter.core.CMakeProjectScanner;
// 导入自定义Git工具类，提供Git仓库克隆和操作功能
import org.ruyisdk.cmakeimporter.core.GitUtils;
// 导入自定义CMake项目导入器，负责将CMake项目导入到Eclipse工作空间
import org.ruyisdk.cmakeimporter.core.CMakeProjectImporter;
// 导入Java标准库文件类，用于文件和目录操作
import java.io.File;
// 导入Eclipse核心资源项目接口，代表Eclipse工作空间中的项目
import org.eclipse.core.resources.IProject;
// 导入Eclipse核心运行时异常，表示Eclipse框架内部错误
import org.eclipse.core.runtime.CoreException;
// 导入JGit API异常类，表示Git操作过程中的错误（虽然当前未直接使用）
import org.eclipse.jgit.api.errors.GitAPIException;
// 导入SWT按钮控件，提供可点击的按钮功能
import org.eclipse.swt.widgets.Button;
// 导入SWT目录选择对话框，让用户选择本地文件夹
import org.eclipse.swt.widgets.DirectoryDialog;
// 导入Eclipse JFace可运行接口，支持在进度监控下执行长时间运行的任务
import org.eclipse.jface.operation.IRunnableWithProgress;
// 导入Eclipse JFace进度监控对话框，在执行长时间任务时显示进度给用户
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
// 导入Eclipse核心进度监控接口，用于跟踪和报告任务执行进度
import org.eclipse.core.runtime.IProgressMonitor;
// 导入Java反射调用目标异常，用于处理反射调用中的异常包装
import java.lang.reflect.InvocationTargetException;
// 导入Eclipse资源插件，提供对工作空间资源的访问和管理功能
import org.eclipse.core.resources.ResourcesPlugin;
// 导入Eclipse核心资源接口，代表工作空间中的各种资源（文件、文件夹等）
import org.eclipse.core.resources.IResource;

// CMake项目导入向导页面类：继承WizardPage，提供用户界面和交互逻辑
// 这个页面是向导的主要交互界面，用户在此输入项目路径或Git仓库地址
public class CMakeProjectImportWizardPage extends WizardPage {
    // 私有成员变量：文本输入控件，用于用户输入本地路径或GitHub仓库地址
    private Text pathText;

    // 构造函数：初始化向导页面实例
    public CMakeProjectImportWizardPage() {
        // 调用父类构造函数，设置页面的唯一标识符，用于向导框架内部管理
        super("CMakeProjectImportWizardPage");
        // 设置页面标题，显示在向导对话框的页面标题区域
        setTitle("CMake 项目导入");
        // 设置页面描述文本，向用户说明该页面的功能和使用方法
        setDescription("输入本地路径或GitHub仓库地址");
    }

    // 重写父类方法：创建页面的用户界面控件
    // 此方法由Eclipse向导框架调用，用于构建页面的可视化界面
    @Override
    public void createControl(Composite parent) {
        // 创建主容器：作为所有控件的父容器，设置为无特殊样式
        Composite container = new Composite(parent, SWT.NONE);
        // 设置容器布局：使用网格布局，3列布局，列宽不相等（false表示列宽按内容自适应）
        container.setLayout(new GridLayout(3, false));

        // 创建提示标签：显示输入框的说明文字，引导用户正确输入
        Label label = new Label(container, SWT.NONE);
        // 设置标签文本：明确告知用户可以输入的内容类型
        label.setText("本地路径或GitHub地址:");
        // 创建路径输入文本框：带边框样式，用于用户输入路径或URL
        pathText = new Text(container, SWT.BORDER);
        // 设置文本框布局数据：水平填充父容器，垂直居中，水平方向可拉伸，垂直方向不可拉伸
        pathText.setLayoutData(new org.eclipse.swt.layout.GridData(SWT.FILL, SWT.CENTER, true, false));

        // 创建浏览按钮：允许用户通过文件对话框选择本地目录
        Button browseButton = new Button(container, SWT.PUSH);
        // 设置按钮文本：使用中文标签，符合用户界面本地化要求
        browseButton.setText("浏览...");
        // 添加按钮点击事件监听器：当用户点击浏览按钮时执行
        browseButton.addListener(SWT.Selection, e -> {
            // 创建目录选择对话框：让用户选择本地文件夹
            DirectoryDialog dialog = new DirectoryDialog(getShell());
            // 打开对话框并获取用户选择的目录路径
            String selected = dialog.open();
            // 验证用户是否选择了有效路径（非取消操作）
            if (selected != null) {
                // 将选择的路径设置到文本输入框中，方便用户确认和修改
                pathText.setText(selected);
            }
        });

        // 设置页面控件：告知向导框架该页面的根控件，完成页面初始化
        setControl(container);
    }

    // 完成方法：处理用户点击"完成"按钮后的所有导入逻辑
    // 返回true表示导入成功，false表示失败（向导将保持打开状态）
    public boolean finish() {
        // 获取用户输入的路径文本并去除首尾空白字符
        String path = pathText.getText().trim();
        // 声明项目目录数组：使用数组是为了在lambda表达式中修改外部变量
        File[] projectDir = new File[1];
        // 判断输入是否为Git仓库地址：检查是否以http://或https://开头
        boolean isGit = (path.startsWith("http://") || path.startsWith("https://"));
        // Git URL规范化：如果是Git地址但未以.git结尾，则自动添加.git后缀
        if (isGit && !path.endsWith(".git")) {
            path = path + ".git";
        }
        // 开始try-catch块：处理导入过程中可能出现的各种异常
        try {
            // 分支处理：根据输入类型执行不同的获取项目目录逻辑
            if (isGit) {
                // Git仓库克隆分支：处理远程Git仓库的下载
                // 创建进度监控对话框：向用户显示克隆进度，提升用户体验
                ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
                // 声明final变量：用于在lambda表达式中使用，确保变量不可变
                final String gitPath = path;
                // 运行带进度监控的任务：true表示可取消，false表示不可取消
                dialog.run(true, false, (IRunnableWithProgress) (monitor) -> {
                    // 开始任务监控：设置任务描述和进度类型（UNKNOWN表示不确定进度）
                    monitor.beginTask("正在克隆Git仓库...", IProgressMonitor.UNKNOWN);
                    try {
                        // 执行Git克隆操作：调用GitUtils工具类克隆仓库到本地临时目录
                        projectDir[0] = GitUtils.cloneGitRepo(gitPath);
                    } catch (Exception e) {
                        // 异常转换：将检查异常转换为运行时异常，便于在lambda中抛出
                        throw new RuntimeException(e);
                    } finally {
                        // 确保监控完成：无论成功失败都要调用done()方法结束监控
                        monitor.done();
                    }
                });
                // 克隆成功提示：向用户确认Git仓库已成功下载，即将开始项目识别
                MessageDialog.openInformation(Display.getDefault().getActiveShell(), "克隆成功", "Git仓库克隆成功，正在识别并导入CMake项目...");
            } else {
                // 本地路径分支：直接使用用户输入的本地路径创建File对象
                projectDir[0] = new File(path);
            }
            // CMake项目识别：在获取到的目录中查找CMakeLists.txt文件
            File cmakeRoot = CMakeProjectScanner.findCMakeRoot(projectDir[0]);
            // 验证CMake项目：检查是否找到有效的CMake项目结构
            if (cmakeRoot == null) {
                // 未找到CMake项目警告：向用户显示错误信息并终止导入过程
                MessageDialog.openWarning(Display.getDefault().getActiveShell(), "导入失败", "未找到CMakeLists.txt");
                // 返回false：告知向导框架导入失败，向导保持打开状态
                return false;
            }
            // 项目名称安全化：将目录名中的非字母数字字符替换为下划线，确保Eclipse项目名合法
            String safeProjectName = cmakeRoot.getName().replaceAll("[^a-zA-Z0-9_]", "_");
            // 执行CMake项目导入：调用导入器将CMake项目导入到Eclipse工作空间
            IProject project = CMakeProjectImporter.importCMakeProject(cmakeRoot, safeProjectName);
            // 项目刷新：如果项目存在，刷新项目资源以确保文件系统同步
            if (project != null && project.exists()) {
                // 深度刷新：递归刷新项目下所有资源，确保Eclipse能识别所有文件
                project.refreshLocal(IResource.DEPTH_INFINITE, null);
            }
            // 工作空间刷新：刷新整个工作空间根目录，确保新项目在Project Explorer中可见
            ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
            // 导入结果验证：检查项目是否成功创建并存在于工作空间中
            if (project == null || !project.exists()) {
                // 导入失败警告：项目创建失败，向用户显示错误信息
                MessageDialog.openWarning(Display.getDefault().getActiveShell(), "导入失败", "CMake项目导入失败");
                // 返回false：告知向导框架导入失败
                return false;
            }
            // 异步UI操作：在UI线程中显示成功消息，避免阻塞当前线程
            Display.getDefault().asyncExec(() -> {
                // 导入成功提示：向用户确认项目已成功导入并可在Project Explorer中查看
                MessageDialog.openInformation(Display.getDefault().getActiveShell(), "导入成功", "CMake项目已成功导入并可在Project Explorer中查看。");
                // 重启建议提示：建议用户重启Eclipse以确保CMake功能完整可用
                MessageDialog.openInformation(Display.getDefault().getActiveShell(), "提示", "为保证CMake功能完整，请重启Eclipse。");
            });
            // 返回成功：告知向导框架导入成功，向导将自动关闭
            return true;
        // 捕获反射调用异常：处理在进度监控对话框中执行任务时的异常
        } catch (InvocationTargetException e) {
            // 获取原始异常：从反射异常包装中提取真正的异常原因
            Throwable cause = e.getCause();
            // Git克隆失败提示：向用户显示Git操作失败的具体错误信息
            MessageDialog.openWarning(Display.getDefault().getActiveShell(), "Git克隆失败", "无法克隆Git仓库: " + (cause != null ? cause.getMessage() : e.getMessage()));
            // 返回失败：告知向导框架操作失败
            return false;
        // 捕获线程中断异常：处理用户取消操作或线程被中断的情况
        } catch (InterruptedException e) {
            // 操作中断提示：向用户确认操作已被中断
            MessageDialog.openWarning(Display.getDefault().getActiveShell(), "操作中断", "操作被用户中断: " + e.getMessage());
            // 返回失败：告知向导框架操作被中断
            return false;
        // 捕获Eclipse核心异常：处理Eclipse工作空间操作中的错误
        } catch (CoreException e) {
            // Eclipse导入失败提示：向用户显示Eclipse项目导入过程中的错误
            MessageDialog.openWarning(Display.getDefault().getActiveShell(), "导入失败", "Eclipse项目导入失败: " + e.getMessage());
            // 返回失败：告知向导框架项目导入失败
            return false;
        // 捕获所有其他异常：处理未预期的错误情况
        } catch (Exception e) {
            // 通用错误提示：向用户显示未知错误的信息
            MessageDialog.openWarning(Display.getDefault().getActiveShell(), "错误", "发生未知错误: " + e.getMessage());
            // 返回失败：告知向导框架发生未知错误
            return false;
        }
    }
} 