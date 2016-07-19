import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Toolkit;
import javax.imageio.ImageIO;

import imagereader.IImageIO;

/*
 * Demand:
 * 利用二进制流读取Bitmap位图文件。注意，这里要求不能使用
 * Java提供的API直接读取图像，根据二进制数据创建Image时
 * 可以使用API；
 */

public class ImageIOImplement implements IImageIO{
	private static final int FF = 0xff;
	
	//把读进来的二进制流转换为十进制
	public int turnToInt(byte[] b, int i) {
		return (((int)b[i] & FF) << 24) |
				(((int)b[i-1] & FF) << 16) |
				(((int)b[i-2] & FF) << 8) |
				(((int)b[i-3] & FF));
	}

    @Override
    public Image myRead(String fpath) throws IOException {
        Image image = null;
        int bitWidth;
        int bitHeight;
        File file = new File(fpath);
        FileInputStream imagefile = new FileInputStream(fpath);
        if (file.exists()) {
        	try {
                byte headinfo[] = new byte[14];
                byte bitinfo[] = new byte[40];
                
                //位图头信息
                imagefile.read(headinfo, 0, 14);
                //位图信息
                imagefile.read(bitinfo, 0, 40);
               
                /*
                 * 字节 #18-21 (14+7)  保存位图宽度(以像素个数表示)
                 * 字节 #22-25 (14+11) 保存位图高度(以像素个数表示)
                 */
                bitWidth = turnToInt(bitinfo, 7);
                bitHeight = turnToInt(bitinfo, 11);
                
                int[] rgbData = getRgbData(imagefile, bitHeight, bitWidth);

                //创建图像
                image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(  
                               bitWidth, bitHeight, rgbData, 0, bitWidth));

            } catch (Exception e) {
                e.printStackTrace();
            }
        	imagefile.close();
        }
        
        return image;
    }

    public int[] getRgbData(FileInputStream imagefile, int bitHeight, int bitmWidth)
    		throws IOException {
    	//在24位图像里面，一个像素占3个字节
		int[] rgbData = new int[bitHeight*bitmWidth*3];
		
		//图像水平线的字节数以4的倍数保存
		int widthSize = bitmWidth*3 % 4;
		
		/*
		 * 判断跳过几个字节
		 * 若不是4的倍数则看它要跳过几个字节
		 */
		int skipNum = 0;
		if (widthSize != 0) {
			skipNum = 4 - widthSize;
		}
		
		/*
		 * 像素是从下到上、从左到右保存的
		 * 根据这个规则把二进制流读进后转换
		 * 为十进制
		 * byte:8位
		 * int:32位
		 * 成功转换要借用0xff
		 */
		for (int i = bitHeight-1; i >= 0; i--) {
			for (int j = 0; j < bitmWidth; j++) {
				rgbData[bitmWidth*i+j] = (((int)imagefile.read() & FF) |
						(((int)imagefile.read() & FF) << 8) |
						(((int)imagefile.read() & FF) << 16) |
						(255 & FF) << 24);
			}
			//跳过无关字节
			imagefile.skip(skipNum);
		}
		
		return rgbData;
	}
    
    /*
     * Write an image to a path
     * Override the Image myWrite
     */
    @Override
    public Image myWrite(Image image, String filepath) throws IOException {
    	File imagepath = new File(filepath + ".bmp");
    	int width = image.getWidth(null);
        int height = image.getHeight(null);
    	BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        try {
            bufferedImage.getGraphics().drawImage(image, 0, 0, width, height, null);
            bufferedImage.getGraphics().dispose();
            ImageIO.write(bufferedImage, "bmp", imagepath);          
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return image;
    }

}
