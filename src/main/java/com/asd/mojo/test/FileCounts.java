/**
 * 
 */
package com.asd.mojo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * com.asd.mojo:mojo-test:filecounts
 * 
 * 
 * @goal filecounts
 * @phase compile
 * @requiresProject true 该命令只能在Maven项目下执行，因为他依赖maven项目
 */
public class FileCounts extends AbstractMojo {

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

    public void execute() throws MojoExecutionException, MojoFailureException {

        if (includes == null) {
            includes = DEFAULT_INCLUDES;
        }

        List<File> rfFiles = new ArrayList<File>();
        getRfFiles(rfFiles, basedir);
        for (File file : rfFiles) {
            try {
                System.out.println(getFilecounts(file));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void getRfFiles(List<File> files, File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (File f : listFiles) {
                getRfFiles(files, f);
            }
        } else {
            for (String include : includes) {
                if (file.getName().endsWith("." + include)) {
                    files.add(file);
                    break;
                }
            }

        }
    }

    private FileCountsInfo getFilecounts(File file) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int count = 0;
        try {
            while (br.ready()) {
                br.readLine();
                count++;
            }
        } catch (Exception e) {
        } finally {
            br.close();
        }
        return new FileCountsInfo(file, count);
    }
}

class FileCountsInfo {

    private File file;
    private int  count;

    public FileCountsInfo(File file, int count) {
        this.file = file;
        this.count = count;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return file.toString() + " count: " + count;
    }
}