

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerTest {
	private static Logger logger = LogManager.getLogger(LoggerTest.class);

	public static void main(String[] args) {
		logger.info("Hello, World!");
		logger.debug("This is debug message.");
		// 记录info级别的信息
		logger.info("This is info message.");
		// 记录error级别的信息
		logger.error("This is error message.");
	}
}
