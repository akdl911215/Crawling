package ap.junghyun.crawling;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


@SpringBootTest
class SeleniumTestApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testURL() throws Exception{

		String urlStr = "https://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=101&oid=009&aid=0004793317";

		URL url = new URL(urlStr);

		byte[] buffer = new byte[1024*8];

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		InputStream inputStream =url.openStream();

		while(true){
			int count = inputStream.read(buffer);
			if(count == -1){
				break;
			}
			bos.write(buffer,0,count);
		}
		System.out.println(bos.toString("UTF-8"));
	}

	@Test
	public void testSelenium()throws Exception  {
		WebDriver driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "C:\\zzz\\chromedriver.exe");

		//Convenient
		driver.get("https://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=101&oid=009&aid=0004793317");

		Thread.sleep(1000);

		List<WebElement> replyList = driver.findElements(By.className(".u_cbox_comment_box"));

		for (WebElement webElement : replyList) {

			System.out.println(webElement.getText());
			System.out.println("-----------------------------------");
		}
	}
}