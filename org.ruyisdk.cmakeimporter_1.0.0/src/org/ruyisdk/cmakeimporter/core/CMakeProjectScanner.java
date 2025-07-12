package org.ruyisdk.cmakeimporter.core;  // 定义包名，属于核心功能模块

import java.io.File;  // 导入File类，用于文件和目录操作

/**
 * CMake项目扫描器
 * 负责检测目录是否为CMake项目，以及查找CMake项目的根目录
 */
public class CMakeProjectScanner {  // 定义CMake项目扫描器类
    
    /**
     * 检查指定目录是否为CMake项目
     * @param dir 要检查的目录
     * @return 如果是CMake项目返回true，否则返回false
     */
    public static boolean isCMakeProject(File dir) {  // 静态方法，判断目录是否为CMake项目
        if (dir == null || !dir.isDirectory()) {  // 检查参数是否为空或不是目录
            return false;  // 如果参数无效，返回false
        }
        File cmakeFile = new File(dir, "CMakeLists.txt");  // 在指定目录下查找CMakeLists.txt文件
        return cmakeFile.exists() && cmakeFile.isFile();  // 检查CMakeLists.txt是否存在且为文件（不是目录）
    }

    /**
     * 在指定目录及其子目录中查找CMake项目的根目录
     * @param dir 开始搜索的目录
     * @return 找到的CMake项目根目录，如果没找到返回null
     */
    public static File findCMakeRoot(File dir) {  // 静态方法，递归查找CMake项目根目录
        if (dir == null || !dir.isDirectory()) return null;  // 检查参数有效性，无效则返回null
        
        File cmake = new File(dir, "CMakeLists.txt");  // 在当前目录下查找CMakeLists.txt文件
        if (cmake.exists()) return dir;  // 如果当前目录包含CMakeLists.txt，返回当前目录作为根目录
        
        File[] subs = dir.listFiles(File::isDirectory);  // 获取当前目录下的所有子目录
        if (subs != null) {  // 检查是否成功获取子目录列表
            for (File sub : subs) {  // 遍历每个子目录
                File found = findCMakeRoot(sub);  // 递归在子目录中查找CMake项目
                if (found != null) return found;  // 如果在子目录中找到CMake项目，立即返回结果
            }
        }
        return null;  // 如果在当前目录及所有子目录中都没找到CMake项目，返回null
    }
} 