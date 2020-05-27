/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package beer.o2.modules.sys.controller;


import beer.o2.common.domain.NormalResult;
import beer.o2.common.enums.Constant;
import beer.o2.common.util.PageUtils;
import beer.o2.common.util.ValidatorUtils;
import beer.o2.modules.sys.domain.role.entity.SysRoleDO;
import beer.o2.modules.sys.domain.role.vo.SysRoleResponseVO;
import beer.o2.modules.sys.service.SysRoleMenuService;
import beer.o2.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	public NormalResult<SysRoleResponseVO> list(@RequestParam Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}

		PageUtils page = sysRoleService.queryPage(params);

		return NormalResult.success(
				SysRoleResponseVO.builder()
						.page(page)
						.build()
		);
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public NormalResult<SysRoleResponseVO> select(){
		Map<String, Object> map = new HashMap<>();

		Long userId = 0L;
		
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			userId = getUserId();
		}
		List<SysRoleDO> list = (List<SysRoleDO>) sysRoleService.listByMap(userId);
		return NormalResult.success(
				SysRoleResponseVO.builder()
						.list(list)
						.build()
		);
		
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public NormalResult<SysRoleResponseVO> info(@PathVariable("roleId") Long roleId){
		SysRoleDO role = sysRoleService.getById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		return NormalResult.success(
				SysRoleResponseVO.builder()
						.role(role)
						.build()
		);
	}
	
	/**
	 * 保存角色
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public NormalResult save(@RequestBody SysRoleDO role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.saveRole(role);

		return NormalResult.success();
	}
	
	/**
	 * 修改角色
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public NormalResult update(@RequestBody SysRoleDO role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.update(role);

		return NormalResult.success();
	}
	
	/**
	 * 删除角色
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public NormalResult delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);

		return NormalResult.success();
	}
}
