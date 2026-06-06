package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.mapper.ProcedureMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.Procedures;
import org.cug.geodt.weibo.sos.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @FileName insertProcedure
 * @Author WJW
 * @Date 2023/7/28 9:07
 * @Description 用于封装Procedure相关的操作
 */
public class ProcedureServiceImpl implements ProcedureService {

    @Value("${fieldName.procedure}")
    String procedurePrefix;

    @Autowired
    Procedures procedures;

    @Autowired
    ProcedureMapper procedureMapper;

    @Override
    public void insertProcedure(String key) {
//        procedures.setIdentifier(procedurePrefix+);
//        procedures.setName();
//        procedures.setDescription();
//        procedures.setProcedureDescriptionFormat();
//        procedures.
    }
}
