package cn.miaomiao.api.utils;

import cn.miaomiao.api.constant.LogConstant;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author miaomiao
 * @date 2019/4/26 14:31
 */
@Slf4j
public class PinyinUtil {
    private static final PinyinUtil INSTANCE = new PinyinUtil();
    private PinyinUtil(){
    }

    public static PinyinUtil getInstance(){
        return INSTANCE;
    }
    private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    static {
        // 设置汉字拼音输出的格式
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 生成拼音首字母
     */
    public String getPinyin(String msg){
        char[] chars = msg.trim().replaceAll(" ", "").toCharArray();
        StringBuilder result = new StringBuilder();
        try {
            for (char c : chars) {
                // 判断能否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    // 将汉字的几种全拼都存到t2数组中
                    String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    // 取出该汉字全拼的第一种读音并连接到字符串t4后
                    result.append(pinyin[0], 0, 1).append(" ");
                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串t4后
                    result.append(c).append(" ");
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error(LogConstant.PINYIN_EXCEPTION + "[msg]：" + msg);
        }
        return result.toString();
    }
}
