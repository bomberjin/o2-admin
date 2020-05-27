

package beer.o2.modules.sys.controller;


import beer.o2.common.domain.NormalResult;
import beer.o2.common.enums.Constant;
import beer.o2.common.util.Assert;
import beer.o2.common.util.PageUtils;
import beer.o2.common.util.ValidatorUtils;
import beer.o2.common.util.group.AddGroup;
import beer.o2.common.util.group.UpdateGroup;
import beer.o2.modules.sys.domain.user.entity.SysUserDO;
import beer.o2.modules.sys.domain.user.vo.PasswordForm;
import beer.o2.modules.sys.domain.user.vo.SysUserResponseVO;
import beer.o2.modules.sys.service.SysUserRoleService;
import beer.o2.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.api.R;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public NormalResult<SysUserResponseVO> list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}
		PageUtils page = sysUserService.queryPage(params);

		return NormalResult.success(
				SysUserResponseVO.builder()
						.page(page)
						.build()
		);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public NormalResult<SysUserResponseVO> info(){
		return NormalResult.success(
				SysUserResponseVO.builder()
						.user(getUser())
						.build()
		);
	}
	
	/**
	 * 修改登录用户密码
	 */
	@PostMapping("/password")
	public NormalResult password(@RequestBody PasswordForm form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");
		
		//sha256加密
		String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return NormalResult.failed("原密码不正确");
		}
		
		return NormalResult.success();
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public NormalResult<SysUserResponseVO> info(@PathVariable("userId") Long userId){
		SysUserDO user = sysUserService.getById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);

		return NormalResult.success(
				SysUserResponseVO.builder()
						.user(user)
						.build()
		);
	}
	
	/**
	 * 保存用户
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public NormalResult save(@RequestBody SysUserDO user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCreateUserId(getUserId());
		sysUserService.saveUser(user);
		
		return NormalResult.success();
	}
	
	/**
	 * 修改用户
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public NormalResult update(@RequestBody SysUserDO user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.update(user);

		return NormalResult.success();
	}
	
	/**
	 * 删除用户
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public NormalResult delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return NormalResult.failed("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return NormalResult.failed("系统管理员不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return NormalResult.success();
	}
}
