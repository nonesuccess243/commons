package com.nbm.core.modeldriven.generator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.PureModel;

import freemarker.template.Template;

enum GeneratorFileUtils
{
        INSTANCE;

        private final static Logger log = LoggerFactory.getLogger(GeneratorFileUtils.class);

        /**
         * 找到参数中claz在哪个src目录，返回该目录的名字
         * @param claz
         * @return
         */
        private String findSrcDir(final Class<?> claz)
        {
                
                final List<String> result  = new ArrayList<>();
                
                Path path = Paths.get(".");//在eclipse中运行此程序时，用.获取的是项目源代码根目录
                
                try
                {
                        Files.walkFileTree(path, new SimpleFileVisitor<Path>()
                        {

                                @Override
                                public FileVisitResult visitFile(Path file,
                                                BasicFileAttributes attrs) throws IOException
                                {
                                        // log.error(file.getFileName().toString());
                                        if (!file.toString().endsWith(".java"))
                                        {
                                                return FileVisitResult.CONTINUE;
                                        } else
                                        {
                                                String p = file.toAbsolutePath()
                                                                .toString();
                                                if (p.endsWith(claz.getName().replaceAll(
                                                                                "\\.", "\\\\") + ".java" ))
                                                        //if为真时，p为要找的java类的源代码路径
                                                {
                                                        
                                                        //将源代码路径中，该java类的路径部分去掉，就得到了该java类所在的src目录
                                                        result.add(p.replace(claz.getName().replaceAll(
                                                                        "\\.", "\\\\") + ".java", ""));
                                                        
                                                        
                                                        return FileVisitResult.TERMINATE;
                                                }
                                        }
                                        return FileVisitResult.CONTINUE;
                                }

                        });
                } catch (IOException e)
                {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
                if( result.isEmpty())
                {
                        log.error(claz.toString());
                }
                
                return result.get(0);
        }
        
        /**
         * 根据给出的class对象，算出dao文件夹所对应的File对象
         * @param modelClass
         * @return
         */
        File generateDaoPackage(Class<? extends PureModel> modelClass)
        {
                String modelPath = GeneratorFileUtils.INSTANCE.findSrcDir(modelClass);
                
                File daoDir = new File( modelPath
                                + "/"
                                + modelClass.getPackage().getName()
                                                .replaceAll("\\.", "/") + "/dao");
                
                if (!daoDir.exists() || !daoDir.isDirectory())
                {
                        daoDir.mkdirs();
                }
                
                return daoDir;
        }
        
        /**
         * 根据给出的class对象，算出资源文件相关dao文件夹所对应的File对象
         * @param modelClass
         * @return
         */
        File generateResourcePackage(Class<? extends PureModel> modelClass)
        {
                String modelPath = GeneratorFileUtils.INSTANCE.findSrcDir(modelClass);
                
                StringBuilder result = new StringBuilder();
                
                if( modelPath.contains("src\\main\\java"))
                {
//                        modelPath = modelPath.replace("src\\main\\java", "src\\main\\resources");
                        result.append( "src\\main\\resources");
                }else if( modelPath.contains("src\\test\\java"))
                {
                        modelPath = modelPath.replace("src\\test\\java", "src\\test\\resources");
                        result.append( "src\\test\\resources");
                }
                
                result.append("/"
                                + modelClass.getPackage().getName()
                                .replaceAll("\\.", "/") + "/dao");
                
                File resourceDir = new File( result.toString());
                
                if (!resourceDir.exists() || !resourceDir.isDirectory())
                {
                        resourceDir.mkdirs();
                }
                
                return resourceDir;
        }
        /**
         * 根据给出的class对象，算出资源文件相关dao文件夹所对应的File对象
         * @param modelClass
         * @return
         */
        File generateSqlPackage(Class<? extends PureModel> modelClass)
        {
                String modelPath = GeneratorFileUtils.INSTANCE.findSrcDir(modelClass);
                
                if( modelPath.contains("src\\main\\java"))
                {
                        modelPath = "src\\main\\resources";
                }else if( modelPath.contains("src\\test\\java"))
                {
                        modelPath = "src\\test\\resources";
                }else
                {
                        modelPath = ".";
                }
                
                File resourceDir = new File( modelPath
                                + "/create_table"
                               );
                
                if (!resourceDir.exists() || !resourceDir.isDirectory())
                {
                        resourceDir.mkdirs();
                }
                
                return resourceDir;
        }
        
        String getMapperXmlFileName(ModelMeta meta)
        {
                return meta.getModelClass().getSimpleName() + "Mapper.xml";
        }

        String getExampleJavaFileName(ModelMeta meta)
        {
                return getExampleJavaName(meta) + ".java";
        }

        String getExampleJavaName(ModelMeta meta)
        {
                return meta.getModelClass().getSimpleName() + "Example";
        }

        String getMapperJavaFileName(ModelMeta meta)
        {
                return meta.getModelClass().getSimpleName() + "Mapper.java";
        }

        Map<String, Object> generateMapperRoot(ModelMeta meta)
        {
                Class<? extends PureModel> modelClass = meta.getModelClass();
                
                String packageName = generatePackageName(modelClass);
                
                Map<String, Object> root = new HashMap<String, Object>();

                root.put("package", packageName);

                root.put("namespace", packageName + "." + modelClass.getSimpleName() + "Mapper");
                root.put("typeName", modelClass.getName());
                root.put("typeSimpleName", modelClass.getSimpleName());

                root.put("mapperPath",
                                packageName.replaceAll("\\.", "/") + "/" + modelClass.getSimpleName() + "Mapper.xml");

                root.put("model", meta);
                root.put("fields", meta.getDbFields());
                root.put("tableName", meta.getDbTypeName());

                root.put("exampleJavaName", packageName + "." + modelClass.getSimpleName() + "Example");
                return root;
        }

        String generatePackageName(Class<? extends PureModel> modelClass)
        {
                String packageName = modelClass.getName().replace(modelClass.getSimpleName(), "dao");
                return packageName;
        }
        
        
        
        public static void main(String[] args)
        {
                
        }

}
