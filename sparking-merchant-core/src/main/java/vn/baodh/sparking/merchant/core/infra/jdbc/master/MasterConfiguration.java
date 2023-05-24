package vn.baodh.sparking.merchant.core.infra.jdbc.master;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Data
@Configuration
@ConfigurationProperties("infra.database.master")
@EnableTransactionManagement
@EnableJdbcRepositories(
    transactionManagerRef = "masterTx",
    jdbcOperationsRef = "masterNamedJdbcTemplate",
    basePackages = "vn.baodh.sparking.merchant.core.infra.jdbc.master")
public class MasterConfiguration {

  private String url;
  private String username;
  private String password;
  private int maxPoolSize = 16;

  @Bean("masterDataSource")
  public DataSource getMasterDataSource() {
    var config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    config.setMaximumPoolSize(maxPoolSize);
    config.setPoolName("master");
    return new HikariDataSource(config);
  }

  @Bean("masterJdbcTemplate")
  public JdbcTemplate getMasterJdbcTemplate(@Qualifier("masterDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean("masterNamedJdbcTemplate")
  public NamedParameterJdbcTemplate getMasterNamedJdbcTemplate(
      @Qualifier("masterDataSource") DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean("masterTx")
  public TransactionManager getMasterTransactionManager(
      @Qualifier("masterDataSource") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
