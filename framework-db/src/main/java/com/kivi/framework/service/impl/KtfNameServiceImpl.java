/**
 * 
 */
package com.kivi.framework.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.kivi.framework.component.ApplicationKit;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.constant.GlobalErrorConst;
import com.kivi.framework.db.dao.BaseDao;
import com.kivi.framework.enums.KtfServiceStatus;
import com.kivi.framework.exception.AppException;
import com.kivi.framework.persist.mapper.KtfServiceNameMapperEx;
import com.kivi.framework.persist.model.KtfServiceName;
import com.kivi.framework.properties.KtfProperties;
import com.kivi.framework.service.IServiceName;
import com.kivi.framework.service.KtfNameService;
import com.kivi.framework.util.CommonUtils;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.util.kit.StrKit;

/**
 * @author Eric
 *
 */
@Service
@DependsOn( value = { "springContextHolder", "tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration" } )
public class KtfNameServiceImpl extends BaseDao<KtfServiceName> implements KtfNameService {

    private static final Logger    log                  = LoggerFactory.getLogger(KtfNameServiceImpl.class);

    // 状态更新周期5分钟
    private static final int       STATUS_UPDATE_SECOND = 5 * 60;

    private KtfServiceName         serviceName          = null;

    private List<KtfServiceName>   servicelist          = null;

    @Autowired
    private KtfProperties          ktfProperties;

    @Autowired
    private KtfServiceNameMapperEx ktfServiceNameMapperEx;

    public KtfNameServiceImpl() {

    }

    @PostConstruct
    private void startup() throws Exception {
        log.info("应用开始启动");
        String path = ktfProperties.getCommon().getSidDir();
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();

        String myidPath = StrKit.join("/", path, "sid");
        myidPath = StrKit.join(".", myidPath, ApplicationKit.me().getServerPort());
        log.info("保存myid，路径:{}", myidPath);

        File fmyid = new File(myidPath);
        if (!fmyid.exists()) {
            // myid不存在，需要注册
            KtfServiceName entity = null;
            if (!SpringContextHolder.containsBean("iServiceName")) {
                entity = new KtfServiceName();
                entity.setName(ApplicationKit.me().getAppcationName());
                entity.setHost(CommonUtils.geLocaltHostIp());
                entity.setPort(ApplicationKit.me().getServerPort());
            }
            else {
                IServiceName service = SpringContextHolder.getBean("iServiceName");
                entity = service.getName();
            }

            // 注册service
            serviceName = this.registService(entity);

            // 写入myid文件
            JSON.writeJSONString(new FileOutputStream(fmyid), serviceName);
        }
        else {
            serviceName = JSON.parseObject(new FileInputStream(fmyid), KtfServiceName.class);

            this.updateServiceStatus(KtfServiceStatus.online);
        }

        Timer time = new Timer();
        time.schedule(new ServiceStatusTask(), STATUS_UPDATE_SECOND * 1000, STATUS_UPDATE_SECOND * 1000);
    }

    @PreDestroy
    private void stoping() {
        log.info("应用开始停止");
        this.updateServiceStatus(KtfServiceStatus.offline);
    }

    @Override
    public int index() {
        List<Short> list = ktfServiceNameMapperEx.listServiceIndex(serviceName.getName());
        int pos = list.indexOf(serviceName.getSlotId());
        return pos;
    }

    public int count( String name ) {
        KtfServiceName entity = new KtfServiceName();
        entity.setName(name);

        return super.count(entity);
    }

    @Override
    public int countOnline() {
        KtfServiceName entity = new KtfServiceName();
        entity.setName(serviceName.getName());
        entity.setStatus(KtfServiceStatus.online.getCode());

        return super.count(entity);
    }

    private List<KtfServiceName> listOnlineService() {
        KtfServiceName entity = new KtfServiceName();
        entity.setName(this.serviceName.getName());
        entity.setStatus(KtfServiceStatus.online.getCode());

        List<KtfServiceName> list = super.selectByEntity(entity);

        return list;
    }

    private KtfServiceName registService( KtfServiceName sname ) {
        if (StrKit.isBlank(sname.getName())) {
            log.error("应用service的名称为空");
            throw new AppException(GlobalErrorConst.E_PARAM_IS_NULL);
        }

        sname.setSid(UUID.randomUUID().toString());
        sname.setSlotId((short) this.count(sname.getName()));
        sname.setStatus(KtfServiceStatus.online.getCode());
        sname.setGmtCreate(DateTimeKit.date());
        sname.setGmtUpdate(sname.getGmtCreate());

        return super.saveNotNull(sname);
    }

    private void updateServiceStatus( KtfServiceStatus status ) {

        KtfServiceName updateEntity = new KtfServiceName();
        updateEntity.setId(this.serviceName.getId());
        updateEntity.setStatus(status.getCode());
        updateEntity.setGmtUpdate(DateTimeKit.date());

        super.updateNotNull(updateEntity);
    }

    private void updateServiceStatus( Long id, KtfServiceStatus status ) {

        KtfServiceName updateEntity = new KtfServiceName();
        updateEntity.setId(id);
        updateEntity.setStatus(status.getCode());
        updateEntity.setGmtUpdate(DateTimeKit.date());

        super.updateNotNull(updateEntity);
    }

    private class ServiceStatusTask extends TimerTask {

        @Override
        public void run() {
            // 更新service状态
            updateServiceStatus(KtfServiceStatus.online);

            servicelist = listOnlineService();
            Iterator<KtfServiceName> it = servicelist.iterator();
            while (it.hasNext()) {
                KtfServiceName s = it.next();
                long minute = DateTimeKit.diff(s.getGmtUpdate(), DateTimeKit.date(), DateTimeKit.SECOND_MS);
                if (minute > STATUS_UPDATE_SECOND) {
                    updateServiceStatus(s.getId(), KtfServiceStatus.offline);
                }
            }
        }

    }
}
