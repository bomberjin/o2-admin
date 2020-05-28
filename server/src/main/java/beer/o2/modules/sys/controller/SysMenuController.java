

package beer.o2.modules.sys.controller;

import beer.o2.common.domain.NormalResult;
import beer.o2.common.enums.Constant;
import beer.o2.common.exception.RRException;
import beer.o2.modules.sys.domain.pub.entity.SysMenuDO;
import beer.o2.modules.sys.domain.pub.vo.SysMenuResponseVO;
import beer.o2.modules.sys.domain.user.vo.SysNavResponseVO;
import beer.o2.modules.sys.service.ShiroService;
import beer.o2.modules.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private ShiroService shiroService;

	/**
	 * 导航菜单
	 */
	@GetMapping("/nav")
	public NormalResult<SysNavResponseVO> nav(){
		List<SysMenuDO> menuList = sysMenuService.getUserMenuList(getUserId());
		Set<String> permissions = shiroService.getUserPermissions(getUserId());
		return NormalResult.success(
				SysNavResponseVO.builder()
						.menuList(menuList)
						.permissions(permissions)
						.build()
		);
	}
	
	/**
	 * 所有菜单列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public List<SysMenuDO> list(){
		List<SysMenuDO> menuList = sysMenuService.list();
		for(SysMenuDO sysMenuEntity : menuList){
			Optional<SysMenuDO> byId = sysMenuService.getById(sysMenuEntity.getParentId());
			if(byId.isPresent()){
				sysMenuEntity.setParentName(byId.get().getName());
			}
		}

		return menuList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public NormalResult<SysMenuResponseVO> select(){
		//查询列表数据
		List<SysMenuDO> menuList = sysMenuService.queryNotButtonList();
		
		//添加顶级菜单
		SysMenuDO root = new SysMenuDO();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);

		return NormalResult.success(
				SysMenuResponseVO.builder()
						.menuList(menuList)
						.build()
		);
		
	}
	
	/**
	 * 菜单信息
	 */
	@GetMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public NormalResult<SysMenuResponseVO> info(@PathVariable("menuId") Long menuId){
		Optional<SysMenuDO> byId = sysMenuService.getById(menuId);
		return NormalResult.success(
				SysMenuResponseVO.builder()
						.menu(byId.get())
						.build()
		);

	}
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public NormalResult save(@RequestBody SysMenuDO menu){
		//数据校验
		verifyForm(menu);
		
		sysMenuService.save(menu);
		
		return NormalResult.success();
	}
	
	/**
	 * 修改
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public NormalResult update(@RequestBody SysMenuDO menu){
		//数据校验
		verifyForm(menu);
				
		sysMenuService.updateById(menu);

		return NormalResult.success();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete/{menuId}")
	@RequiresPermissions("sys:menu:delete")
	public NormalResult delete(@PathVariable("menuId") long menuId){
		if(menuId <= 31){
			NormalResult.failed("系统菜单，不能删除");
		}

		//判断是否有子菜单或按钮
		List<SysMenuDO> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			NormalResult.failed("请先删除子菜单或按钮");
		}

		sysMenuService.delete(menuId);

		return NormalResult.success();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuDO menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuDO parentMenu = sysMenuService.getById(menu.getParentId()).get();
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
