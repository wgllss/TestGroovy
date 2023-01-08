package com.wgllss.music.plugin;

import com.android.build.gradle.BaseExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

//groovy   和 java
public class ASMPlugin implements Plugin<Project> {
    //   main函数         插件  apply函数
    @Override
    public void apply(Project project) {
        System.out.println("------------wgllss 888   -------------------->");
        BaseExtension baseExtension = project.getExtensions()
                .getByType(BaseExtension.class);

        baseExtension.registerTransform(new ASMTransform2());
    }
}
