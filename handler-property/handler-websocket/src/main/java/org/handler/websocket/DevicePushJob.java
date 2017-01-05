package org.handler.websocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

/**
 * 设备数量推送任务
 */
@DisallowConcurrentExecution
public class DevicePushJob extends QuartzJobBean {
    private Logger log = LoggerFactory.getLogger(DevicePushJob.class);
   /* @Autowired
    private DeviceService deviceService;*/

    @SuppressWarnings("unused")
    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        final Object object = jobDataMap.get(JOBDATA_KEY_APPCTX);
        final ApplicationContext appCtx = (ApplicationContext) object;
        final DeviceWSSHolder deviceWSSHolder = (DeviceWSSHolder) jobDataMap .get(JOBDATA_KEY_INDEXCONFIG_DEVICE);
        List<String> ids  = deviceWSSHolder.getEffectDataIds();
        if(ids.size()==0){
            return;
        }
        //String idsStr = Joiner.on(",").join(ids);
        //System.out.println("××××××××××××××××××要查询的设备类别是："+idsStr);
        //log.info("××××××××××××××××××要查询的设备类别是："+idsStr);
        //查询数据 id,type,value 把数据都装到新的List<SubscribeBO>中发送，DeviceWSSHolder数据仅作为字典查询用
        List<Integer> integers = Lists.newArrayList();
        for (String typeIdFmt : ids) {
            List<String> result = Splitter.onPattern(PORTLET_TREE_DEVICETYPE_FORMAT)
                    .omitEmptyStrings().splitToList(typeIdFmt);
            Integer id = Integer.parseInt(result.get(0));
            integers.add(id);
        }
        List<SubscribeBO> subscribeBOs = Lists.newArrayList();
     //   DeviceService deviceService = appCtx.getBean(DeviceService.class);
        Map<Integer,Integer> deviceCounts =  deviceService.countDeviceByDeviceType(integers,false);
        if (deviceCounts == null || deviceCounts.size()==0) {
            return;
        }
        for (Map.Entry<Integer, Integer> deviceCount : deviceCounts.entrySet()) {
            Integer deviceTypeId = deviceCount.getKey();
            Integer count = deviceCount.getValue();
            SubscribeBO sb = new SubscribeBO(SubscribeType.DEVICE_COUNT.getCode(),PORTLET_TREE_DEVICETYPE_FORMAT+deviceTypeId.toString());
            sb.setSubscribeValue(""+count);
            subscribeBOs.add(sb);
        }
        for(SubscribeBO bo:subscribeBOs){
            Map.Entry<SubscribeBO, List<WebSocketSession>> entry = DeviceWSSHolder
                    .getInstance().getItemBySubscribeBO(bo);
            if(entry !=null){
                SubscribeBO temp = entry.getKey();
                bo.setSubscribeId(temp.getSubscribeId());
                List<WebSocketSession> sessions = entry.getValue();
                Iterator<WebSocketSession> iterator = sessions.iterator();
                while (iterator.hasNext()) {
                    WebSocketSession session = iterator.next();
                    if (session != null && session.isOpen()) {
                        try {
                            JSONObject ret = new JSONObject();
                            ret.put("success", true);
                            List<SubscribeBO> retSbo = Lists.newArrayList(bo);
                            ret.put("data", retSbo);
                            String jsonString = JSON.toJSONString(ret);
                            //System.err.println(jsonString);
                            log.info(jsonString);
                            session.sendMessage(new TextMessage(jsonString));
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    }else{
                        iterator.remove();
                    }
                }
            }
        }

    }

}
