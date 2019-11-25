package Tools;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
	public static String longToString(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    return format.format(date);
	}
}
