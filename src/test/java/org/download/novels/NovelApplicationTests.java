package org.download.novels;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class NovelApplicationTests {

	private String urlTEst = "https://www.koreanmtl.online/2021/05/reformation-of-deadbeat-noble-1.html";

	@Test
	void contextLoads() {
		assertDoesNotThrow(()->Jsoup.connect(urlTEst).get());
	}

}
