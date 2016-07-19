import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.awt.Toolkit;
import imagereader.IImageProcessor;

/*
 * 把读取彩色图像转换成灰度图像
 * 提取并且显示彩色图像各个色彩通道
 */
public class ImageProcessor implements IImageProcessor{

	//一些常用值
    private static int filterrde = 0xffff0000;
    private static int filtergreen = 0xff00ff00;
    private static int fliterBlue = 0xff0000ff;
    private static int Red = 0x00ff0000;
    private static int Green = 0x0000ff00;
    private static int Blue = 0x000000ff;
    private static double R= 0.299;
    private static double G = 0.587;
    private static double B = 0.114;
    
    //用于创建图像
    private Toolkit kit = Toolkit.getDefaultToolkit();
    
    //Turn Red
    class RedFilter extends RGBImageFilter {
        public int filterRGB(int a, int b, int c) {
            return (c & filterrde);
        }
    }
    
    //Turn Red
    @Override
    public Image showChanelR(Image image) {
        RedFilter red = new RedFilter();
        return kit.createImage(new FilteredImageSource(image.getSource(),red));
    }
    
    //Turn Green
    class GreenFilter extends RGBImageFilter {

        public int filterRGB(int a, int b, int c) {
            return (c & filtergreen);
        }
    }

    //Turn Green
    @Override
    public Image showChanelG(Image arg0) {
        GreenFilter green = new GreenFilter();
        return kit.createImage(new FilteredImageSource(arg0.getSource(),green));
    }

    //Turn Blue
    class BlueFilter extends RGBImageFilter {

        public int filterRGB(int a, int b, int c) {
            return (c & fliterBlue);
        }
    }
    
 
    //Turn Blue
    @Override
    public Image showChanelB(Image arg0) {
        BlueFilter blue = new BlueFilter();
        return kit.createImage(new FilteredImageSource(arg0.getSource(),blue));
    }

    //Turn Gray
    class GrayFilter extends RGBImageFilter {

        public int filterRGB(int a, int b, int c) {
            
            int red = (c & Red) >> 16;
            int green = (c & Green) >> 8;        
            int blue = (c & Blue);
            int gray = (int)((double)(R * red) + (double)(G * green) + (double)(B * blue));
            return (c & 0xff000000) | (gray << 16) | (gray << 8) | gray;
        }
    }

    //Turn Gray
    @Override
    public Image showGray(Image arg0) {
        GrayFilter gray = new GrayFilter();
        return kit.createImage(new FilteredImageSource(arg0.getSource(),gray));
    }

}
