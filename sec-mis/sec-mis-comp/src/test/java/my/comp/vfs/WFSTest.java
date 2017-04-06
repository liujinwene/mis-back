package my.comp.vfs;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sec.mis.lang.UuidUtils;
import com.sec.mis.vfs.WFS;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-vfs.xml" })
public class WFSTest extends AbstractJUnit4SpringContextTests {

	@Test
	public void testLocal() throws FileSystemException {
		FileObject file = WFS.resolveFile("D://test/set");

		if(!file.exists()) {
			file.createFolder();
		};
		
	}
	
	
	@Test
	public void testFtp() throws FileSystemException {
		FileObject file = VFS.getManager().resolveFile("ftp://upuser:123456789@192.168.1.200:2100/sqb2/2013/test.jsp");
		if(!file.exists()) {
			file.createFile();
		};
	}
	
	
	@Test
	public void test() throws FileSystemException {
		FileObject file = WFS.resolveFile("photo://test.jpg");

		if(!file.exists()) {
			file.createFile();
		};
		
		/*
		if(file.exists()) {
			file.delete();
		}
		*/
		System.out.println(file.getName());
		
		
		System.out.println(WFS.resolveFile("xyb1://aaaa").getName());
	}

	
	public InputStream getInputStream(String fileName){
		File file = new File(fileName);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		return in;
	}
	
	
	@Test
	public void testFileCopy(){
		String srcFilaPathAndName = "d:/test.jpg";
		File srcFile = new File(srcFilaPathAndName);
		//FileObject destFileObj = WFS.resolveFile("sqb://photo");
		File destDir = new File("d:/xyb");
		try {
			FileUtils.copyFileToDirectory(srcFile,destDir );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFileCopy2(){
		String srcFilaPathAndName = "d:/test.jpg";
		InputStream in = this.getInputStream(srcFilaPathAndName);
		
		
		try {
			org.apache.commons.io.FileUtils.copyInputStreamToFile(in, new File("d:/xyb", UuidUtils.getUuidTrimHyphen()+"jpg" ));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	
}
