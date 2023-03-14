package com.multicampus.w2.service;

import com.multicampus.w2.dao.MemberDAO;
import com.multicampus.w2.dto.MemberDTO;
import domain.MemberVO;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum MemberService {
    INSTANCE;

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService(){
        dao = new MemberDAO();
        modelMapper = new ModelMapper();

    }

    //service 안에서 login()
    public MemberDTO login(String mid,String mpw )throws Exception{
        MemberVO vo = dao.getWithPassword(mid,mpw);
        MemberDTO memberDTO = modelMapper.map(vo,MemberDTO.class);

        return memberDTO;
    }
    //uuid update 기능 서비스객체에 등록
    public void updateUuid(String mid, String uuid) throws  Exception{
        dao.updateUuid(mid,uuid);
    }

    public MemberDTO getByUUID(String uuid) throws  Exception{

           MemberVO vo = dao.selectUUID(uuid);
           MemberDTO memberDTO = modelMapper.map(vo,MemberDTO.class);
           return  memberDTO;


    }
}
