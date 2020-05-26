
package beer.o2.modules.sys.service.impl;

import beer.o2.common.domain.NormalResult;
import beer.o2.modules.sys.dao.SysUserTokenRepository;
import beer.o2.modules.sys.domain.user.entity.SysUserTokenDO;
import beer.o2.modules.sys.domain.user.vo.UserTokenResponseVO;
import beer.o2.modules.sys.oauth2.TokenGenerator;
import beer.o2.modules.sys.service.SysUserTokenService;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;


@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysUserTokenServiceImpl  implements SysUserTokenService {
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;

	@Autowired
	SysUserTokenRepository sysUserTokenRepository;

	@Override
	public NormalResult createToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		Optional<SysUserTokenDO> byId = sysUserTokenRepository.findById(userId);

		if(!byId.isPresent()){
			SysUserTokenDO tokenEntity = new SysUserTokenDO();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			sysUserTokenRepository.save(tokenEntity);

		}else{
			SysUserTokenDO tokenEntity = byId.get();
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			sysUserTokenRepository.save(tokenEntity);

		}

		return NormalResult.success(UserTokenResponseVO.builder()
				.expire(EXPIRE)
				.token(token)
				.build());
	}

	@Override
	public void logout(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//修改token
		SysUserTokenDO tokenEntity = new SysUserTokenDO();
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);

		sysUserTokenRepository.save(tokenEntity);
	}
}
