/**
 * 
 */
package com.asd.mojo.test;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;


/**
 * com.asd.mojo:mojo-test:test3 -Dothers=o1,o2,o3
 * 
 * @goal test3
 * @phase compile
 * @requiresProject true 该命令只能在Maven项目下执行，因为他依赖maven项目
 */
public class Test3 extends AbstractMojo {

    private static final String[] DEFAULT_INCLUDES = new String[] { "java", "xml", "properties" };
    /**
     * 项目根目录
     * 
     * @parameter expression="${project.basedir}"
     * @required
     * @readonly
     */
    private File                  basedir;
    /**
     * 项目资源目录
     * 
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     * @readonly
     */
    private File                  sourceDirectory;
    /**
     * 项目测试资源目录
     * 
     * @parameter expression="${project.build.testSourceDirectory}"
     * @required
     * @readonly
     */
    private File                  testSourceDirectory;
    /**
     * 其他目录,由于没有配置required 以及 readonly，故可以不用配置， 且在pom.xml plugin->configuration会显示该属性的代码提示
     * 
     * @parameter expression="${otherFile}"
     */
    private File                  otherFile;
    /**
     * 项目资源
     * 
     * @parameter expression="${project.build.resources}"
     * @required
     * @readonly
     */
    private List<Resource>        resources;
    /**
     * 项目测试资源
     * 
     * @parameter expression="${project.build.testResources}"
     * @required
     * @readonly
     */
    private List<Resource>        testResources;
    /**
     * 额外参数，由于没有配置expression，所以只能过通过pom.xml plugin->configuration配置获得
     * 
     * @parameter
     */
    private String[]              includes;
    /**
     * 额外参数，可以通过pom.xml plugin->configuration配置获得，也可以通过 -Dother=a,b,c 这样获得,但如果同时在pom.xml中配置也通过-D配置， 那么将使用-D替换pom.xml的配置
     * 
     * @parameter expression="${others}"
     */
    private String[]              others;
    /**
     * 设置一个默认值
     * 
     * @parameter default-value="99"
     */
    private int                   num;

    public void execute() throws MojoExecutionException, MojoFailureException {

        for (Object key : getPluginContext().keySet()) {
            System.out.println("key:" + key + "  value:" + getPluginContext().get(key));
        }

        if (includes == null) {
            includes = DEFAULT_INCLUDES;
        }

        getLog().info("\t basedir : " + basedir.toString());
        getLog().info("\t sourceDirectory : " + sourceDirectory.toString());
        getLog().info("\t testSourceDirectory : " + testSourceDirectory.toString());
        if (otherFile != null) {
            getLog().info("\t otherFile : " + otherFile.toString());
        }
        for (Resource rs : resources) {
            getLog().info("\t resource : " + rs.toString());
        }
        for (Resource rs : testResources) {
            getLog().info("\t testResource : " + rs.toString());
        }
        if (includes != null) {
            for (String str : includes) {
                getLog().info("\t include : " + str);
            }
        }
        if (others != null) {
            for (String str : others) {
                getLog().info("\t other : " + str);
            }
        }

        getLog().info("\t num : " + num);

    }
}