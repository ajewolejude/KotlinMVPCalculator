package com.thegreychain.kotlinclculator;

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
public class CalculatorActivityTest2 {

    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityTestRule = new ActivityTestRule<>(CalculatorActivity.class);

    @Test
    public void calculatorActivityTest2() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**?
         * This UI Tests : ((5*5)+(2*10))
         * 1. Onclick of (
         * 2. Onclick of 5
         * 3. Onclick of *
         * 4. Onclick of +
         * 5. Onclick of 2
         * 6. Onclick of 10
         * 7. Check if Result display is 45.0
         * 8. Onclick of C- clear button
         * 9. Check if Result display is blank
         *
         *
         *
         */
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_open_b), withText("("),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                25),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_open_b), withText("("),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                25),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_number_five), withText("5"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_operator_multiply), withText("*"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                11),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_number_five), withText("5"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_close_b), withText(")"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                26),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btn_operator_add), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                21),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btn_open_b), withText("("),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                25),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btn_number_two), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                14),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_operator_multiply), withText("*"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                11),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.btn_number_one), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                13),
                        isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.btn_number_zero), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                18),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.btn_close_b), withText(")"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                26),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.btn_close_b), withText(")"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                26),
                        isDisplayed()));
        appCompatButton14.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.btn_evaluate), withText("="),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                20),
                        isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.lbl_display),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("45.0")));

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.btn_clear), withText("C"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                23),
                        isDisplayed()));
        appCompatButton16.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.lbl_display),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.root_activity_calculator),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("")));

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
