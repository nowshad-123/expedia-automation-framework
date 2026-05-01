package io.github.nowshad.expedia.utilis;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger logger =
        LogManager.getLogger(RetryAnalyzer.class);

    private int retryCount = 0;
    private static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY) {
            retryCount++;
            logger.warn("Retrying failed test: {} | " +
                "Attempt: {}/{}",
                result.getName(),
                retryCount,
                MAX_RETRY);
            return true;
        }
        return false;
    }
}
