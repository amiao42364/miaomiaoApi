package cn.miaomiao.api.utils;

import cn.miaomiao.api.constant.StringConstant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 时间工具类
 *
 * @author miaomiao
 * @date 2019/5/13 14:44
 */

@SuppressWarnings("unused")
public class DateUtil {

    /**
     * 用于计算星座，俺也不知道什么原理呀
     */
    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座",
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
    private final static String[] zodiacs = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    /**
     * string转LocalDate
     *
     * @param time   time
     * @param format format
     * @return LocalDate
     */
    public static LocalDate stringToDate(String time, String format) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(time, DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException e) {
            return null;
        }
        return localDate;
    }

    /**
     * string转LocalDate
     *
     * @param time time
     * @return LocalDate
     */
    public static LocalDate stringToDate(String time) {
        return stringToDate(time, StringConstant.DEFAULT_DATA_FORMAT);
    }

    /**
     * 返回当前时间的string类型
     *
     * @return DateStr
     */
    public static String nowDateStr() {
        return nowDateStr(StringConstant.DEFAULT_DATA_TIME_FORMAT);
    }

    /**
     * 返回当前时间的string类型
     *
     * @param format 时间格式
     * @return DateStr
     */
    public static String nowDateStr(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 根据生日计算年龄
     *
     * @param birthday 生日
     * @return 年龄
     */
    public static Integer getAge(String birthday) {
        if (VerifyEmptyUtil.isEmpty(birthday)) {
            return null;
        }
        LocalDate birth = stringToDate(birthday);
        if (birth == null) {
            return null;
        }
        LocalDate now = LocalDate.now();
        Period period = Period.between(birth, now);
        return period.getYears();
    }

    /**
     * 通过生日计算星座
     *
     * @param birthday 生日
     * @return 星座
     */
    public static String getConstellation(String birthday) {
        if (VerifyEmptyUtil.isEmpty(birthday)) {
            return "";
        }
        LocalDate birth = stringToDate(birthday);
        if (birth == null) {
            return "";
        }
        int month = birth.getMonthValue();
        int day = birth.getDayOfMonth();
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    /**
     * 通过生日计算生肖
     *
     * @param birthday 生日
     * @return 生肖
     */
    public static String getZodiac(String birthday) {
        if (!VerifyEmptyUtil.isEmpty(birthday)) {
            LocalDate birth = stringToDate(birthday);
            if (birth == null) {
                return "";
            }
            int year = birth.getYear();
            if (year < 1900) {
                return "未知";
            }
            int start = 1900;
            return zodiacs[(year - start) % zodiacs.length];
        } else {
            return "";
        }
    }
}
