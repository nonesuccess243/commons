package com.nbm.core.modeldriven.generator;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.nbm.core.modeldriven.test.model.ModelDrivenTestModel;

public class GeneratorFileUtilsTest
{

        @Test
        public void test()
        {

                String s = "C:\\Users\\玉哲\\workspace\\commons.db\\.\\src\\test\\java\\com\\nbm\\core\\modeldriven\\test\\model\\dao";
                assertEquals(true, s.contains("src\\test\\java"));
                
                
                s = "C:\\Users\\玉哲\\workspace\\commons.db\\src\\test\\resource\\com\\nbm\\core\\modeldriven\\test\\model\\dao";
                
                System.out.println(s);
                assertEquals(true, s.contains("resource"));
                System.out.println(GeneratorFileUtils.INSTANCE
                                .generateResourcePackage(ModelDrivenTestModel.class));
                
                File file = GeneratorFileUtils.INSTANCE
                                .generateResourcePackage(ModelDrivenTestModel.class);
                
                System.out.println(file.getAbsolutePath());
                assertEquals(true, file.getAbsolutePath().contains("resource"));
                
                
                GeneratorFileUtils.INSTANCE
                                .generateResourcePackage(ModelDrivenTestModel.class).mkdirs();
                
                assertEquals(true, file.exists());
                
        }

}
