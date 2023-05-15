import com.planitary.message.push.Utils.DateUtils;
import org.junit.Test;

public class DateTest {


    @Test
    public void BirthTest(){
        String birthday = "1996-06-25";
        long currentDayToBirth = DateUtils.currentDayToBirth(birthday);
        System.out.println(currentDayToBirth);
    }
}
