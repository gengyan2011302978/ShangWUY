package com.phjt.shangxueyuan.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.phjt.shangxueyuan.bean.model.File;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FILE".
*/
public class FileDao extends AbstractDao<File, Long> {

    public static final String TABLENAME = "FILE";

    /**
     * Properties of entity File.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property FileName = new Property(1, String.class, "fileName", false, "FILE_NAME");
        public final static Property Type = new Property(2, int.class, "type", false, "TYPE");
        public final static Property Url = new Property(3, String.class, "url", false, "URL");
        public final static Property FileType = new Property(4, String.class, "fileType", false, "FILE_TYPE");
        public final static Property FileLevel = new Property(5, String.class, "fileLevel", false, "FILE_LEVEL");
        public final static Property FileLevelUrl = new Property(6, String.class, "fileLevelUrl", false, "FILE_LEVEL_URL");
        public final static Property FileLevelDesc = new Property(7, String.class, "fileLevelDesc", false, "FILE_LEVEL_DESC");
        public final static Property Path = new Property(8, String.class, "path", false, "PATH");
        public final static Property Size = new Property(9, Long.class, "size", false, "SIZE");
        public final static Property SizeStr = new Property(10, String.class, "sizeStr", false, "SIZE_STR");
        public final static Property Status = new Property(11, int.class, "status", false, "STATUS");
        public final static Property Progress = new Property(12, int.class, "progress", false, "PROGRESS");
        public final static Property CourseId = new Property(13, int.class, "courseId", false, "COURSE_ID");
        public final static Property Speed = new Property(14, String.class, "speed", false, "SPEED");
        public final static Property CreateTime = new Property(15, java.util.Date.class, "createTime", false, "CREATE_TIME");
        public final static Property Checked = new Property(16, boolean.class, "checked", false, "CHECKED");
        public final static Property Show = new Property(17, boolean.class, "show", false, "SHOW");
        public final static Property Seq = new Property(18, int.class, "seq", false, "SEQ");
        public final static Property Videotype = new Property(19, int.class, "videotype", false, "VIDEOTYPE");
    }


    public FileDao(DaoConfig config) {
        super(config);
    }
    
    public FileDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FILE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"FILE_NAME\" TEXT," + // 1: fileName
                "\"TYPE\" INTEGER NOT NULL ," + // 2: type
                "\"URL\" TEXT," + // 3: url
                "\"FILE_TYPE\" TEXT," + // 4: fileType
                "\"FILE_LEVEL\" TEXT," + // 5: fileLevel
                "\"FILE_LEVEL_URL\" TEXT," + // 6: fileLevelUrl
                "\"FILE_LEVEL_DESC\" TEXT," + // 7: fileLevelDesc
                "\"PATH\" TEXT," + // 8: path
                "\"SIZE\" INTEGER," + // 9: size
                "\"SIZE_STR\" TEXT," + // 10: sizeStr
                "\"STATUS\" INTEGER NOT NULL ," + // 11: status
                "\"PROGRESS\" INTEGER NOT NULL ," + // 12: progress
                "\"COURSE_ID\" INTEGER NOT NULL ," + // 13: courseId
                "\"SPEED\" TEXT," + // 14: speed
                "\"CREATE_TIME\" INTEGER," + // 15: createTime
                "\"CHECKED\" INTEGER NOT NULL ," + // 16: checked
                "\"SHOW\" INTEGER NOT NULL ," + // 17: show
                "\"SEQ\" INTEGER NOT NULL ," + // 18: seq
                "\"VIDEOTYPE\" INTEGER NOT NULL );"); // 19: videotype
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FILE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, File entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(2, fileName);
        }
        stmt.bindLong(3, entity.getType());
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }
 
        String fileType = entity.getFileType();
        if (fileType != null) {
            stmt.bindString(5, fileType);
        }
 
        String fileLevel = entity.getFileLevel();
        if (fileLevel != null) {
            stmt.bindString(6, fileLevel);
        }
 
        String fileLevelUrl = entity.getFileLevelUrl();
        if (fileLevelUrl != null) {
            stmt.bindString(7, fileLevelUrl);
        }
 
        String fileLevelDesc = entity.getFileLevelDesc();
        if (fileLevelDesc != null) {
            stmt.bindString(8, fileLevelDesc);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(9, path);
        }
 
        Long size = entity.getSize();
        if (size != null) {
            stmt.bindLong(10, size);
        }
 
        String sizeStr = entity.getSizeStr();
        if (sizeStr != null) {
            stmt.bindString(11, sizeStr);
        }
        stmt.bindLong(12, entity.getStatus());
        stmt.bindLong(13, entity.getProgress());
        stmt.bindLong(14, entity.getCourseId());
 
        String speed = entity.getSpeed();
        if (speed != null) {
            stmt.bindString(15, speed);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(16, createTime.getTime());
        }
        stmt.bindLong(17, entity.getChecked() ? 1L: 0L);
        stmt.bindLong(18, entity.getShow() ? 1L: 0L);
        stmt.bindLong(19, entity.getSeq());
        stmt.bindLong(20, entity.getVideotype());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, File entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(2, fileName);
        }
        stmt.bindLong(3, entity.getType());
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }
 
        String fileType = entity.getFileType();
        if (fileType != null) {
            stmt.bindString(5, fileType);
        }
 
        String fileLevel = entity.getFileLevel();
        if (fileLevel != null) {
            stmt.bindString(6, fileLevel);
        }
 
        String fileLevelUrl = entity.getFileLevelUrl();
        if (fileLevelUrl != null) {
            stmt.bindString(7, fileLevelUrl);
        }
 
        String fileLevelDesc = entity.getFileLevelDesc();
        if (fileLevelDesc != null) {
            stmt.bindString(8, fileLevelDesc);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(9, path);
        }
 
        Long size = entity.getSize();
        if (size != null) {
            stmt.bindLong(10, size);
        }
 
        String sizeStr = entity.getSizeStr();
        if (sizeStr != null) {
            stmt.bindString(11, sizeStr);
        }
        stmt.bindLong(12, entity.getStatus());
        stmt.bindLong(13, entity.getProgress());
        stmt.bindLong(14, entity.getCourseId());
 
        String speed = entity.getSpeed();
        if (speed != null) {
            stmt.bindString(15, speed);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(16, createTime.getTime());
        }
        stmt.bindLong(17, entity.getChecked() ? 1L: 0L);
        stmt.bindLong(18, entity.getShow() ? 1L: 0L);
        stmt.bindLong(19, entity.getSeq());
        stmt.bindLong(20, entity.getVideotype());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public File readEntity(Cursor cursor, int offset) {
        File entity = new File( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // fileName
            cursor.getInt(offset + 2), // type
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // url
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // fileType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // fileLevel
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // fileLevelUrl
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // fileLevelDesc
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // path
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // size
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // sizeStr
            cursor.getInt(offset + 11), // status
            cursor.getInt(offset + 12), // progress
            cursor.getInt(offset + 13), // courseId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // speed
            cursor.isNull(offset + 15) ? null : new java.util.Date(cursor.getLong(offset + 15)), // createTime
            cursor.getShort(offset + 16) != 0, // checked
            cursor.getShort(offset + 17) != 0, // show
            cursor.getInt(offset + 18), // seq
            cursor.getInt(offset + 19) // videotype
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, File entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFileName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setType(cursor.getInt(offset + 2));
        entity.setUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFileType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFileLevel(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFileLevelUrl(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFileLevelDesc(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setPath(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSize(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setSizeStr(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setStatus(cursor.getInt(offset + 11));
        entity.setProgress(cursor.getInt(offset + 12));
        entity.setCourseId(cursor.getInt(offset + 13));
        entity.setSpeed(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCreateTime(cursor.isNull(offset + 15) ? null : new java.util.Date(cursor.getLong(offset + 15)));
        entity.setChecked(cursor.getShort(offset + 16) != 0);
        entity.setShow(cursor.getShort(offset + 17) != 0);
        entity.setSeq(cursor.getInt(offset + 18));
        entity.setVideotype(cursor.getInt(offset + 19));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(File entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(File entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(File entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}