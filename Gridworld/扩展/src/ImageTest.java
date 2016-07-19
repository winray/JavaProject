import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


/*
 * Demand:
 *  编写Junit测试程序，测试输出的图片是否与goal
 *  文件夹下的图片一致。（比较位图宽度、位图高度
 *  以及像素值）
 */
/*
 * 此Test用字符串读取了绝对路径，可以在任何文件目录
 * 成功运行，只要不破坏此项目的完整性
 */
public class ImageTest {
	private File filepathFile;
	private String abstring;
	private String diString;
	private String dimString;

	private String sourceFilePath;
	private String myFilePath;
	
	@Before
	public void init() {
		filepathFile = new File(this.getClass().getResource("").getPath());
		abstring = filepathFile.getAbsolutePath();
		diString = abstring.substring(0, abstring.length()-3)+"goal/";
		dimString = abstring.substring(0, abstring.length()-3)+"my/";
	}

	
	//Test: java API的读取和myRead的读取是否一样
	@Test
	public void testRead() throws IOException {
		//The same picture path.
		sourceFilePath = diString+"1_red_goal.bmp";

		FileInputStream in = new FileInputStream(sourceFilePath);
		BufferedImage goal = ImageIO.read(in);
		
		ImageIOImplement imageioer = new ImageIOImplement();
		Image image= imageioer.myRead(sourceFilePath);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(image, 0, 0, width, height, null);

		assertEquals(goal.getWidth(null),image.getWidth(null));
		assertEquals(goal.getHeight(null),image.getHeight(null));
		
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}
	
	//Red
	@Test
	public void testShowChanelR1() {
		sourceFilePath = diString+"1_red_goal.bmp";
		myFilePath = dimString+"my1_red.bmp";
		RunTest runner = new RunTest(sourceFilePath, myFilePath);
		runner.init();
		runner.testHeight();
		runner.testWidth();
		runner.testRGB();
	}
	
	//Green
	@Test
	public void testShowChanelG1() {
		sourceFilePath = diString+"1_green_goal.bmp";
		myFilePath = dimString+"my1_green.bmp";
		RunTest runner = new RunTest(sourceFilePath, myFilePath);
		runner.init();
		runner.testHeight();
		runner.testWidth();
		runner.testRGB();
	}
	
	//Blue
	@Test
	public void testShowChanelB1() {
		sourceFilePath = diString+"1_blue_goal.bmp";
		myFilePath = dimString+"my1_blue.bmp";
		RunTest runner = new RunTest(sourceFilePath, myFilePath);
		runner.init();
		runner.testHeight();
		runner.testWidth();
		runner.testRGB();
	}
	
	//Gray
	@Test
	public void testShowGray1() {
		sourceFilePath = diString+"1_gray_goal.bmp";
		myFilePath = dimString+"my1_gray.bmp";
		RunTest runner = new RunTest(sourceFilePath, myFilePath);
		runner.init();
		runner.testHeight();
		runner.testWidth();
		runner.testRGB();
	}
	
	//Red
	@Test
	public void testShowChanelR2() {
		sourceFilePath = diString+"2_red_goal.bmp";
		myFilePath = dimString+"my2_red.bmp";
		RunTest runner = new RunTest(sourceFilePath, myFilePath);
		runner.init();
		runner.testHeight();
		runner.testWidth();
		runner.testRGB();
	}
	
	//Green
	@Test
	public void testShowChanelG2() {
		sourceFilePath = diString+"2_green_goal.bmp";
		myFilePath = dimString+"my2_green.bmp";
		RunTest runner = new RunTest(sourceFilePath, myFilePath);
		runner.init();
		runner.testHeight();
		runner.testWidth();
		runner.testRGB();
	}
	
	//Blue
	@Test
	public void testShowChanelB2() {
		sourceFilePath = diString+"2_blue_goal.bmp";
		myFilePath = dimString+"my2_blue.bmp";
		RunTest runner = new RunTest(sourceFilePath, myFilePath);
		runner.init();
		runner.testHeight();
		runner.testWidth();
		runner.testRGB();
	}
	
	//Gray
	@Test
	public void testShowGray2() {
		sourceFilePath = diString+"2_gray_goal.bmp";
		myFilePath = dimString+"my2_gray.bmp";
		RunTest runner = new RunTest(sourceFilePath, myFilePath);
		runner.init();
		runner.testHeight();
		runner.testWidth();
		runner.testRGB();
	}
}

class RunTest
{
	private String sourceFile = null;
	private String myFile = null;
	
	BufferedImage bi;
	BufferedImage mybi;
	
	public RunTest(String sf, String mf)
	{
		sourceFile = sf;
		myFile = mf;
	}
	
	//initialize test
	public void init()
	{
		 File sourcrImage = new File(sourceFile);
		 File myImage = new File(myFile);
		 try {
			 bi = ImageIO.read(sourcrImage);
			 mybi = ImageIO.read(myImage);
		 } catch(Exception e){   
			 e.printStackTrace();   
		 } 
	}
	
	//Test BitHeight
	public void testHeight()
	{
		assertEquals(bi.getWidth(), mybi.getWidth());
	}
	
	//Test bitWidth
	public void testWidth()
	{
		assertEquals(mybi.getHeight(), mybi.getHeight());
	}
	
	//Test RGB
	public void testRGB()
	{
		 for(int i = 0; i < mybi.getWidth();i++) {   
			 for(int j = 0; j < mybi.getHeight();j++) {
				 assertEquals(bi.getRGB(i, j), mybi.getRGB(i, j));
			 }
		 }
	}
}

