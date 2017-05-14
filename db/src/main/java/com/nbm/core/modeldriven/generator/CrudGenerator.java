package com.nbm.core.modeldriven.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.core.modeldriven.ModelMeta;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class CrudGenerator
{

        public static Db db = Db.MYSQL;

        private final static Logger log = LoggerFactory.getLogger(CrudGenerator.class);

        private ModelMeta meta;

        private freemarker.template.Configuration mapperCfg;

        private Map<String, Object> root;

        /**
         * 生成的dao类所在的文件夹对应的File对象
         */
        private File daoDir;

        /**
         * 生成的资源文件对应的File文件夹
         * 
         * 在原本的方案中，resource和dao是放在同一个文件夹下的，但如果采用maven构建项目，放在同一文件夹下打包时会排除在外
         * 
         * 
         */
        private File resourceDir;
        
        /**
         * 保存sql语句的路径
         */
        private File sqlDir;

        //以下几个content结尾的变量，在生成文件内容的同时，将字符串内容在此类中缓存起来，便于生成的时候用程序处理
	private String mapperXmlContent;
	
	private String mapperJavaContent;

	private String exampleJavaContent;

	private String createSqlContent;

	private String extraContent;

        public CrudGenerator(Class<? extends PureModel> modelClass)
        {
                super();
                this.meta = ModelMeta.getModelMeta(modelClass);
        }

        public void generate() throws Exception
        {
                init();
                generateMapperXml();
                generateExampleJava();
                generateMapperJava();
                generateCreateSql();
                generateExtra();
        }

        public String getMapperXmlContent()
        {
        	return mapperXmlContent;
        }

        public String getMapperJavaContent()
        {
        	return mapperJavaContent;
        }

        public String getExampleJavaContent()
        {
        	return exampleJavaContent;
        }

        public String getCreateSqlContent()
        {
        	return createSqlContent;
        }

        public String getExtraContent()
        {
        	return extraContent;
        }

        public ModelMeta getMeta()
        {
        	return meta;
        }

        private void init() throws IOException, URISyntaxException
        {
                mapperCfg = new freemarker.template.Configuration();
                mapperCfg.setClassForTemplateLoading(CrudGenerator.class, "");
                mapperCfg.setObjectWrapper(new DefaultObjectWrapper());
        
                // 初始化dao类要放在的package下，保证有此文件夹
                daoDir = GeneratorFileUtils.INSTANCE.generateDaoPackage(meta.getModelClass());
                resourceDir = GeneratorFileUtils.INSTANCE.generateResourcePackage(meta.getModelClass());
                sqlDir = GeneratorFileUtils.INSTANCE.generateSqlPackage(meta.getModelClass());
        
                root = GeneratorFileUtils.INSTANCE.generateMapperRoot(meta);
        
        }

        private void generateMapperXml() throws Exception
        {
                this.mapperXmlContent = generateResourceFile("Mapper.xml.ftl",  GeneratorFileUtils.INSTANCE.getMapperXmlFileName(meta));
        }

        private void generateMapperJava() throws Exception
        {
                this.mapperJavaContent = generateAFile("Mapper.java.ftl",  GeneratorFileUtils.INSTANCE.getMapperJavaFileName(meta));
        }

        private void generateExampleJava() throws Exception
        {
                this.exampleJavaContent = generateAFile("Example.java.ftl",  GeneratorFileUtils.INSTANCE.getExampleJavaFileName(meta));
        }

        private void generateCreateSql() throws Exception
        {
                this.createSqlContent = generateSqlFile("create.sql.ftl", meta.getDbTypeName() + ".sql");
        }

        private void generateExtra() throws Exception
        {
                this.extraContent = generateResourceFile("extra.ftl", meta.getModelClass().getSimpleName() + ".extra");
        }

        
        /**
         * 
         * @param templatename
         * @param fileName
         * @return 生成文件的内容
         * @throws Exception
         */
        private String generateFile(String templateName, String fileName, File dir) throws Exception
        {
                
                Template temp = mapperCfg.getTemplate(db.getFtlFileName(templateName));

                Writer out = new OutputStreamWriter(
                                new FileOutputStream(dir.getAbsoluteFile().toString() + "/" + fileName));

                String result = generateContent(templateName, fileName);
                out.append(result);
                out.flush();
                
                return result;
                
        }
        
        private String generateContent(String templateName, String fileName) throws Exception
        {
                
                Template temp = mapperCfg.getTemplate(db.getFtlFileName(templateName));
                
                StringWriter writer = new StringWriter();
                temp.process(root, writer);
                writer.flush();
                
                return writer.toString();
                
        }
        
        /**
         * 
         * @param templatename
         * @param fileName
         * @return 生成文件的内容
         * @throws Exception
         */
        private String generateAFile(String templatename, String fileName) throws Exception
        {
                return generateFile(templatename, fileName, daoDir);
                
        }
        
        /**
         * 
         * @param templatename
         * @param fileName
         * @return 生成文件的内容
         * @throws Exception
         */
        private String generateResourceFile(String templatename, String fileName) throws Exception
        {
                return generateFile(templatename, fileName, resourceDir);
        }
        
        private String generateSqlFile(String templatename, String fileName) throws Exception
        {
                return generateFile(templatename, fileName, sqlDir);
        }

        
}
