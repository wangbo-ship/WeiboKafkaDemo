package org.cug.geodt.weibo.sos.service.Imp;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.common.constants.RedisConstants;
import org.cug.geodt.weibo.sos.service.InterfaceCallStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.cug.geodt.weibo.sos.common.constants.RedisConstants.KEY_PREFIX;

/**
 * @FileName InterfaceStatisticsServiceImpl
 * @Author WJW
 * @Date 2023/10/6 15:50
 * @Description
 */
@Service
public class InterfaceCallStatisticsServiceImpl implements InterfaceCallStatisticsService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    static final Counter userCounter = Metrics.counter("user.counter.total", "services", "demo");

    /**
     * 使用mysql自增统计接口使用次数
     */
    @Override
    public void processCollectResult() {
        userCounter.increment(1D);
    }

    /**
     * 使用redis自增统计接口使用次数
     */
    public void cpuUsageCount() {
        stringRedisTemplate.opsForValue().increment(RedisConstants.cpuUsage);
    }

    @Override
    public Result getAllRequestCount() {
        //1、获取所有以KEY_PREFIX为前缀的key
        Set<String> keys = stringRedisTemplate.keys(KEY_PREFIX+"*");
        //获取对应的value并存入map中
        Map map = new HashMap<String,Integer>();
        keys.stream().forEach( key -> map.put(key,stringRedisTemplate.opsForValue().get(key)));
        return Result.ok(map);
    }

    @Override
    public Result getRequestCount(String path) {
        return Result.ok(stringRedisTemplate.opsForValue().get(KEY_PREFIX + path));
    }

    @Override
    public Result getCpuCores() {
        SystemInfo systemInfo = new SystemInfo();
        HashMap<String, Integer> cpuCoresInfo = new HashMap<>();
        cpuCoresInfo.put("totalCores", systemInfo.getHardware().getProcessor().getLogicalProcessorCount());
        cpuCoresInfo.put("availableCores",Runtime.getRuntime().availableProcessors());
        return Result.ok(cpuCoresInfo);
    }

}
