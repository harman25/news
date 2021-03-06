/* ############################################################################
* Copyright 2013 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: AL Deal-Maker
* Module: Common
* Source: DealmakerDbAdapter.java
* Author: HP
* Organization: HP BAS India
* Revision: 0.1
* Date: 08-22-2013
* Contents:
*-----------------------------------------------------------------------------
* Revision History:
*     who                                  when                                    what
*  	Roopa Shree							08-22-2013								Initial functionality
* #############################################################################
*/
package com.app.news.database;


import java.io.InputStream;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.news.R;

public class CustomDbAdapter implements DatabaseManager {
	private Context mCtx;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private static CustomDbAdapter instance;
	
	public static final String DATABASE_NAME = "News.db";	
	public static final int DATABASE_VERSION = 1;

	/**
	 * Constructor - takes context to allow the database to be opened/created
	 * 
	 * @param ctx
	 *            - the Context within which to work. Application context
	 *            preferred.
	 */
	private CustomDbAdapter(Context ctx) {
		mCtx = ctx;

	}

	/**
	 * 
	 * @param context
	 *            . Should be Application Context.
	 * @return
	 */

	public static synchronized CustomDbAdapter getInstance(Context context) {
		if (instance == null) {
			instance = new CustomDbAdapter(context);
			Log.i("CREATING NEW INSTANCE FOR MANAGER","");
			try {
				instance.open();
				Log.i("OPENING DATABASE...", "");
			} catch (SQLException e) {
				//DLog.wtf(TAG, "!Err when opening database", e);
				throw new RuntimeException("!Err when opening database", e);
			}

		}
		return instance;
	}

	/**
	 * close database when there is no reference to InstO2reDbAdapter object
	 */
	@Override
	protected void finalize() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

	/**
	 * Open the o2 database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         Initialisation call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	private synchronized CustomDbAdapter open() throws SQLException {
		if (mDbHelper == null) {
			mDbHelper = new DatabaseHelper(mCtx);
		}
		if (mDb == null) {
			Log.i("GETTING WRITABLE CONNECTION...", "");
			//mDb = mDbHelper.getWritableDatabase();
			mDb = mDbHelper.getWritableDatabase();
			
		}
		return this;

	}
	


		private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				readAndRunSqlScript(db, R.raw.db_script);
				Log.i("CREATING NEW DATABASE...", "");
			} catch (Exception e) {
				throw new RuntimeException("!Err when executing sql file", e);
			}
		}

		/**
		 * Read and execute sql statements strored in file
		 * 
		 * @param db
		 *            - instance of database
		 * @param id
		 *            - sql file id (should be placed in raw directory)
		 * @throws Exception
		 *             - when sql file is invalid
		 */

		private void readAndRunSqlScript(final SQLiteDatabase db, int id)
				throws Exception {
			final SqlScriptReader reader = new SqlScriptReader();
			final InputStream is = mCtx.getResources().openRawResource(id);
			final List<String> sqls = reader.readSqlStatment(is);
			for (String sql : sqls) {
				//DLog.i("db", sql);
				db.execSQL(sql);
			}
			is.close();
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				Log.i("DATABASE UPGRADED", "");
				readAndRunSqlScript(db, R.raw.db_script);
			} catch (Exception e) {
				throw new RuntimeException("!Err when executing sql file", e);
			}

		}

	}


	@Override
	public void beginTransaction() {
		mDb.beginTransaction();
	}

	@Override
	public void setTransactionSuccessful() {
		mDb.setTransactionSuccessful();
	}

	@Override
	public void endTransaction() {
		mDb.endTransaction();
	}

	@Override
	public void execSQL(String sql) {
		mDb.execSQL(sql);
	}

	@Override
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		return mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	
	@Override
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
		return mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy,limit);
	}
	
	@Override
	public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
		return mDb.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy,limit);
	}
	
	@Override
	public Cursor rawQuery(String sql, String[] selectionArgs) {
		return mDb.rawQuery(sql, selectionArgs);
	}

	@Override
	public long insert(String table, String nullColumnHack, ContentValues values) {
		return mDb.insert(table, nullColumnHack, values);
	}
	

	@Override
	public long insertWithOnConflictReplace(String table,
			ContentValues initialValues) {

		return mDb.insertWithOnConflict(table, null, initialValues,
				SQLiteDatabase.CONFLICT_REPLACE);

	}
	
	@Override
	public long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
		return mDb.insertWithOnConflict(table, nullColumnHack, initialValues, conflictAlgorithm);
	}
	

	@Override
	public int delete(String table, String whereClause, String[] whereArgs) {
		return mDb.delete(table, whereClause, whereArgs);

	}

	@Override
	public long updateWithOnConflictReplace(String table, ContentValues values,
			String whereClause, String[] whereArgs) {
		return mDb.updateWithOnConflict(table, values, whereClause, whereArgs,
				SQLiteDatabase.CONFLICT_REPLACE);
	}

	@Override
	public long update(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		return mDb.update(table, values, whereClause, whereArgs);
	}

	
}
