package beer.o2.modules.sys.service.impl;

import beer.o2.common.exception.RRException;
import beer.o2.common.util.DateUtils;
import beer.o2.modules.sys.dao.SysCaptchaRepository;
import beer.o2.modules.sys.domain.pub.entity.SysCaptchaDO;
import beer.o2.modules.sys.service.SysCaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Optional;

/**
 * @Author:bomber
 * @Date:Created in 8:14 下午 2020/5/25
 * @Description:
 * @Modified By:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysCaptchaServiceImpl implements SysCaptchaService {
    @Autowired
    private Producer producer;

    @Autowired
    SysCaptchaRepository sysCaptchaRepository;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();

        SysCaptchaDO captchaEntity = new SysCaptchaDO();
        captchaEntity.setGmtCreate(new Date());
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //5分钟后过期
        captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        sysCaptchaRepository.save(captchaEntity);

        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        Optional<SysCaptchaDO> byUuid = sysCaptchaRepository.findByUuid(uuid);
        if(!byUuid.isPresent()){
            return false;
        }

        //删除验证码
        sysCaptchaRepository.delete(byUuid.get());

        if(byUuid.get().getCode().equalsIgnoreCase(code) && byUuid.get().getExpireTime().getTime() >= System.currentTimeMillis()){
            return true;
        }

        return false;
    }
}
