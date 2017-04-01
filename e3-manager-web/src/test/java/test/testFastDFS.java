package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.manager.utils.FastDFSClient;
import junit.framework.TestCase;

public class testFastDFS  {
	
	@Test
	public void test() throws Exception, IOException, MyException{
		
		String conf_filename =this.getClass().getResource("/fdfs/fdfs_client.conf").getPath();
		//解决中文路径问题
		 conf_filename = URLDecoder.decode(conf_filename, "utf-8");
		//创建初始化信息
		ClientGlobal.init(conf_filename );
		//创建Tracker_client
		TrackerClient trackerClient = new TrackerClient();
		//创建Tracker_server
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建Storage Server
		StorageServer storageServer=null;
		//创建StoragerClient
		//获取StoragerClient 有两中途径, trackerServer  storageServer
		//如果storageServer不为空,则自动选storageServer,如果storageServer为空选择trackerServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		String local_filename="E://Koala.jpg";
		String file_ext_name="jpg";
		//调用StoragerClient方法实现上传和下载
		String[] upload_file = storageClient.upload_file(local_filename, file_ext_name,null);
		for (String string : upload_file) {
			System.out.println(string);
		}
		
	}
	@Test 
	public void test2 () throws Exception{
		FastDFSClient client=new FastDFSClient();
		String fileName="E://2.jpg";
		String extName="jpg";
		String uploadFile = client.uploadFile(fileName, extName);
		System.out.println(uploadFile);
		
		
	}

}
