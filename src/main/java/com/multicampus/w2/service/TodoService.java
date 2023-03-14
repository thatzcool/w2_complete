package com.multicampus.w2.service;

import com.multicampus.w2.dao.TodoDAO;
import com.multicampus.w2.dto.TodoDTO;
import com.multicampus.w2.util.MapperUtil;
import domain.TodoVO;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public enum TodoService {
    INSTANCE;

    private TodoDAO dao;
    private ModelMapper modelMapper;

    TodoService(){
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();

    }

    public void register(TodoDTO todoDTO) throws Exception{
        TodoVO todoVO = modelMapper.map(todoDTO,TodoVO.class);
       //System.out.println("todoVO:" + todoVO);
        log.info(todoVO);
        dao.insert(todoVO);

    }

    public List<TodoDTO> listAll() throws Exception{
       List<TodoVO> voList = dao.selectAll();

       log.info("----voList---------------");
       log.info(voList);

       List<TodoDTO> dtoList = voList.stream()
               .map(vo -> modelMapper.map(vo,TodoDTO.class))
               .collect(Collectors.toList());

        return dtoList;
    }
    public TodoDTO get(Long tno) throws Exception{

        log.info("tno: "+ tno );
        TodoVO todoVO = dao.selectOne(tno);
        TodoDTO todoDTO = modelMapper.map(todoVO,TodoDTO.class);
        return  todoDTO;
    }

    // remove()   :tno를 이용하여 글 하나 삭제
    public void remove(Long tno) throws Exception{
        log.info("tno :" + tno);
        dao.deleteOne(tno);

    }

    // modify()    : TodoDTO 타입을 이용
   public void modify(TodoDTO todoDTO) throws Exception{
        log.info("todoDTO : " + todoDTO);
        TodoVO todoVO = modelMapper.map(todoDTO,TodoVO.class);
        dao.updateOne(todoVO);
   }
}
