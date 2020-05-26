

package beer.o2.modules.sys.service;

import beer.o2.common.domain.NormalResult;

/**
 * 用户Token
 *
 */
public interface SysUserTokenService  {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	NormalResult createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
