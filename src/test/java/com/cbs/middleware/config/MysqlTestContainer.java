package com.cbs.middleware.config;

public class MysqlTestContainer /* implements SqlTestContainer */ {
	/*
	 * 
	 * private static final Logger log =
	 * LoggerFactory.getLogger(MysqlTestContainer.class); private long memoryInBytes
	 * = 100 * 1024 * 1024; private long memorySwapInBytes = 200 * 1024 * 1024;
	 * 
	 * private MySQLContainer<?> mysqlContainer;
	 * 
	 * @Override public void destroy() { if (null != mysqlContainer &&
	 * mysqlContainer.isRunning()) { mysqlContainer.stop(); } }
	 * 
	 * @Override public void afterPropertiesSet() { if (null == mysqlContainer) {
	 * mysqlContainer = new MySQLContainer<>("mysql:8.0.30-debian")
	 * .withDatabaseName("CBSMiddleware")
	 * .withTmpFs(Collections.singletonMap("/testtmpfs", "rw")) .withLogConsumer(new
	 * Slf4jLogConsumer(log)) .withReuse(true) .withPrivilegedMode(true)
	 * .withConfigurationOverride("testcontainers/mysql")
	 * .withCreateContainerCmdModifier(cmd ->
	 * cmd.getHostConfig().withMemory(memoryInBytes).withMemorySwap(
	 * memorySwapInBytes)); } if (!mysqlContainer.isRunning()) {
	 * mysqlContainer.start(); } }
	 * 
	 * @Override public JdbcDatabaseContainer<?> getTestContainer() { return
	 * mysqlContainer; }
	 */}
