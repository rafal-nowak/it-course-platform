package pl.sages.javadevpro.projecttwo.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.CompositeArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

@AnalyzeClasses(packages = "pl.sages.javadevpro.projecttwo",
        importOptions = {ImportOption.DoNotIncludeTests.class})
public class GeneralCodingRulesTest {

    @ArchTest
    public static final ArchRule generalRules = CompositeArchRule.of(
            GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS)
            .and(GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION)
            .and(GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS);
}
