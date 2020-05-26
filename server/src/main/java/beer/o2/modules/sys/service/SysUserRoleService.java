

package beer.o2.modules.sys.service;


import java.util.List;


/**
 * 用户与角色对应关系
 *
 */
public interface SysUserRoleService  {
	
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
