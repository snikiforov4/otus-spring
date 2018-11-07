package ua.nykyforov.migratetool.shell;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static ua.nykyforov.migratetool.JobNames.RDB_TO_NO_SQL;

@ShellComponent
public class MigrateCommands {

    private final JobOperator jobOperator;

    @Autowired
    public MigrateCommands(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    @ShellMethod("Start migration.")
    public void start() throws JobParametersInvalidException, NoSuchJobException, JobInstanceAlreadyExistsException {
        jobOperator.start(RDB_TO_NO_SQL, "");
    }

}
