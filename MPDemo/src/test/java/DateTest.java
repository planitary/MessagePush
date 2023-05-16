import com.planitary.message.push.Utils.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DateTest {

    @Test
    public void BirthTest(){
        String birthday = "1996-01-11";
        long currentDayToBirth = DateUtils.currentDayToBirth(birthday);
        System.out.println(currentDayToBirth);
    }
}
