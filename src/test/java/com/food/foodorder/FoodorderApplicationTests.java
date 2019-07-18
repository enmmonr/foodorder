package com.food.foodorder;



import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j //可以用此注解来简化日志记录的操作，可以省略 Logger logger=  .....
public class FoodorderApplicationTests {
  //private final Logger logger= LoggerFactory.getLogger(FoodorderApplicationTests.class);  //使用了@Slf4j代替
    @Test
    public void contextLoads() {
        String name="asda";
        String password="123";
        log.error("error");
        log.debug("Debug");
        log.info("name{},password{}",name,password); //低于info级别的不会被记录，如debug

    }

}
