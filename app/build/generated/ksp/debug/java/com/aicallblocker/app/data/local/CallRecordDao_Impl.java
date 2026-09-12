package com.aicallblocker.app.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CallRecordDao_Impl implements CallRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CallRecordEntity> __insertionAdapterOfCallRecordEntity;

  public CallRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCallRecordEntity = new EntityInsertionAdapter<CallRecordEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `call_records` (`id`,`phoneNumber`,`reason`,`spamScore`,`blockedAt`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CallRecordEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getPhoneNumber());
        statement.bindString(3, entity.getReason());
        statement.bindDouble(4, entity.getSpamScore());
        statement.bindLong(5, entity.getBlockedAt());
      }
    };
  }

  @Override
  public Object insert(final CallRecordEntity record,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCallRecordEntity.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CallRecordEntity>> observeRecent(final int limit) {
    final String _sql = "SELECT * FROM call_records ORDER BY blockedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_records"}, new Callable<List<CallRecordEntity>>() {
      @Override
      @NonNull
      public List<CallRecordEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfSpamScore = CursorUtil.getColumnIndexOrThrow(_cursor, "spamScore");
          final int _cursorIndexOfBlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "blockedAt");
          final List<CallRecordEntity> _result = new ArrayList<CallRecordEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallRecordEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpReason;
            _tmpReason = _cursor.getString(_cursorIndexOfReason);
            final float _tmpSpamScore;
            _tmpSpamScore = _cursor.getFloat(_cursorIndexOfSpamScore);
            final long _tmpBlockedAt;
            _tmpBlockedAt = _cursor.getLong(_cursorIndexOfBlockedAt);
            _item = new CallRecordEntity(_tmpId,_tmpPhoneNumber,_tmpReason,_tmpSpamScore,_tmpBlockedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAll(final Continuation<? super List<CallRecordEntity>> $completion) {
    final String _sql = "SELECT * FROM call_records ORDER BY blockedAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CallRecordEntity>>() {
      @Override
      @NonNull
      public List<CallRecordEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfSpamScore = CursorUtil.getColumnIndexOrThrow(_cursor, "spamScore");
          final int _cursorIndexOfBlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "blockedAt");
          final List<CallRecordEntity> _result = new ArrayList<CallRecordEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallRecordEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpReason;
            _tmpReason = _cursor.getString(_cursorIndexOfReason);
            final float _tmpSpamScore;
            _tmpSpamScore = _cursor.getFloat(_cursorIndexOfSpamScore);
            final long _tmpBlockedAt;
            _tmpBlockedAt = _cursor.getLong(_cursorIndexOfBlockedAt);
            _item = new CallRecordEntity(_tmpId,_tmpPhoneNumber,_tmpReason,_tmpSpamScore,_tmpBlockedAt);
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
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM call_records";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object countByReason(final Continuation<? super List<ReasonCount>> $completion) {
    final String _sql = "SELECT reason, COUNT(*) AS total FROM call_records GROUP BY reason ORDER BY total DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ReasonCount>>() {
      @Override
      @NonNull
      public List<ReasonCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReason = 0;
          final int _cursorIndexOfTotal = 1;
          final List<ReasonCount> _result = new ArrayList<ReasonCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReasonCount _item;
            final String _tmpReason;
            _tmpReason = _cursor.getString(_cursorIndexOfReason);
            final long _tmpTotal;
            _tmpTotal = _cursor.getLong(_cursorIndexOfTotal);
            _item = new ReasonCount(_tmpReason,_tmpTotal);
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
}
