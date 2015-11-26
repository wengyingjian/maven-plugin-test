/**
 * 
 */
package com.asd.mojo.test;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * com.asd.mojo:mojo-test:test1 -Dname=vicky -Dage=27 -DisOk=true
 * 
 * @goal test1
 * @phase compile
 * @requiresProject false 该命令可以在任何路径下执行，因为他不依赖maven项目
 */
public class Test1 extends AbstractMojo {

    /**
     * @parameter expression="${name}"
     * @required
     */
    String  name;
    /**
     * @parameter expression="${age}"
     * @required
     */
    int     age;
    /**
     * @parameter expression="${isOk}"
     * @required
     */
    boolean isOk;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(this.toString());
    }

    public String toString() {
        return "String is : \"" + name + "\"" + "int is : \"" + age + "\"" + "boolean is : \"" + isOk + "\"";
    }
}