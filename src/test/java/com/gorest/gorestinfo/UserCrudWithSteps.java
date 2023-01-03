package com.gorest.gorestinfo;

import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCrudWithSteps extends TestBase {
        static String name = "Parv" + TestUtils.getRandomValue();
        static String email = TestUtils.getRandomValue() + "parv@gmail.com";
        static String gender = "male";
        static String status = "active";
        static int userId;

        @Steps
        UserSteps userSteps;

        @Title("This will create a new user")
        @Test
        public void test001() {
            userSteps.createUser(name,email,gender,status).statusCode(201).log().all();
        }

        @Title("Verify if the user was added to the application")
        @Test
        public void test002() {
                HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
                Assert.assertThat(userMap, hasValue(name));
                userId = (int) userMap.get("id");

        }

        @Title("Update the user information and verify the updated information")
        @Test
        public void test003() {
                name = name + "_updated";
                userSteps.updateUser(userId, name, email, gender, status).statusCode(200).log().all();

                HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
                Assert.assertThat(userMap, hasValue(name));

        }
        @Title("Delete the user and verify if the user is deleted!")
        @Test
        public void test004() {
                userSteps.deleteUser(userId).statusCode(204);
                userSteps.getUserById(userId).statusCode(404);
        }
    }
