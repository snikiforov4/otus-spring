package ua.nykyforov.migratetool.shell;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.base.Preconditions.checkNotNull;
import static ua.nykyforov.migratetool.JobNames.RDB_TO_NO_SQL;

@ShellComponent
public class MigrateCommands {

    private final JobOperator jobOperator;
    private final AtomicReference<Long> jobId = new AtomicReference<>();

    @Autowired
    public MigrateCommands(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    @ShellMethod("Start migration job.")
    public void start() throws JobParametersInvalidException, NoSuchJobException, JobInstanceAlreadyExistsException {
        Long jid = jobOperator.start(RDB_TO_NO_SQL, "");
        jobId.set(jid);
    }

    @ShellMethod("Restart migration job.")
    public void restart() throws JobParametersInvalidException, NoSuchJobException, JobRestartException,
            NoSuchJobExecutionException, JobInstanceAlreadyCompleteException {
        Long jid = checkNotNull(jobId.get(), "JobId is null. Job might not started.");
        jobOperator.restart(jid);
    }

}
