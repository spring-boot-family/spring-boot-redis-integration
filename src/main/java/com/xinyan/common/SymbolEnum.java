package com.xinyan.common;

import lombok.Getter;

import java.io.File;

/**
 * 符号枚举类
 *
 * @author weimin_ruan
 * @date 2019/6/20
 */
@Getter
public enum SymbolEnum {
    /** #号 **/
    WELL("#", "#号"),
    /** 空字符串 **/
    BLANK("", "空字符串"),
    /** 空格 **/
    SPACE(" ", "空格"),
    /** 短横线 **/
    LINE("-", "短横线"),
    /** 中括号加短横线 **/
    BRACKET_AND_LINE("[-]", "中括号加短横线"),
    /** 竖线 **/
    VERTICAL_LINE("|", "竖线"),
    /** \\|-斜杠+竖线 **/
    COLON_CHECK("\\|", "斜杠+竖线"),
    /** [|]-中括号加竖线 **/
    BRACKET_AND_COLON("[|]", "中括号+竖线"),
    /** *号 **/
    STAR("*", "*号"),
    /** 点 **/
    POINT(".", "点"),
    /** 左中括号 **/
    LEFT_MID_BRACKET("[", "左中括号"),
    /** 右中括号 **/
    RIGHT_MID_BRACKET("]", "右中括号"),
    /** 冒号 **/
    COLON(":", "冒号"),
    /** 逗号 **/
    COMMA(",", "逗号"),
    /** 句号 **/
    STOP_CODE("。", "句号"),
    /** 下划线 **/
    UNDER_LINE("_", "下划线"),
    /** 加号 **/
    ADD("+", "加号"),
    /** 双竖线 **/
    DOUBLE_COLON("||", "双竖线"),
    /** 斜杠 **/
    SLASH("/", "斜杠"),
    /** 反斜杠 **/
    UNTI_SLASH("\\", "反斜杠"),
    /** 左括号 **/
    LEFT_BRACKET("(", "左括号"),
    /** 右括号 **/
    RIGHT_BRACKET(")", "右括号"),
    /** 等于号 **/
    EQUALS("=", "等于号"),
    /** 不等于号 **/
    NOT_EQUALS("!=", "不等于号"),
    /** 分号 **/
    SEMICOLON(";", "分号"),
    /** 左大括号 **/
    LEFT_BIG_BRACKET("{", "左大括号"),
    /** 右大括号 **/
    RIGHT_BIG_BRACKET("}", "右大括号"),
    /** &号 **/
    AMPERSAND("&", "&号"),
    /** 百分号 **/
    PERCENTAGE("%", "百分号"),
    /** @号 **/
    EMAIL_CODE("@", "@号"),
    /** ￥符号 **/
    RMB_CODE("￥", "￥符号"),
    /** $符号 **/
    US_CODE("$", "$符号"),
    /** 感叹号 **/
    EXCLAMATION("!", "感叹号"),
    /** 双引号 **/
    DUBBO_QUOTA_CODE("\"", "双引号"),
    /** 单引号 **/
    QUOTA_CODE("\'", "单引号"),
    /** 单引号 **/
    SIGLE_REFER("'", "单引号"),
    /** 问号 **/
    QUESTION_CODE("?", "问号"),
    /** 换行 **/
    WRAP("\n", "换行"),
    /** 回车 **/
    ENTER("\r", "回车"),
    /** 交易渠道换行前距 (18次空格键)**/
    BEFORE_STANCE("                  ", "交易渠道换行前距"),
    /** 文件分割符 **/
    FILE_SEPARATOR(File.separator, "文件分割符 "),
    ;

    SymbolEnum(String symbol, String desc) {
        this.symbol = symbol;
        this.desc = desc;
    }

    /** 符号 **/
    private String symbol;
    /** 描述 **/
    private String desc;

}
