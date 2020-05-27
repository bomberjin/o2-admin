
package beer.o2.modules.sys.controller;

import beer.o2.common.domain.NormalResult;
import beer.o2.modules.sys.domain.user.entity.SysUserDO;
import beer.o2.modules.sys.domain.user.vo.SysLoginFormRequestVO;
import beer.o2.modules.sys.service.SysCaptchaService;
import beer.o2.modules.sys.service.SysUserService;
import beer.o2.modules.sys.service.SysUserTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	public NormalResult login(@RequestBody SysLoginFormRequestVO form)throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return NormalResult.failed("验证码不正确");
		}

		//用户信息
		SysUserDO user = sysUserService.queryByUserName(form.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return NormalResult.failed("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return NormalResult.failed("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		NormalResult token = sysUserTokenService.createToken(user.getUserId());
		return token;
	}


	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public NormalResult logout() {
		sysUserTokenService.logout(getUserId());
		return NormalResult.success();
	}
	
}
