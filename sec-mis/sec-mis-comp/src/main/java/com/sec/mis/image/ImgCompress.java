package com.sec.mis.image;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.*;


public class ImgCompress {
	private Image img;
	private int width;	//图片宽度
	private int height;	//图片高度
	private String srcFilePath;		//源图片路径
	private String srcFileName;		//源图片文件名
	private String destFilePath;	//目的图片路径
	private String destFileName;	//目的图片文件名
	
	public static void main(String[] args) throws Exception{
		ImgCompress imgCompress = new ImgCompress("C://temp","test.jpg","C://temp","test2.jpg");
		imgCompress.resizeByWidth(200);
	}
	
	/**
	 * 构造函数
	 */
	public ImgCompress(String srcFilePath,String srcFileName, String destFilePath, String destFileName) {
		this.srcFilePath = srcFilePath;
		this.srcFileName = srcFileName;
		this.destFilePath = destFilePath;
		this.destFileName = destFileName;
		
		File file = new File(srcFilePath+File.separator+srcFileName);// 读入文件
		try {
			img = ImageIO.read(file);		// 构造Image对象
			width = img.getWidth(null);    // 得到源图宽
			height = img.getHeight(null);  // 得到源图长
		} catch (IOException e) {
			e.printStackTrace();
		}      

	}
	
	/**
	 * 构造函数
	 */
	public ImgCompress(InputStream inputStream, String destFilePath, String destFileName) {
		this.destFilePath = destFilePath;
		this.destFileName = destFileName;
		
		try {
			img = ImageIO.read(inputStream);		// 构造Image对象
			width = img.getWidth(null);    // 得到源图宽
			height = img.getHeight(null);  // 得到源图长
		} catch (IOException e) {
			e.printStackTrace();
		}      

	}
	
	

	/**
	 * 按照宽度还是高度进行压缩
	 * @param w int 最大宽度
	 * @param h int 最大高度
	 */
	public void resizeFix(int w, int h) throws IOException {
		if (width / height > w / h) {
			resizeByWidth(w);
		} else {
			resizeByHeight(h);
		}
	}
	
	/**
	 * 以宽度为基准，等比例放缩图片
	 * @param w int 新宽度
	 */
	public void resizeByWidth(int w) throws IOException {
		int h = (int) (height * w / width);
		resize(w, h);
	}
	/**
	 * 以高度为基准，等比例缩放图片
	 * @param h int 新高度
	 */
	public void resizeByHeight(int h) throws IOException {
		int w = (int) (width * h / height);
		resize(w, h);
	}
	/**
	 * 强制压缩/放大图片到固定的大小
	 * @param w int 新宽度
	 * @param h int 新高度
	 */
	@SuppressWarnings("restriction")
	public void resize(int w, int h) throws IOException {
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB ); 
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		File destFile = new File(this.destFilePath+File.separator+this.destFileName);
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// 可以正常实现bmp、png、gif转jpg
		JPEGImageEncoder createJPEGEncoder = JPEGCodec.createJPEGEncoder(out);
		JPEGImageEncoder encoder = createJPEGEncoder;
		encoder.encode(image); // JPEG编码
		out.close();
	}
}