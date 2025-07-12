package org.ruyisdk.cmakeimporter.core;  // 定义包名，属于核心功能模块

import java.io.File;  // 导入File类，用于文件和目录操作
import java.util.ArrayList;  // 导入ArrayList类，用于动态数组操作
import java.util.Arrays;  // 导入Arrays类，提供数组操作工具方法
import java.util.List;  // 导入List接口，定义列表数据结构
import org.eclipse.core.resources.IProject;  // 导入Eclipse项目接口
import org.eclipse.core.resources.IProjectDescription;  // 导入Eclipse项目描述接口
import org.eclipse.core.resources.ResourcesPlugin;  // 导入Eclipse资源插件，管理工作空间
import org.eclipse.core.runtime.CoreException;  // 导入Eclipse核心异常类
import org.eclipse.core.runtime.NullProgressMonitor;  // 导入空进度监视器，用于不需要进度显示的操作
import org.eclipse.cdt.core.CCorePlugin;  // 导入CDT核心插件，提供C/C++开发功能

/**
 * CMake项目导入器
 * 负责将CMake项目导入到Eclipse工作空间，并配置相应的项目性质
 */
public class CMakeProjectImporter {  // 定义CMake项目导入器类
    
    /**
     * 导入CMake项目到Eclipse工作空间
     * @param projectDir 项目目录
     * @param projectName 项目名称
     * @return 创建的Eclipse项目对象
     * @throws CoreException 如果导入过程中发生错误
     */
    public static IProject importCMakeProject(File projectDir, String projectName) throws CoreException {  // 静态方法，导入CMake项目
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);  // 获取工作空间根目录下的指定项目
        if (!project.exists()) {  // 检查项目是否已存在
            IProjectDescription desc = ResourcesPlugin.getWorkspace().newProjectDescription(projectName);  // 创建新的项目描述对象
            desc.setLocationURI(projectDir.toURI());  // 设置项目的物理位置为指定目录
            project.create(desc, new NullProgressMonitor());  // 根据描述创建项目，不显示进度
            project.open(new NullProgressMonitor());  // 打开项目，使其在Eclipse中可见
        }
        
        // 添加C/C++和CMake三种nature（项目性质）
        String[] requiredNatures = {  // 定义项目需要的性质数组
            "org.eclipse.cdt.core.cnature",  // C语言项目性质
            "org.eclipse.cdt.core.ccnature",  // C++语言项目性质
            "org.eclipse.cdt.cmake.core.cmakeNature"  // CMake项目性质
        };
        
        IProjectDescription desc = project.getDescription();  // 获取项目的当前描述
        List<String> natures = new ArrayList<>(Arrays.asList(desc.getNatureIds()));  // 将现有的项目性质转换为可修改的列表
        
        for (String nature : requiredNatures) {  // 遍历所有需要的项目性质
            if (!natures.contains(nature)) {  // 检查当前性质是否已存在
                natures.add(nature);  // 如果不存在，则添加到性质列表中
            }
        }
        
        desc.setNatureIds(natures.toArray(new String[0]));  // 将更新后的性质列表设置回项目描述
        project.setDescription(desc, new NullProgressMonitor());  // 应用新的项目描述，不显示进度
        
        // 创建.cproject文件，确保为CDT项目
        CCorePlugin.getDefault().createCDTProject(desc, project, new NullProgressMonitor());  // 调用CDT插件创建C/C++项目结构
        
        // 刷新并重载项目，确保 Project Nature 立即生效
        project.refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());  // 递归刷新项目的所有资源
        project.close(new NullProgressMonitor());  // 关闭项目
        project.open(new NullProgressMonitor());  // 重新打开项目，确保所有更改生效
        
        return project;  // 返回配置完成的项目对象
    }
} 