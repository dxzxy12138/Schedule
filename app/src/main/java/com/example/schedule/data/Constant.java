package com.example.schedule.data;

/**
 * 常量
 * <p/>
 */
public class Constant {

    /**
     * 新建还是编辑
     */
    public final static String NEW_OR_EDIT = "new_or_edit";
    public final static boolean NEW = true;
    public final static boolean EDIT = false;

    /**
     * 排序方式
     */
    public final static int SORT_BY_DEFAULT = 0;
    public final static int SORT_BY_DATE = 1;
    public final static int SORT_BY_SIGNIFICANCE = 2;

    /**
     * 抽屉菜单的标识
     */
    public final static String SHARED_SETTING = "default_label_id";
    public final static String DEFAULT_LABEL_ID_SETTING = "default_label_id";
    public final static int DEFAULT_LABEL_ID = 0;//默认集ID
    public final static int LABEL_ID_1 = 1;
    public final static int LABEL_ID_2 = 2;
    public final static int LABEL_ID_HAVE_DONE = 3;

    /**
     * Date_Format格式
     */
    public final static String DATE_FORMAT = "yyyy/MM/dd HH:mm";

    /**
     * 数据库的常量
     */
    public final static String TODO_LIST = "todo_list";
    public final static String ID = "id";
    public final static String IS_DONE = "is_done";
    public final static String TITLE = "title";
    public final static String PS = "ps";
    public final static String DATE_LINE = "date_line";
    public final static String LABEL_ID = "label_id";
    public static final String CREATE_TODOLIST = "create table todo_list (" +
            "id integer primary key autoincrement, " +
            "is_done integer," +
            "title text, " +
            "ps text, " +
            "date_line text," +
            "label_id integer)";
}

