package Rule;

import ControllerTests.DonationTests;
import com.cyberschnitzel.Util.DatabaseUtil;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DonationTests.class})
public class Ruler{
    private static TestRule setupTeardown = new ExternalResource() {
        @Override
        protected void before() {
            System.out.println("Before rule");
            DatabaseUtil.testing = true;
        }
        @Override
        protected void after() {
            System.out.println("After rule");
            DatabaseUtil.testing = false;
        }
    };

    @ClassRule
    public static TestRule chain = RuleChain.outerRule(setupTeardown);
}
