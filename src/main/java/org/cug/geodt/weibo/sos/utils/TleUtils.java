package org.cug.geodt.weibo.sos.utils;

import com.google.gson.Gson;
import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.data.DataContext;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.frames.FramesFactory;
import org.orekit.propagation.Propagator;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.utils.PVCoordinates;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * @FileName TleUtils
 * @Author WJW
 * @Date 2023/9/5 21:14
 * @Description
 */
public class TleUtils {

    public static void main(String [] args) {
        try {
            // 加载Orekit数据
            File orekitData = new File("D:\\Desktop\\GeoDT\\geodt-service_\\geodt-service\\geodt-service\\src\\main\\resources\\orekit-data");
            DataProvidersManager manager = DataContext.getDefault().getDataProvidersManager();
            manager.addProvider(new DirectoryCrawler(orekitData));


            // TLE数据
            String line1 = "1 40697U 15028A   23124.03269176  .00000204  00000-0  94618-4 0  9996";
            String line2 = "2 40697  98.5704 199.1700 0001077  93.9615 266.1679 14.30818542410699";

            // 解析TLE数据
            TLE tle = new TLE(line1, line2);
            Propagator propagator = TLEPropagator.selectExtrapolator(tle);

            // CZML文件
            Map<String, Object> czml = new HashMap<>();
            List<Map<String, Object>> packets = new ArrayList<>();

            // 设置卫星属性
            Map<String, Object> satellite = new HashMap<>();
            satellite.put("id", "Satellite1");
            satellite.put("name", "Satellite 1");

            // 计算轨道位置
            AbsoluteDate startDate = tle.getDate();
            AbsoluteDate endDate = startDate.shiftedBy(24 * 60 * 60); // 结束日期为起始日期后一天
            AbsoluteDate currentDate = startDate;

            while (currentDate.compareTo(endDate) <= 0) {
                PVCoordinates pvCoordinates = propagator.getPVCoordinates(currentDate, FramesFactory.getICRF());
                Vector3D position = pvCoordinates.getPosition();

                // 创建CZML数据包
                Map<String, Object> packet = new HashMap<>();
                packet.put("id", UUID.randomUUID().toString());
                packet.put("name", "Position");
                packet.put("availability", currentDate.toString() + "/" + currentDate.toString());
                packet.put("position", Arrays.asList(position.getX(), position.getY(), position.getZ()));
                packets.add(packet);

                // 更新日期
                currentDate = currentDate.shiftedBy(60*60*12); // 每分钟计算一次轨道位置
            }

//            satellite.put("position", packets);
            packets.add(satellite);
            czml.put("packet", packets);


            try {
                Gson gson = new Gson();
                String json = gson.toJson(czml);
                // 将CZML写入文件
                File czmlFile = new File("D:\\Desktop\\GeoDT\\geodt-service_\\geodt-service\\geodt-service\\src\\main\\resources\\b.czml"); // CZML输出文件的路径
                FileWriter writer = new FileWriter(czmlFile);
                writer.write(json);
                writer.close();
                System.out.println("TLE转换为CZML完成！");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("TLE转换为CZML失败！");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

