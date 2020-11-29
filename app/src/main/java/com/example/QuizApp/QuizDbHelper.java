package com.example.QuizApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.QuizApp.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="mydatabase.db";
    private static final int DATABASE_VERSION=1;
    private SQLiteDatabase db;



    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;

        final String SQL_CREATE_QUESTIONS_TABLE="CREATE TABLE "+
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT , "+
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR +" INTEGER"+
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    private void fillQuestionsTable() {
        Question question1=new Question("A is correct","A", "B","C",1);
        addQuestion(question1);

        Question question2=new Question("B is correct","A", "B","C",2);
        addQuestion(question2);

        Question question3=new Question("C is correct","A", "B","C",3);
        addQuestion(question3);

        Question question4=new Question("A is correct AGAIN","A", "B","C",1);
        addQuestion(question4);

        Question question5=new Question("B is correct AGAIN","A", "B","C",2);
        addQuestion(question5);


    }

    private void addQuestion(Question question)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(QuestionsTable.COLUMN_QUESTION,question.getQuestion());
        contentValues.put(QuestionsTable.COLUMN_OPTION1,question.getOption1());
        contentValues.put(QuestionsTable.COLUMN_OPTION2,question.getOption2());
        contentValues.put(QuestionsTable.COLUMN_OPTION3,question.getOption3());
        contentValues.put(QuestionsTable.COLUMN_ANSWER_NR,question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME,null,contentValues);


    }
    public List<Question> getAllQuestions()
    {
        List<Question> questionList=new ArrayList<>();
        db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ QuestionsTable.TABLE_NAME,null);
        if(c.moveToFirst())
        {
            do {
                Question question=new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);


            }while (c.moveToNext());
        }
        c.close();
        return questionList;

    }

}
