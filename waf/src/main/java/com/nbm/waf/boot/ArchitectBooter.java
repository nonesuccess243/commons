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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.nbm.commons.db.meta.UnderlineCamelConverter;
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

        private final static Logger log = LoggerFactory
                        .getLogger(ArchitectBooter.class);

        private final static String FILE_NAME = "webmeta.json";

        public void boot() throws Exception
        {
                Map<String, ?> resultMap = readAndGetMap();

                generateJavaPackage(resultMap);
                generateInitSqlFile(resultMap);
                generateInitDbgrid(resultMap);

        }

        private void generateInitDbgrid(Map<String, ?> resultMap)
                        throws IOException
        {
                Paths.get("./src/main/resources/dbgrid_config").toFile()
                                .mkdirs();// 创建sql文件夹

                for (Map subsys : (List<Map>) resultMap.get("subsys"))
                {
                        String subsysCode = subsys.get("code").toString();

                        Paths.get("./src/main/resources/dbgrid_config/"
                                        + subsysCode).toFile().mkdirs();

                        for (Map module : (List<Map>) subsys.get("module"))
                        {
                                String moduleCode = module.get("code")
                                                .toString();

                                Paths.get("./src/main/resources/dbgrid_config/"
                                                + subsysCode + "/" + moduleCode
                                                + ".xml").toFile()
                                                .createNewFile();

                                // for (Map moduleItem : (List<Map>)
                                // module.get("moduleItem"))
                                // {
                                // String moduleItemCode =
                                // moduleItem.get("code").toString();
                                // }
                        }
                }

        }

        private void generateJavaPackage(Map<String, ?> resultMap)
                        throws IOException
        {
                String packagePrefix = UnderlineCamelConverter.INSTANCE
                                .javaName2PackageName(resultMap.get("group")
                                                .toString())
                                + "."
                                + UnderlineCamelConverter.INSTANCE
                                                .javaName2PackageName(resultMap
                                                                .get("name").toString());

                System.out.println(resultMap.get("subsys").getClass());

                for (Map subsys : (List<Map>) resultMap.get("subsys"))
                {
                        String subsysCode = subsys.get("code").toString();
                        for (Map module : (List<Map>) subsys.get("module"))
                        {
                                String moduleCode = module.get("code")
                                                .toString();
                                for (Map moduleItem : (List<Map>) module
                                                .get("moduleItem"))
                                {
                                        String moduleItemCode = moduleItem
                                                        .get("code").toString();

                                        // generate package folder
                                        Path packagePath = Paths
                                                        .get("./src/main/java/"
                                                                        + packagePrefix.replace(
                                                                                        ".",
                                                                                        "/")
                                                                        + "/"
                                                                        + UnderlineCamelConverter.INSTANCE
                                                                                        .javaName2PackageName(
                                                                                                        subsysCode)
                                                                        + "/"
                                                                        + UnderlineCamelConverter.INSTANCE
                                                                                        .javaName2PackageName(
                                                                                                        moduleCode)
                                                                        + "/"
                                                                        + UnderlineCamelConverter.INSTANCE
                                                                                        .javaName2PackageName(
                                                                                                        moduleItemCode));
                                        log.debug(packagePath.toFile()
                                                        .getAbsolutePath());
                                        packagePath.toFile().mkdirs();

                                        String path = "./src/main/webapp/"
                                                        + UnderlineCamelConverter.INSTANCE
                                                                        .javaName2WebPath(
                                                                                        subsysCode)
                                                        + "/"
                                                        + UnderlineCamelConverter.INSTANCE
                                                                        .javaName2WebPath(
                                                                                        moduleCode)
                                                        + "/"
                                                        + UnderlineCamelConverter.INSTANCE
                                                                        .javaName2WebPath(
                                                                                        moduleItemCode);

                                        // generate web folder and web file

                                        Path webPath = Paths
                                                        .get("./src/main/webapp/"
                                                                        + UnderlineCamelConverter.INSTANCE
                                                                                        .javaName2WebPath(
                                                                                                        subsysCode)
                                                                        + "/"
                                                                        + UnderlineCamelConverter.INSTANCE
                                                                                        .javaName2WebPath(
                                                                                                        moduleCode)
                                                                        + "/"
                                                                        + UnderlineCamelConverter.INSTANCE
                                                                                        .javaName2WebPath(
                                                                                                        moduleItemCode));

                                        log.debug("webpath={}", webPath.toFile()
                                                        .getAbsolutePath());
                                        webPath.toFile().mkdirs();
                                        
                                        Path indexPath = Paths
                                                        .get("./src/main/webapp/"
                                                                        + UnderlineCamelConverter.INSTANCE
                                                                        .javaName2WebPath(
                                                                                        subsysCode)
                                                                        + "/"
                                                                        + UnderlineCamelConverter.INSTANCE
                                                                        .javaName2WebPath(
                                                                                        moduleCode)
                                                                        + "/"
                                                                        + UnderlineCamelConverter.INSTANCE
                                                                        .javaName2WebPath(
                                                                                        moduleItemCode) + "/index.jsp");
                                        
                                        indexPath.toFile().createNewFile();
                                        

                                }
                        }
                }
        }


        /**
         * 
         * @param resultMap
         * @return 生成的sql语句
         * @throws IOException
         */
        private String generateInitSqlFile(Map<String, ?> resultMap)
                        throws IOException
        {

                StringBuilder result = new StringBuilder();

                Paths.get("./sql").toFile().mkdirs();// 创建sql文件夹
                Path sqlFile = Paths.get("./sql/init_module.sql");// sql文件路径

                int subSysId = 10;// 从10开始
                for (Map subsys : (List<Map>) resultMap.get("subsys"))
                {
                        String subsysCode = subsys.get("code").toString();
                        subSysId++;

                        result.append(StringUtil.getString(
                                        "-- subsys[{}] start\n", subsysCode));

                        result.append(StringUtil.getString(
                                        "INSERT INTO S_SUBSYS(ID,CODE,NAME,ABBR,WELCOME_PAGE,ORDERBY) VALUES ('{}', '{}', '{}', '{}', '{}','{}') ;\n",
                                        subSysId, subsysCode,
                                        subsys.get("name"),
                                        subsys.get("name").toString()
                                                        .substring(0, 1),
                                        subsys.get("welcomePage") != null
                                                        ? subsys.get("welcomePage")
                                                                        .toString()
                                                        : "",
                                        subSysId));
                        result.append("\n");

                        int moduleId = subSysId * 100 + 10;
                        for (Map module : (List<Map>) subsys.get("module"))
                        {
                                String moduleCode = module.get("code")
                                                .toString();
                                moduleId++;

                                result.append(StringUtil.getString(
                                                "-- module[{}] start\n",
                                                moduleCode));

                                result.append(StringUtil.getString(
                                                "INSERT INTO S_MODULE(ID, CODE, NAME, SUBSYS_ID, ORDERBY) VALUES ({}, '{}', '{}', {}, {});\n",
                                                moduleId, moduleCode,
                                                module.get("name"), subSysId,
                                                moduleId));
                                result.append("\n");

                                int moduleItemId = moduleId * 100 + 10;
                                for (Map moduleItem : (List<Map>) module
                                                .get("moduleItem"))
                                {
                                        String moduleItemCode = moduleItem
                                                        .get("code").toString();
                                        moduleItemId++;

                                        result.append(StringUtil.getString(
                                                        "INSERT into S_MODULE_ITEM (ID,CODE,NAME,URL,MODULE_ID,ISPUBLIC,ISSHOW,INFO_HANDLER,ORDERBY) values ({}, '{}','{}','{}',{},'{}','{}','{}',{});",
                                                        moduleItemId,
                                                        moduleItemCode,
                                                        moduleItem.get("name"),
                                                        StringUtil.getString(
                                                                        "/{}/{}/{}/index.jsp",
                                                                        subsysCode,
                                                                        moduleCode,
                                                                        moduleItemCode),
                                                        moduleId,
                                                        moduleItem.get("isPublic") == null
                                                                        || StringUtils.isBlank(
                                                                                        moduleItem.get("isPublic")
                                                                                                        .toString()) ? "N"
                                                                                                                        : moduleItem.get(
                                                                                                                                        "isPublic")
                                                                                                                                        .toString(),
                                                        moduleItem.get("isShow") == null
                                                                        || StringUtils.isBlank(
                                                                                        moduleItem.get("isShow")
                                                                                                        .toString()) ? "Y"
                                                                                                                        : moduleItem.get(
                                                                                                                                        "isShow")
                                                                                                                                        .toString(),
                                                        moduleItem.get("infoHandler") == null
                                                                        || StringUtils.isBlank(
                                                                                        moduleItem.get("infoHandler")
                                                                                                        .toString()) ? ""
                                                                                                                        : moduleItem.get(
                                                                                                                                        "infoHandler")
                                                                                                                                        .toString(),
                                                        moduleItemId));
                                        result.append("\n");

                                }
                                result.append(StringUtil.getString(
                                                "-- module[{}] end\n",
                                                moduleCode));
                                result.append("\n");
                        }

                        result.append(StringUtil.getString(
                                        "-- subsys[{}] end\n\n", subsysCode));
                }
                try (BufferedWriter writer = Files.newBufferedWriter(sqlFile,
                                StandardCharsets.UTF_8,
                                StandardOpenOption.CREATE))
                {
                        writer.write(result.toString());
                }

                return result.toString();
        }

        private Map<String, ?> readAndGetMap()
                        throws IOException, URISyntaxException
        {
                String result = new String(Files.readAllBytes(Paths.get(this
                                .getClass().getClassLoader()
                                .getResource(FILE_NAME).toURI())));

                Gson gson = new Gson();
                Map<String, ?> resultMap = gson.fromJson(result, Map.class);
                return resultMap;
        }
}
