

package com.giovanny.study.config.mybatis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author xys1717
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class MyDataSourceProperties {

	private ClassLoader classLoader;

	/**
	 * Name of the datasource. Default to "testdb" when using an embedded database.
	 */
	private String name;

	/**
	 * Whether to generate a random datasource name.
	 */
	private boolean generateUniqueName;

	/**
	 * Fully qualified name of the connection pool implementation to use. By default, it
	 * is auto-detected from the classpath.
	 */
	private Class<? extends DataSource> type;

	/**
	 * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
	 */
	private String driverClassName;

	/**
	 * JDBC URL of the database.
	 */
	private String url;

	/**
	 * Login username of the database.
	 */
	private String username;

	/**
	 * Login password of the database.
	 */
	private String password;

	/**
	 * JNDI location of the datasource. Class, url, username & password are ignored when
	 * set.
	 */
	private String jndiName;

	/**
	 * Initialize the datasource with available DDL and DML scripts.
	 */
	private DataSourceInitializationMode initializationMode = DataSourceInitializationMode.EMBEDDED;

	/**
	 * Platform to use in the DDL or DML scripts (such as schema-${platform}.sql or
	 * data-${platform}.sql).
	 */
	private String platform = "all";

	/**
	 * Schema (DDL) script resource references.
	 */
	private List<String> schema;

	/**
	 * Username of the database to execute DDL scripts (if different).
	 */
	private String schemaUsername;

	/**
	 * Password of the database to execute DDL scripts (if different).
	 */
	private String schemaPassword;

	/**
	 * Data (DML) script resource references.
	 */
	private List<String> data;

	/**
	 * Username of the database to execute DML scripts (if different).
	 */
	private String dataUsername;

	/**
	 * Password of the database to execute DML scripts (if different).
	 */
	private String dataPassword;

	/**
	 * Whether to stop if an error occurs while initializing the database.
	 */
	private boolean continueOnError = false;

	/**
	 * Statement separator in SQL initialization scripts.
	 */
	private String separator = ";";

	/**
	 * SQL scripts encoding.
	 */
	private Charset sqlScriptEncoding;

	private EmbeddedDatabaseConnection embeddedDatabaseConnection = EmbeddedDatabaseConnection.NONE;


	private String uniqueName;


}
