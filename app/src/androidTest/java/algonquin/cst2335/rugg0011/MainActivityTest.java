package algonquin.cst2335.rugg0011;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test to verify that when we input a simple password<br>
     * the application won't let us through
     */
    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.logInEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.logInEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.logInButton), withText("Log in"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.logInText), withText("YOU... SHALL NOT... PASS!!!"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("YOU... SHALL NOT... PASS!!!")));
    }

    /**
     * Test to verify that when we are missing an upper case<br>
     * letter the application won't let us through
     */
    @Test
    public void testFindMissingUpperCase(){
        ViewInteraction appCompatEditText = onView(withId(R.id.logInEditText));
        appCompatEditText.perform(replaceText("g00gl3@2k14"));
        ViewInteraction materialButton = onView(withId(R.id.logInButton));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.logInText));
        textView.check(matches(withText("YOU... SHALL NOT... PASS!!!")));
    }

    /**
     * Test to verify that when we are missing an lower case<br>
     * letter the application won't let us through
     */
    @Test
    public void testFindMissingLowerCase(){
        ViewInteraction appCompatEditText = onView(withId(R.id.logInEditText));
        appCompatEditText.perform(replaceText("G00GL3@2K14"));
        ViewInteraction materialButton = onView(withId(R.id.logInButton));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.logInText));
        textView.check(matches(withText("YOU... SHALL NOT... PASS!!!")));
    }

    /**
     * Test to verify that when we are missing a number<br>
     * the application won't let us through
     */
    @Test
    public void testFindMissingNumber(){
        ViewInteraction appCompatEditText = onView(withId(R.id.logInEditText));
        appCompatEditText.perform(replaceText("Google@ZkIA"));
        ViewInteraction materialButton = onView(withId(R.id.logInButton));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.logInText));
        textView.check(matches(withText("YOU... SHALL NOT... PASS!!!")));
    }

    /**
     * Test to verify that when we are missing a special character<br>
     * the application won't let us through
     */
    @Test
    public void testFindMissingSpecial(){
        ViewInteraction appCompatEditText = onView(withId(R.id.logInEditText));
        appCompatEditText.perform(replaceText("G00gl32k14"));
        ViewInteraction materialButton = onView(withId(R.id.logInButton));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.logInText));
        textView.check(matches(withText("YOU... SHALL NOT... PASS!!!")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
