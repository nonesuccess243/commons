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

        private String packageName;

        
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

        private void generateMapperXml() throws Exception
        {
                this.mapperXmlContent = generateResourceFile("Mapper.xml.ftl", getMapperXmlFileName());
        }

        private void generateMapperJava() throws Exception
        {
                this.mapperJavaContent = generateAFile("Mapper.java.ftl", getMapperJavaFileName());
        }

        private void generateExampleJava() throws Exception
        {
                this.exampleJavaContent = generateAFile("Example.java.ftl", getExampleJavaFileName());
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
        private String generateAFile(String templatename, String fileName) throws Exception
        {
                Template temp = getFreemarkerTemplate(templatename);

                Writer out = new OutputStreamWriter(
                                new FileOutputStream(daoDir.getAbsoluteFile().toString() + "/" + fileName));

                temp.process(root, out);
                out.flush();
                
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
        private String generateResourceFile(String templatename, String fileName) throws Exception
        {
                Template temp = getFreemarkerTemplate(templatename);

                Writer out = new OutputStreamWriter(
                                new FileOutputStream(resourceDir.getAbsoluteFile().toString() + "/" + fileName));

                temp.process(root, out);
                out.flush();
                
                StringWriter writer = new StringWriter();
                temp.process(root, writer);
                writer.flush();
                
                return writer.toString();
        }
        
        private String generateSqlFile(String templatename, String fileName) throws Exception
        {
                Template temp = getFreemarkerTemplate(templatename);

                Writer out = new OutputStreamWriter(
                                new FileOutputStream(sqlDir.getAbsoluteFile().toString() + "/" + fileName));

                temp.process(root, out);
                out.flush();
                
                StringWriter writer = new StringWriter();
                temp.process(root, writer);
                writer.flush();
                
                return writer.toString();
        }

        private String getMapperXmlFileName()
        {
                return meta.getModelClass().getSimpleName() + "Mapper.xml";
        }

        private String getExampleJavaFileName()
        {
                return getExampleJavaName() + ".java";
        }

        private String getExampleJavaName()
        {
                return meta.getModelClass().getSimpleName() + "Example";
        }

        private String getMapperJavaFileName()
        {
                return meta.getModelClass().getSimpleName() + "Mapper.java";
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

        private void init() throws IOException, URISyntaxException
        {
                mapperCfg = new freemarker.template.Configuration();

                mapperCfg.setClassForTemplateLoading(CrudGenerator.class, "");

                mapperCfg.setObjectWrapper(new DefaultObjectWrapper());

                packageName = generatePackageName(meta.getModelClass());

                // 初始化dao类要放在的package下，保证有此文件夹
                daoDir = GeneratorFileUtils.INSTANCE.generateDaoPackage(meta.getModelClass());
                resourceDir = GeneratorFileUtils.INSTANCE.generateResourcePackage(meta.getModelClass());
                sqlDir = GeneratorFileUtils.INSTANCE.generateSqlPackage(meta.getModelClass());

                root = generateMapperRoot(meta.getModelClass());

        }

        private Template getFreemarkerTemplate(String templateName) throws IOException, URISyntaxException
        {
                return mapperCfg.getTemplate(db.getFtlFileName(templateName));
        }

        private Map<String, Object> generateMapperRoot(Class<? extends PureModel> modelClass)
        {
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

        private static String generatePackageName(Class<? extends PureModel> modelClass)
        {
                String packageName = modelClass.getName().replace(modelClass.getSimpleName(), "dao");
                return packageName;
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
}
