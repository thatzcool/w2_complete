package com.multicampus.w2.dao;

import domain.TodoVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    public String getTime(){

        String now = null;

        try(Connection connection = ConnectionUtil.INSTANCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select now()");
            ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            resultSet.next();

            now = resultSet.getString(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }



    public String getTime2() throws  Exception{

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("select now()");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

           resultSet.next();

         String  now = resultSet.getString(1);

        return now;
    }

    public void insert(TodoVO vo) throws  Exception{
        //쿼리문 작성  PreparedStatement  tbl_todo테이블
       String query ="INSERT INTO tbl_todo (title,dueDate,finished) values(?,?,?)";

       @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
       @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);

       preparedStatement.setString(1,vo.getTitle());
       preparedStatement.setDate(2, Date.valueOf(vo.getDueDate()));
       preparedStatement.setBoolean(3,vo.isFinished());

       preparedStatement.executeUpdate();

    }
    //TodoDAO의 목록 기능 구현하기

    public List<TodoVO> selectAll() throws Exception{

        String query = "SELECT * FROM tbl_todo";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<TodoVO> list = new ArrayList<TodoVO>();
        while (resultSet.next()){
            TodoVO vo = TodoVO.builder()   //빌더 패턴
                    .tno(resultSet.getLong("tno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished")).build();

            list.add(vo);
        }


    return  list;
    }

 // tno을 받아서 데이터를 가져오는 기능 구현

    public TodoVO selectOne(Long tno) throws Exception{
        String query = "SELECT * FROM tbl_todo WHERE tno =?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1,tno);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        TodoVO vo = TodoVO.builder()   //빌더 패턴
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished")).build();
        //빌더 패턴을 이용하여 구현

        return  vo;
    }


    //수정 기능 구현 : 특정한 번호(tno) 를 가진 데이터의 제목, 만료일(dueDate), 완료 여부를 update 하도록 구성
    public void updateOne(TodoVO todoVO) throws Exception{
     String query = "update tbl_todo set title=?, dueDate=?,finished = ? where tno=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,todoVO.getTitle());
        preparedStatement.setDate(2,Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3,todoVO.isFinished());
        preparedStatement.setLong(4,todoVO.getTno());
        preparedStatement.executeUpdate();

    }

    // 삭제 기능 구현
    public void deleteOne(Long tno) throws Exception{
      String query ="delete from tbl_todo where tno = ?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1,tno);
        preparedStatement.executeUpdate();

    }


}