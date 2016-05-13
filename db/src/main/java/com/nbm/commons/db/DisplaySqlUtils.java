/***********************************************
 * Title:       DisplayUtils.java
 * Description: DisplayUtils.java
 * Create Date: 2013-7-7
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.commons.db;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.commons.PackageUtils;
import com.nbm.commons.db.meta.DbNamingConverter;
import com.nbm.commons.enums.CodeEnum;
import com.nbm.commons.enums.CodeEnumItem;
import com.nbm.commons.enums.Displayable;
import com.younker.waf.utils.StringUtil;

/**
 *
 */
public class DisplaySqlUtils
{
        private final static Logger log = LoggerFactory.getLogger(DisplaySqlUtils.class);

        private List<Class<?>> foundClass = new ArrayList<Class<?>>();

        public DisplaySqlUtils(String parentPackage)
        {
                for (String className : PackageUtils.getClassName(parentPackage))
                {
                        // log.debug(className);
                        Class<?> claz = null;
                        try
                        {
                                claz = Class.forName(className);
                        } catch (Throwable e)
                        {
                                log.error("forName异常", e);
                                continue;
                        }
                        if (isDisplayableEnum(claz))
                        {
                                foundClass.add(claz);
                        }
                }
        }

        public String generateAll()
        {
                StringBuffer sb = new StringBuffer();
                for (Class<?> claz : foundClass)
                {
                        sb.append(generateSql(claz));
                        sb.append("\n");
                }
                return sb.toString();
        }

        private static String generateSql(Class<?> claz)
        {
                StringBuffer sb = new StringBuffer();

                CodeEnum a = claz.getAnnotation(CodeEnum.class);

                if (a == null)
                {
                        log.error(claz.getName() + "没加CodeEnum");
                        return "";
                }

                String typeCodeName = DbNamingConverter.DEFAULT_ONE
                                .javaPropertyName2ColumnName(claz.getSimpleName());

                sb.append("INSERT INTO S_CODETYPE (ID, CODE, NAME, REMARK) VALUES (S_CODETYPE_SEQ.nextval,'"
                                + typeCodeName + "','" + a.name() + "','" + a.remark() + "');");
                sb.append("\n");

                for (Field f : claz.getDeclaredFields())
                {
                        if (!f.isEnumConstant())
                                continue;
                        CodeEnumItem ce = f.getAnnotation(CodeEnumItem.class);
                        if (ce == null)
                        {
                                throw new RuntimeException(claz.getName() + "的" + f.getName()
                                                + "字段没加CodeEnumItem");
                        }
                        sb.append(

                        StringUtil.getString(
                                        "INSERT INTO S_CODE (ID, TYPECODE, CODE, NAME, REMARK, SHOWORDER, AVAILABLE, SYSTYPE, MNEMONIC) VALUES ("
                                                        + "S_CODE_SEQ.nextval, '{}', '{}', '{}', '{}', '{}', 'Y', 'Y', '');",
                                        typeCodeName, f.getName(), ce.displayName(), ce.remark(),
                                        ce.showOrder()));
                        sb.append("\n");
                }

                return sb.toString();
        }

        private static boolean isDisplayableEnum(Class<?> claz)
        {
                Class<?>[] instances = claz.getInterfaces();
                for (Class<?> c : instances)
                {
                        if (c == Displayable.class)
                        {
                                return true;
                        }
                }
                return false;
        }

        public final static void main(String[] args)
        {

//                try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("sql/enum.sql"),
//                                StandardCharsets.UTF_8))
//                {
//                        writer.write(new DisplaySqlUtils("com.nbm.bhjs.datacheck.tjpop.lb.enums").generateAll());
//                } catch (IOException e)
//                {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                }

                // System.out.println(new
                // DisplayUtils("com.nbm").generateAll());
                System.out.println(new DisplaySqlUtils("com.nbm.bhjs.birthservice.model.enums").generateAll());
        }
}
