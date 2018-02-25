package com.younker.waf.dbgrid;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nbm.exception.NbmBaseRuntimeException;

public class DBGridGeneratorTest
{

        @BeforeClass
        public static void setUpBeforeClass() throws Exception
        {
        }

        @AfterClass
        public static void tearDownAfterClass() throws Exception
        {
        }

        @Before
        public void setUp() throws Exception
        {
        }

        @After
        public void tearDown() throws Exception
        {
        }

        @Test
        public void test()
        {
                DBGrids dbgrids = DBGridGenerator.getInstance()
                                .getDBGrids("dbgrid_config/dbgrids.xml");

                assertNotNull(dbgrids);
                // assertNotNull(dbgrids.getDBGrid("/public/orgNameQuery/orgNameQuery"));

                assertNotNull(dbgrids.getDBGrid(
                                "/woman_manage/batch_handle/approval_move/moveApprovalSearch"));

                String notExist = "dbgrid_config/dbgrids_this_file_must_not_exist.xml";
                try
                {
                        DBGrids dbgrids_error = DBGridGenerator.getInstance()
                                        .getDBGrids(notExist);
                        fail("should be fail");
                } catch (NbmBaseRuntimeException e)
                {
                        assertEquals(notExist, e.get("configFilePath"));
                }

        }

        @Test
        public void testDbgrid()
        {
                DBGrids dbgrids = DBGridGenerator.getInstance()
                                .getDBGrids("dbgrid_config/dbgrids.xml");
                DBGrid dbgrid = dbgrids.getDBGrid(
                                "/public/message/displaymessageuser");
                System.out.println(dbgrid.generateCountSql());

        }

        @Test
        public void testGenerateDir()
        {
                Path path = Paths.get("dbgrid_config");// 在eclipse中运行此程序时，用.获取的是项目源代码根目录

                try
                {
                        Files.walkFileTree(path, new SimpleFileVisitor<Path>()
                        {

                                @Override
                                public FileVisitResult visitFile(Path file,
                                                BasicFileAttributes attrs)
                                                throws IOException
                                {
                                        System.out.println(file.toFile().getPath());
                                        return FileVisitResult.CONTINUE;
                                }

                        });
                } catch (IOException e)
                {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

}
