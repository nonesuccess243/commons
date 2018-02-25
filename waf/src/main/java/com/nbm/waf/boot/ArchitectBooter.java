package com.nbm.waf.boot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.nbm.commons.db.meta.UnderlineCamelConverter;
import com.nbm.exception.NbmBaseRuntimeException;
import com.wayeasoft.core.configuration.Cfg;
import com.younker.waf.utils.StringUtil;

/**
 * 根据一个简单的定义文件，创建项目子系统、一级目录、二级目录所有元素相关结构的工具类
 * 
 * @author niyuzhe
 *
 */
public enum ArchitectBooter
{
        INSTANCE;

        private final static Logger log = LoggerFactory.getLogger(ArchitectBooter.class);

        /**
         * 结构元数据定义文件的路径
         */
        private final static String FILE_NAME = Cfg.I.get("commons.webmeta.file", String.class, "webmeta.json");

        public void boot()
        {
                try
                {
                        Map<String, ?> resultMap = readAndGetMap();

                        generateJavaPackage(resultMap);
                        generateInitSqlFile(resultMap);
                        generateInitDbgrid(resultMap);
                } catch (Exception e)
                {
                        throw new NbmBaseRuntimeException("archetect booter 异常", e);
                }
                log.info("webmeta创建完成");

        }

        private void generateInitDbgrid(Map<String, ?> resultMap) throws IOException
        {

                String dir_path = "./src/main/resources/"
                                + Cfg.I.get("dbgrid.config_file_dir", String.class, "dbgrid_config");

                log.info("创建[{}]目录作为dbgrid配置文件目录", dir_path);
                Paths.get(dir_path).toFile().mkdirs();// 创建sql文件夹

                for (Map subsys : (List<Map>) resultMap.get("subsys"))
                {
                        String subsysCode = subsys.get("code").toString();

                        String subsysPath = dir_path + "/" + subsysCode;
                        log.info("创建[{}]目录作为子系统[{}]的dbgrid配置文件目录", subsysPath, subsysCode);
                        Paths.get(subsysPath).toFile().mkdirs();

                        for (Map module : (List<Map>) subsys.get("module"))
                        {
                                String moduleCode = module.get("code").toString();
                                String moduleFilePath = dir_path + "/" + subsysCode + "/" + moduleCode + ".xml";
                                log.info("创建[{}]目录作为一级模块[{}]的dbgrid配置文件目录", moduleFilePath, moduleCode);
                                Paths.get(moduleFilePath).toFile().createNewFile();

                                // for (Map moduleItem : (List<Map>)
                                // module.get("moduleItem"))
                                // {
                                // String moduleItemCode =
                                // moduleItem.get("code").toString();
                                // }
                        }
                }

        }

        private void generateJavaPackage(Map<String, ?> resultMap) throws IOException
        {
                String packagePrefix = UnderlineCamelConverter.INSTANCE
                                .javaName2PackageName(resultMap.get("group").toString()) + "."
                                + UnderlineCamelConverter.INSTANCE
                                                .javaName2PackageName(resultMap.get("name").toString());

                for (Map subsys : (List<Map>) resultMap.get("subsys"))
                {
                        String subsysCode = subsys.get("code").toString();
                        for (Map module : (List<Map>) subsys.get("module"))
                        {
                                String moduleCode = module.get("code").toString();
                                for (Map moduleItem : (List<Map>) module.get("moduleItem"))
                                {
                                        String moduleItemCode = moduleItem.get("code").toString();

                                        generateJavaAndWebFileOfSingleModuleItem(packagePrefix, subsysCode, moduleCode,
                                                        moduleItemCode);

                                }
                        }
                }
        }

        private void generateJavaAndWebFileOfSingleModuleItem(String packagePrefix, String subsysCode,
                        String moduleCode, String moduleItemCode) throws IOException
        {

                String packageName = packagePrefix + "."
                                + UnderlineCamelConverter.INSTANCE.javaName2PackageName(subsysCode) + "."
                                + UnderlineCamelConverter.INSTANCE.javaName2PackageName(moduleCode) + "."
                                + UnderlineCamelConverter.INSTANCE.javaName2PackageName(moduleItemCode);
                log.debug("packageName={}", packageName);

                // generate package folder
                Path packagePath = Paths.get("./"
                                + Cfg.I.get("commons.webmeta.java_src_dir", String.class, "src/main/java/") + "/"
                                + packageName.replaceAll("\\.", "/"));
                
                log.debug("packagePath={}", packagePath);
                
                packagePath.toFile().mkdirs();

                Path packageInfoPath = packagePath.resolve(Paths.get("package-info.java"));
                
                /**
                 * @author niyuzhe
                 *
                 */
                // package com.nbm.waf.boot.demo;

                String packageInfoContent = "/**\n* java package auto generated by architect booter at "
                                + new DateTime().toString() + "\n */\npackage " + packageName + ";";

                try (BufferedWriter writer = Files.newBufferedWriter(packageInfoPath, StandardCharsets.UTF_8,
                                StandardOpenOption.CREATE))
                {
                        writer.write(packageInfoContent);
                }
                
                log.info("创建目录[{}]用于模块[{}/{}]的java代码", packagePath.toString(), moduleCode, moduleItemCode);

                // generate web folder and web file

                Path webPath = Paths.get(
                                "./" + Cfg.I.get("commons.webmeta.web_src_dir", String.class, "src/main/webapp") + "/"
                                                + UnderlineCamelConverter.INSTANCE.javaName2WebPath(subsysCode) + "/"
                                                + UnderlineCamelConverter.INSTANCE.javaName2WebPath(moduleCode) + "/"
                                                + UnderlineCamelConverter.INSTANCE.javaName2WebPath(moduleItemCode));

                webPath.toFile().mkdirs();
                log.info("创建目录[{}]用于模块[{}/{}]的web代码", webPath.toString(), moduleCode, moduleItemCode);

                Path indexPath = Paths.get("./"
                                + Cfg.I.get("commons.webmeta.web_src_dir", String.class, "src/main/webapp") + "/"
                                + UnderlineCamelConverter.INSTANCE.javaName2WebPath(subsysCode) + "/"
                                + UnderlineCamelConverter.INSTANCE.javaName2WebPath(moduleCode) + "/"
                                + UnderlineCamelConverter.INSTANCE.javaName2WebPath(moduleItemCode) + "/index.jsp");

                indexPath.toFile().createNewFile();
                log.info("为模块[{}/{}]创建index.jsp[{}]", moduleCode, moduleItemCode, indexPath);
        }

        /**
         * 
         * @param resultMap
         * @return 生成的sql语句
         * @throws IOException
         */
        private String generateInitSqlFile(Map<String, ?> resultMap) throws IOException
        {

                String sqlDir = Cfg.I.get("commons.webmeta.sql_dir", String.class, "src/main/resources/sql");
                
                
                Paths.get("./" + sqlDir ).toFile().mkdirs();// 创建sql文件夹
                Path sqlFile = Paths.get("./" + sqlDir + "/init_module_auto_generate.sql");// sql文件路径

                StringBuilder result = new StringBuilder("--auto generated at " + new DateTime() + "\n");
                int subSysId = 10;// 从10开始
                for (Map subsys : (List<Map>) resultMap.get("subsys"))
                {
                        String subsysCode = subsys.get("code").toString();
                        subSysId++;

                        result.append(StringUtil.getString("-- subsys[{}] start\n", subsysCode));

                        result.append(StringUtil.getString(
                                        "INSERT INTO S_SUBSYS(ID,CODE,NAME,ABBR,WELCOME_PAGE,ORDERBY) VALUES ('{}', '{}', '{}', '{}', '{}','{}') ;\n",
                                        subSysId, subsysCode, subsys.get("name"),
                                        subsys.get("name").toString().substring(0, 1),
                                        subsys.get("welcomePage") != null ? subsys.get("welcomePage").toString() : "",
                                        subSysId));
                        result.append("\n");

                        int moduleId = subSysId * 100 + 10;
                        for (Map module : (List<Map>) subsys.get("module"))
                        {
                                String moduleCode = module.get("code").toString();
                                moduleId++;

                                result.append(StringUtil.getString("-- module[{}] start\n", moduleCode));

                                result.append(StringUtil.getString(
                                                "INSERT INTO S_MODULE(ID, CODE, NAME, SUBSYS_ID, ORDERBY) VALUES ({}, '{}', '{}', {}, {});\n",
                                                moduleId, moduleCode, module.get("name"), subSysId, moduleId));
                                result.append("\n");

                                int moduleItemId = moduleId * 100 + 10;
                                for (Map moduleItem : (List<Map>) module.get("moduleItem"))
                                {
                                        String moduleItemCode = moduleItem.get("code").toString();
                                        moduleItemId++;

                                        result.append(StringUtil.getString(
                                                        "INSERT into S_MODULE_ITEM (ID,CODE,NAME,URL,MODULE_ID,ISPUBLIC,ISSHOW,INFO_HANDLER,ORDERBY) values ({}, '{}','{}','{}',{},'{}','{}','{}',{});",
                                                        moduleItemId, moduleItemCode, moduleItem.get("name"), StringUtil
                                                                        .getString("/{}/{}/{}/index.jsp", subsysCode,
                                                                                        moduleCode, moduleItemCode),
                                                        moduleId,
                                                        moduleItem.get("isPublic") == null || StringUtils
                                                                        .isBlank(moduleItem.get("isPublic").toString())
                                                                                        ? "N"
                                                                                        : moduleItem.get("isPublic")
                                                                                                        .toString(),
                                                        moduleItem.get("isShow") == null || StringUtils
                                                                        .isBlank(moduleItem.get("isShow").toString())
                                                                                        ? "Y"
                                                                                        : moduleItem.get("isShow")
                                                                                                        .toString(),
                                                        moduleItem.get("infoHandler") == null || StringUtils.isBlank(
                                                                        moduleItem.get("infoHandler").toString())
                                                                                        ? ""
                                                                                        : moduleItem.get("infoHandler")
                                                                                                        .toString(),
                                                        moduleItemId));
                                        result.append("\n");

                                }
                                result.append(StringUtil.getString("-- module[{}] end\n", moduleCode));
                                result.append("\n");
                        }

                        result.append(StringUtil.getString("-- subsys[{}] end\n\n", subsysCode));
                }
                try (BufferedWriter writer = Files.newBufferedWriter(sqlFile, StandardCharsets.UTF_8,
                                StandardOpenOption.CREATE))
                {
                        writer.write(result.toString());
                }

                return result.toString();
        }

        private Map<String, ?> readAndGetMap() throws IOException, URISyntaxException
        {
                String result = new String(Files.readAllBytes(
                                Paths.get(this.getClass().getClassLoader().getResource(FILE_NAME).toURI())));

                Gson gson = new Gson();
                Map<String, ?> resultMap = gson.fromJson(result, Map.class);

                log.info("读取定义文件成功[{}]", FILE_NAME);

                return resultMap;
        }
}
