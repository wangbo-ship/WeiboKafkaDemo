package org.cug.geodt.weibo.sos.service.Imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cug.geodt.weibo.sos.mapper.LogMapper;
import org.cug.geodt.weibo.sos.pojo.GeodtServiceLog;
import org.cug.geodt.weibo.sos.service.LogService;
import org.springframework.stereotype.Service;

/**
 * @FileName LogServiceImpl
 * @Author WJW
 * @Date 2023/10/10 11:16
 * @Description
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, GeodtServiceLog> implements LogService {

}
