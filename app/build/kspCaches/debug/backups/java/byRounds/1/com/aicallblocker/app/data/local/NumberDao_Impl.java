package com.aicallblocker.app.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NumberDao_Impl implements NumberDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NumberEntity> __insertionAdapterOfNumberEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public NumberDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNumberEntity = new EntityInsertionAdapter<NumberEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `numbers` (`phoneNumber`,`listType`,`label`,`spamScore`,`addedAt`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NumberEntity entity) {
        statement.bindString(1, entity.getPhoneNumber());
        statement.bindString(2, __ListType_enumToString(entity.getListType()));
        if (entity.getLabel() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLabel());
        }
        statement.bindDouble(4, entity.getSpamScore());
        statement.bindLong(5, entity.getAddedAt());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM numbers WHERE phoneNumber = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final NumberEntity entity, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNumberEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final String number, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, number);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object findByNumber(final String number,
      final Continuation<? super NumberEntity> $completion) {
    final String _sql = "SELECT * FROM numbers WHERE phoneNumber = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, number);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NumberEntity>() {
      @Override
      @Nullable
      public NumberEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfListType = CursorUtil.getColumnIndexOrThrow(_cursor, "listType");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfSpamScore = CursorUtil.getColumnIndexOrThrow(_cursor, "spamScore");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final NumberEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final ListType _tmpListType;
            _tmpListType = __ListType_stringToEnum(_cursor.getString(_cursorIndexOfListType));
            final String _tmpLabel;
            if (_cursor.isNull(_cursorIndexOfLabel)) {
              _tmpLabel = null;
            } else {
              _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            }
            final float _tmpSpamScore;
            _tmpSpamScore = _cursor.getFloat(_cursorIndexOfSpamScore);
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _result = new NumberEntity(_tmpPhoneNumber,_tmpListType,_tmpLabel,_tmpSpamScore,_tmpAddedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllByType(final ListType type,
      final Continuation<? super List<NumberEntity>> $completion) {
    final String _sql = "SELECT * FROM numbers WHERE listType = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __ListType_enumToString(type));
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<NumberEntity>>() {
      @Override
      @NonNull
      public List<NumberEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfListType = CursorUtil.getColumnIndexOrThrow(_cursor, "listType");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfSpamScore = CursorUtil.getColumnIndexOrThrow(_cursor, "spamScore");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<NumberEntity> _result = new ArrayList<NumberEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NumberEntity _item;
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final ListType _tmpListType;
            _tmpListType = __ListType_stringToEnum(_cursor.getString(_cursorIndexOfListType));
            final String _tmpLabel;
            if (_cursor.isNull(_cursorIndexOfLabel)) {
              _tmpLabel = null;
            } else {
              _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            }
            final float _tmpSpamScore;
            _tmpSpamScore = _cursor.getFloat(_cursorIndexOfSpamScore);
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _item = new NumberEntity(_tmpPhoneNumber,_tmpListType,_tmpLabel,_tmpSpamScore,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAll(final Continuation<? super List<NumberEntity>> $completion) {
    final String _sql = "SELECT * FROM numbers ORDER BY addedAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<NumberEntity>>() {
      @Override
      @NonNull
      public List<NumberEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfListType = CursorUtil.getColumnIndexOrThrow(_cursor, "listType");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfSpamScore = CursorUtil.getColumnIndexOrThrow(_cursor, "spamScore");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<NumberEntity> _result = new ArrayList<NumberEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NumberEntity _item;
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final ListType _tmpListType;
            _tmpListType = __ListType_stringToEnum(_cursor.getString(_cursorIndexOfListType));
            final String _tmpLabel;
            if (_cursor.isNull(_cursorIndexOfLabel)) {
              _tmpLabel = null;
            } else {
              _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            }
            final float _tmpSpamScore;
            _tmpSpamScore = _cursor.getFloat(_cursorIndexOfSpamScore);
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _item = new NumberEntity(_tmpPhoneNumber,_tmpListType,_tmpLabel,_tmpSpamScore,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __ListType_enumToString(@NonNull final ListType _value) {
    switch (_value) {
      case BLACKLIST: return "BLACKLIST";
      case WHITELIST: return "WHITELIST";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private ListType __ListType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "BLACKLIST": return ListType.BLACKLIST;
      case "WHITELIST": return ListType.WHITELIST;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
