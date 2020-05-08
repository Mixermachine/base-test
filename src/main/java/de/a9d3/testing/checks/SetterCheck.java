package de.a9d3.testing.checks;

import de.a9d3.testing.testdata.TestDataProvider;

import java.util.logging.Logger;

public class SetterCheck implements CheckInterface {
    private static final Logger LOGGER = Logger.getLogger(SetterCheck.class.getName());

    private final TestDataProvider provider;
    private final String regexExcluded;
    private final String seed;

    public SetterCheck() {
        this("");
    }

    public SetterCheck(String regexExcluded) {
        this(new TestDataProvider(), regexExcluded);
    }

    public SetterCheck(TestDataProvider provider) {
        this(provider, "");
    }

    public SetterCheck(TestDataProvider provider, String regexExcluded) {
        this.provider = provider;
        this.regexExcluded = regexExcluded;
        this.seed = "48107951-0256-4a84-9f8e-132ee651ae9e";
    }

    @Override
    public boolean check(Class<?> c) {
        return false;
    }
}
