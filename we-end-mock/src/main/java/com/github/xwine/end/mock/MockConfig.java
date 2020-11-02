package com.github.xwine.end.mock;

import com.github.xwine.end.mock.constraint.VMEnum;

import java.math.BigDecimal;
import java.util.*;

public class MockConfig {
    private Boolean weEndOn = false;
    private Boolean mockOn = false;
    private String appName = "we-end";
    private String nowUser = "no-name";
    private String path = "/export/data/mock";
    private Integer deep = 4;
    private boolean localCacheSwitch = false;
    private boolean remoteCacheSwitch = true;
    private String idCachePath = "data";
    private String template = "type";
    private String templateString = "abc";
    private Integer templateInteger = 123;
    private Long templateLong = 12345L;
    private Double templateDouble = 1111111D;
    private Float templateFloat = 12345F;
    private BigDecimal templateBigDecimal = new BigDecimal(2020);
    private Boolean templateBoolean = true;
    private Character templateCharacter = 'o';
    private Byte templateByte = new Byte("1");
    private Date templateDate = new Date();
    private String consolePrefix = "/console";
    private List<String> mockClasses = new ArrayList<>();
    private Map<String,String> extConfig = new HashMap<String,String>();
    private Map<String,Object> intelligent = new HashMap<String,Object>();
    private Boolean intelligentOn = true;

    public MockConfig() {
    }

    public Boolean getMockOn() {
        return mockOn;
    }

    public void setMockOn(Boolean mockOn) {
        this.mockOn = mockOn;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNowUser() {
        return nowUser;
    }

    public void setNowUser(String nowUser) {
        this.nowUser = nowUser;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isLocalCacheSwitch() {
        return localCacheSwitch;
    }

    public void setLocalCacheSwitch(boolean localCacheSwitch) {
        this.localCacheSwitch = localCacheSwitch;
    }

    public boolean isRemoteCacheSwitch() {
        return remoteCacheSwitch;
    }

    public void setRemoteCacheSwitch(boolean remoteCacheSwitch) {
        this.remoteCacheSwitch = remoteCacheSwitch;
    }

    public String getIdCachePath() {
        return idCachePath;
    }

    public void setIdCachePath(String idCachePath) {
        this.idCachePath = idCachePath;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplateString() {
        return templateString;
    }

    public void setTemplateString(String templateString) {
        this.templateString = templateString;
    }

    public Integer getTemplateInteger() {
        return templateInteger;
    }

    public void setTemplateInteger(Integer templateInteger) {
        this.templateInteger = templateInteger;
    }

    public Long getTemplateLong() {
        return templateLong;
    }

    public void setTemplateLong(Long templateLong) {
        this.templateLong = templateLong;
    }

    public Double getTemplateDouble() {
        return templateDouble;
    }

    public void setTemplateDouble(Double templateDouble) {
        this.templateDouble = templateDouble;
    }

    public Float getTemplateFloat() {
        return templateFloat;
    }

    public void setTemplateFloat(Float templateFloat) {
        this.templateFloat = templateFloat;
    }

    public BigDecimal getTemplateBigDecimal() {
        return templateBigDecimal;
    }

    public void setTemplateBigDecimal(BigDecimal templateBigDecimal) {
        this.templateBigDecimal = templateBigDecimal;
    }

    public Boolean getTemplateBoolean() {
        return templateBoolean;
    }

    public void setTemplateBoolean(Boolean templateBoolean) {
        this.templateBoolean = templateBoolean;
    }

    public Character getTemplateCharacter() {
        return templateCharacter;
    }

    public void setTemplateCharacter(Character templateCharacter) {
        this.templateCharacter = templateCharacter;
    }

    public Byte getTemplateByte() {
        return templateByte;
    }

    public void setTemplateByte(Byte templateByte) {
        this.templateByte = templateByte;
    }

    public Date getTemplateDate() {
        return templateDate;
    }

    public void setTemplateDate(Date templateDate) {
        this.templateDate = templateDate;
    }

    public String getConsolePrefix() {
        return consolePrefix;
    }

    public void setConsolePrefix(String consolePrefix) {
        this.consolePrefix = consolePrefix;
    }

    public List<String> getMockClasses() {
        return mockClasses;
    }

    public void setMockClasses(List<String> mockClasses) {
        this.mockClasses = mockClasses;
    }

    public Boolean getWeEndOn() {
        return weEndOn;
    }

    public MockConfig setWeEndOn(Boolean weEndOn) {
        this.weEndOn = weEndOn;
        return this;
    }

    public Integer getDeep() {
        return deep;
    }

    public MockConfig setDeep(Integer deep) {
        this.deep = deep;
        return this;
    }

    public Map<String, String> getExtConfig() {
        return extConfig;
    }

    public MockConfig setExtConfig(Map<String, String> extConfig) {
        this.extConfig = extConfig;
        return this;
    }

    @Override
    public String toString() {
        return "MockConfig{" +
                "weEndOn=" + weEndOn +
                ", mockOn=" + mockOn +
                ", appName='" + appName + '\'' +
                ", nowUser='" + nowUser + '\'' +
                ", path='" + path + '\'' +
                ", deep=" + deep +
                ", localCacheSwitch=" + localCacheSwitch +
                ", remoteCacheSwitch=" + remoteCacheSwitch +
                ", idCachePath='" + idCachePath + '\'' +
                ", template='" + template + '\'' +
                ", templateString='" + templateString + '\'' +
                ", templateInteger=" + templateInteger +
                ", templateLong=" + templateLong +
                ", templateDouble=" + templateDouble +
                ", templateFloat=" + templateFloat +
                ", templateBigDecimal=" + templateBigDecimal +
                ", templateBoolean=" + templateBoolean +
                ", templateCharacter=" + templateCharacter +
                ", templateByte=" + templateByte +
                ", templateDate=" + templateDate +
                ", consolePrefix='" + consolePrefix + '\'' +
                ", mockClasses=" + mockClasses +
                ", extConfig=" + extConfig +
                ", intelligent=" + intelligent +
                '}';
    }

    public static String initNowUser() {
        return System.getProperty(VMEnum.USER_NAME.getKey(),VMEnum.USER_NAME.getValue());
    }

    public static String initAppName() {
        return System.getProperty(VMEnum.APP_NAME.getKey(),VMEnum.APP_NAME.getValue());
    }

    public Map<String, Object> getIntelligent() {
        return intelligent;
    }

    public MockConfig setIntelligent(Map<String, Object> intelligent) {
        this.intelligent = intelligent;
        return this;
    }

    public Boolean getIntelligentOn() {
        return intelligentOn;
    }

    public MockConfig setIntelligentOn(Boolean intelligentOn) {
        this.intelligentOn = intelligentOn;
        return this;
    }
}
