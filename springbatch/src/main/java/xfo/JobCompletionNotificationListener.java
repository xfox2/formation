package xfo;

import org.springframework.stereotype.Component;
import xfo.beans.Person;
import org.apache.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static Logger logger = Logger.getLogger(JobCompletionNotificationListener.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate){

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution){
        if(jobExecution.getStatus().equals(BatchStatus.COMPLETED)){
            logger.info("Job Finished !!! Time to verify the results");
            List<Person> results = jdbcTemplate.query("SELECT first_name,last_name From people", new RowMapper<Person>() {
                @Override
                public Person mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new Person(resultSet.getString(1),resultSet.getString(2));
                }
            });
            logger.info("List of Persons loaded : ");
            results.stream().forEach(logger::info);

        }
    }
}
