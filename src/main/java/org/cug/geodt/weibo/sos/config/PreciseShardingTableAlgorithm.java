package org.cug.geodt.weibo.sos.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Properties;

/**
 * Author WJW
 * Date 2023/3/31 14:24
 */
@Slf4j

@Configuration
public class PreciseShardingTableAlgorithm implements StandardShardingAlgorithm<String> {
    // 分表数量
    @Value("${tableSize}")
    private  int tableSize = 10;


//    public void setTableSize(int size) {
//        tableSize = size;
//    }

    /**
     * @description: 分表策略：按sensorId 格式devicenumber number部分进行取模运算
     * @param tableNames
     * @param preciseShardingValue
     * @return
     */
    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<String> preciseShardingValue) {

//        log.info("Table PreciseShardingAlgorithm tableNames:{} ,preciseShardingValue: {}.",
//                JSON.toJSONString(tableNames), JSON.toJSONString(preciseShardingValue));
        // 按表数量取模
        // 截取用户编号倒数二三位数字，（如1234的倒数二三位为23）
//        String num = StringUtils.substring(preciseShardingValue.getValue(), 6, preciseShardingValue.getValue().length());
        String[] parts = preciseShardingValue.getValue().split(":");
        String num = parts[parts.length-1].trim();
        int mod = Integer.parseInt(num) % tableSize;
        for (String tableName : tableNames) {
            // 分表的规则
            if (tableName.endsWith(String.valueOf(mod))) {
                return tableName;
            }
        }
        throw new UnsupportedOperationException();

    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
        return null;
    }


    @Override
    public String getType() {
        return null;
    }


    @Override
    public void init(Properties properties) {

    }

    @Override
    public Properties getProps() {
        return null;
    }
}
