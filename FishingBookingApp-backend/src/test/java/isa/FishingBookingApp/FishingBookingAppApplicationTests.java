package isa.FishingBookingApp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class FishingBookingAppApplicationTests {

	@Test
	void contextLoads() {
		assertThat(true).isTrue();
	}

}
