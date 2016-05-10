package com.nbm.httpclient;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.httpclient.model.ClientInstance;
import com.nbm.httpclient.model.XmlClientBuilder;


/**
 * 将某个文件夹下的xml统一读入的
 */
public class FolderXmlClientBuilder
{
	
	private final static Logger log = LoggerFactory.getLogger(FolderXmlClientBuilder.class);
	
	
	/**
	 * 从classpath开始的相对路径.
	 * 
	 * 如果传入的是文件路径（File.isDirectory() == false），则从此配置文件开始读取，并且以此配置文件所在目录为根目录，扫描所有配置文件。
	 * 
	 * 如果传入的是目录，则直接读取所有文件
	 */
	private String folder;

	
	public FolderXmlClientBuilder(String folder)
	{
		this.folder = folder;
	}

	public ClientInstance build()
	{

		final ClientInstance instance = new ClientInstance();

		try
		{
			Path path = Paths.get(this.getClass()
					.getClassLoader()
					.getResource(folder)
					.toURI());
			
			File rootFile = path.toFile();
			
			if( !rootFile.isDirectory() )
			{
				log.debug("传入的是文件，优先处理[{}]", rootFile);
				instance.merge(new XmlClientBuilder(
						rootFile.toURI())
						.build());
				path = rootFile.getParentFile().toPath();
			}else
			{
				rootFile = null;
			}
			
			final File rootFileTemp = rootFile;//给下面的闭包使用
			final Path pathTemp = path;//给下面的闭包使用
			
			//遍历该目录下的所有文件，所以不用再支持import了
			Files.walkFileTree(path,
					new SimpleFileVisitor<Path>()
					{

						@Override
						public FileVisitResult visitFile(
								Path file,
								BasicFileAttributes attrs)
								throws IOException
						{
							
							log.debug("开始处理{}", file
									.toUri());
							
							if( file.toFile().equals(rootFileTemp))
							{
								log.debug("已经处理过rootFile{}", file);
							}else
							{
								try
								{
									instance.merge(new XmlClientBuilder(
											file.toUri())
											.build());
								} catch (Exception e)
								{
									throw new RuntimeException("处理" + file + "时出现错误", e);
								}
							}
							
							return FileVisitResult.CONTINUE;
						}

					});
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try( DirectoryStream<Path> stream
		// =
		// Files.newDirectoryStream(Paths.get(this.getClass().getClassLoader().getResource(folder).toURI()),
		// "*.xml"))
		// {
		// for( Path entry : stream )
		// {
		// System.out.println(entry.getFileName());
		// }
		// } catch (IOException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (URISyntaxException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// XmlClientBuilder c = new

		return instance;

	}
	
	public static void main(String[] args)
	{
		ClientInstance instance = new FolderXmlClientBuilder("cofnig_tjpop").build();
		
	}
}
