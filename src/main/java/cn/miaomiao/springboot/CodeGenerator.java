package cn.miaomiao.springboot;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 一个自动生成数据库实体类工具
 * 使用的是mybatis-plus自带的工具，具体参考https://mybatis.plus/guide/#%E7%89%B9%E6%80%A7
 * 稍微暴力修改了下，只生成entity实体类，正常会生成mybatis-plus配套的controller、service等
 * @author miaomiao
 * @date 2019/4/17 17:51
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("miaomiao");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://47.101.154.8:3306/miaomiao?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("miaomiao");
        dsc.setPassword("miaomiao");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("springboot");
        pc.setParent("cn.miaomiao");
        Map<String, String> pathInfo = new HashMap<>(2);
        String path = gc.getOutputDir() + "." + pc.getParent() + "." + pc.getEntity();
        path = path.replaceAll("\\.", "/");
        pathInfo.put("entity_path", path);
        pc.setPathInfo(pathInfo);
        mpg.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(scanner().split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);

        // 手动设置只生成entity
        mpg.setConfig(new ConfigBuilder(mpg.getPackageInfo(), mpg.getDataSource(), mpg.getStrategy(), mpg.getTemplate(), mpg.getGlobalConfig()));
        ConfigBuilder config = mpg.getConfig();
        List<TableInfo> list = config.getTableInfoList();
        for (TableInfo tableInfo : list) {
            tableInfo.setMapperName(null);
            tableInfo.setXmlName(null);
            tableInfo.setServiceName(null);
            tableInfo.setServiceImplName(null);
            tableInfo.setControllerName(null);
        }
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + "表名，多个英文逗号分割" + "："));
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + "表名，多个英文逗号分割" + "！");
    }
}
