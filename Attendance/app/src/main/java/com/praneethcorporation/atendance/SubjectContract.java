package com.praneethcorporation.atendance;

import android.content.ContentResolver;
import android.provider.BaseColumns;

/**
 * Created by user on 7/16/2017.
 */

public class SubjectContract {


    public static class SubjectEntry implements BaseColumns {

        public static final String TABLE_NAME = "attendance_info";
        public static final String _ID = BaseColumns._ID;
        public static final String NAME_OF_THE_SUBJECT = "subjectName";
        public static final String NO_OF_PRESENTS = "presents";
        public static final String NO_OF_TOTAL_CLASSES = "classes";
        public static final String PERCENTAGE = "percentage";


    }
}
