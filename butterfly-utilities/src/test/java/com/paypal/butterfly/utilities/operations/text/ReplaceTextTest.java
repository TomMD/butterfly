package com.paypal.butterfly.utilities.operations.text;

import com.paypal.butterfly.extensions.api.TOExecutionResult;
import com.paypal.butterfly.utilities.TransformationUtilityTestHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Unit test for {@link ReplaceText}
 *
 * @author facarvalho
 */
public class ReplaceTextTest extends TransformationUtilityTestHelper {

    @Test
    public void noOpTest() throws IOException {
        ReplaceText replaceText = new ReplaceText("(\\$\\{packageName\\})").setReplacement("com.testapp").relative("web.xml");
        TOExecutionResult executionResult = replaceText.execution(transformedAppFolder, transformationContext);
        Assert.assertEquals(executionResult.getType(), TOExecutionResult.Type.NO_OP);

        assertNotChangedFile("web.xml");
    }

    @Test
    public void successFirstOnlyTest() throws IOException {
        ReplaceText replaceText = new ReplaceText("foo").setReplacement("zoo").relative("application.properties");
        TOExecutionResult executionResult = replaceText.execution(transformedAppFolder, transformationContext);
        Assert.assertEquals(executionResult.getType(), TOExecutionResult.Type.SUCCESS);

        assertChangedFile("application.properties");
        assertSameLineCount("application.properties");

        Properties properties = getProperties("application.properties");

        Assert.assertEquals(properties.size(), 3);
        Assert.assertEquals(properties.getProperty("bar"), "barv");
        Assert.assertEquals(properties.getProperty("zoo"), "zoov");
        Assert.assertEquals(properties.getProperty("foofoo"), "foofoov");
    }

    @Test
    public void successAllTest() throws IOException {
        ReplaceText replaceText = new ReplaceText("foo").setReplacement("zoo").relative("application.properties").setFirstOnly(false);
        TOExecutionResult executionResult = replaceText.execution(transformedAppFolder, transformationContext);
        Assert.assertEquals(executionResult.getType(), TOExecutionResult.Type.SUCCESS);

        assertChangedFile("application.properties");
        assertSameLineCount("application.properties");

        Properties properties = getProperties("application.properties");

        Assert.assertEquals(properties.size(), 3);
        Assert.assertEquals(properties.getProperty("bar"), "barv");
        Assert.assertEquals(properties.getProperty("zoo"), "zoov");
        Assert.assertEquals(properties.getProperty("zoozoo"), "zoozoov");
    }

    @Test
    public void fileDoesNotExistTest() {
        ReplaceText replaceText = new ReplaceText("foo").relative("application_zeta.properties");
        TOExecutionResult executionResult = replaceText.execution(transformedAppFolder, transformationContext);
        Assert.assertEquals(executionResult.getType(), TOExecutionResult.Type.ERROR);
        Assert.assertEquals(executionResult.getException().getClass(), FileNotFoundException.class);
    }

}
