package com.thegreychain.kotlincalculator;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class CalculatorActivitySimpleExpressionTest {

    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityTestRule = new ActivityTestRule<>(CalculatorActivity.class);

    /**?
     * This UI Tests : 1+2
     * 1. Onclick of 1
     * 2. Onclick of *
     * 3. Check if Result display is 3.0
     *
     *
     *
     */
    @Test
    public void calculatorActivityEvaluateTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_number_one), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                13),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_operator_add), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                21),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_number_two), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                14),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_evaluate), withText("="),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                20),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.lbl_display), withText("3.0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("3.0")));

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
