package chat.ros.testing2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class mainclass {

    private static DateFormat onlyDayFormat = new SimpleDateFormat("dd");
    private static DateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static void main(String[] args){
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 7);
        Date datePlusDays = c.getTime();
        String onlyDate = onlyDayFormat.format(datePlusDays);
        String fullDate = fullDateFormat.format(datePlusDays);
        System.out.println("Only day: " + onlyDate);
        System.out.println("Full date: " + fullDate);
    }

}
