import com.planitary.message.push.Utils.DateUtils;
import com.planitary.message.push.handler.DateHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class DateTest {

    @Resource
    DateHandler dateHandler;
    @Test
    public void BirthTest(){
        String birthday = "1996-01-11";
        long currentDayToBirth = dateHandler.currentDayToBirth(birthday);
        System.out.println(currentDayToBirth);
    }
}
