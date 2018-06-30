package ua.nykyforov.dao;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nykyforov.domain.QuizAnswer;
import ua.nykyforov.domain.QuizQuestion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;

public class CsvQuestionDAO implements QuestionDAO {
    private static final Logger logger = LoggerFactory.getLogger(CsvQuestionDAO.class);

    @Override
    public Collection<QuizQuestion> getAllQuestions() {
        ImmutableList.Builder<QuizQuestion> builder = ImmutableList.builder();
        URL url = checkNotNull(getClass().getClassLoader().getResource("quiz.csv"),
                "resource could not be found");
        try(CSVParser parser = CSVFormat.RFC4180.withHeader(QuizHeader.class)
                .parse(new BufferedReader(new InputStreamReader(url.openStream())))) {
            List<CSVRecord> records = parser.getRecords();
            for (int i = 1, recordsSize = records.size(); i < recordsSize; i++) {
                CSVRecord record = records.get(i);
                String question = record.get(QuizHeader.QUESTION);
                List<QuizAnswer> answers = buildQuizAnswers(record);
                builder.add(new QuizQuestion(question, answers));
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
        return builder.build();
    }

    private List<QuizAnswer> buildQuizAnswers(CSVRecord record) {
        List<QuizAnswer> answers = newArrayList();
        int correctAnswer = Integer.parseInt(record.get(QuizHeader.CORRECT_ANSWERS));
        Map<String, String> map = record.toMap();
        for (int i = 1; i <= 5; i++) {
            String answerValue = map.get("ANSWER_" + i);
            if (!isNullOrEmpty(answerValue)) {
                answers.add(new QuizAnswer(answerValue, i == correctAnswer));
            }
        }
        return answers;
    }
}
