/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package beer.o2.modules.sys.service;

import beer.o2.common.util.PageUtils;
import beer.o2.modules.sys.domain.user.entity.SysUserDO;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 */
public interface SysUserService  {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserDO queryByUserName(String username);

	/**
	 * 保存用户
	 */
	void saveUser(SysUserDO user);
	
	/**
	 * 修改用户
	 */
	void update(SysUserDO user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(Long userId, String password, String newPassword);
}
