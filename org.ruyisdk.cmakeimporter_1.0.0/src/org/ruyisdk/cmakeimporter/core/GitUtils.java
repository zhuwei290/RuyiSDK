package org.ruyisdk.cmakeimporter.core;  // 定义包名，属于核心功能模块

import java.io.File;  // 导入File类，用于文件和目录操作
import java.io.IOException;  // 导入IO异常类，处理输入输出相关错误
import java.nio.file.Files;  // 导入Files类，提供文件系统操作的工具方法
import org.eclipse.jgit.api.Git;  // 导入JGit的Git类，提供Git操作的高级API
import org.eclipse.jgit.api.errors.GitAPIException;  // 导入JGit API异常类，处理Git操作相关错误

/**
 * Git工具类
 * 提供Git仓库操作功能，主要用于从远程仓库克隆代码
 */
public class GitUtils {  // 定义Git工具类
    
    /**
     * 克隆Git仓库到临时目录
     * @param repoUrl Git仓库的URL地址
     * @return 克隆后的本地目录对象
     * @throws IOException 如果创建临时目录或文件操作失败
     * @throws GitAPIException 如果Git克隆操作失败
     */
    public static File cloneGitRepo(String repoUrl) throws IOException, GitAPIException {  // 静态方法，克隆Git仓库
        File tempDir = Files.createTempDirectory("cmakeimporter_").toFile();  // 创建以"cmakeimporter_"为前缀的临时目录，并转换为File对象
        Git.cloneRepository()  // 开始Git克隆操作的构建器模式
            .setURI(repoUrl)  // 设置要克隆的远程仓库URL
            .setDirectory(tempDir)  // 设置克隆到的本地目录
            .call();  // 执行克隆操作
        return tempDir;  // 返回包含克隆代码的临时目录
    }
} 