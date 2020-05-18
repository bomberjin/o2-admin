

package beer.o2.common.exception;

import beer.o2.common.domain.NormalResult;
import beer.o2.common.enums.ResponseCode;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 * @author
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public NormalResult handleRRException(RRException e){
		return NormalResult.failed(e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public NormalResult handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return NormalResult.failed(ResponseCode.RESOURCE_DOES_NOT_EXIST);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public NormalResult handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return NormalResult.failed("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public NormalResult handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return NormalResult.failed("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public NormalResult handleException(Exception e){
		logger.error(e.getMessage(), e);
		return NormalResult.failed(ResponseCode.ERROR);
	}
}
