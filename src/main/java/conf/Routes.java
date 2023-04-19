/**
 * Copyright (C) the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Copyright (C) 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;

import controllers.CorsHeadersController;
import controllers.EmployeeController;
import controllers.StudentController;
import filters.CorsHandler;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
//
//import controllers.ApiController;
//import controllers.ApplicationController;
//import controllers.ArticleController;
//import controllers.LoginLogoutController;

public class Routes implements ApplicationRoutes {
    
    @Inject
    NinjaProperties ninjaProperties;

    /**
     * Using a (almost) nice DSL we can configure the router.
     * 
     * The second argument NinjaModuleDemoRouter contains all routes of a
     * submodule. By simply injecting it we activate the routes.
     * 
     * @param router
     *            The default router of this application
     */
    @Override
    public void init(Router router) {  
        
        // puts test data into db:
//        if (!ninjaProperties.isProd()) {
//            router.GET().route("/setup").with(ApplicationController::setup);
//        }
        
        ///////////////////////////////////////////////////////////////////////
        // Login / Logout
        ///////////////////////////////////////////////////////////////////////
//        router.GET().route("/login").with(LoginLogoutController::login);
//        router.POST().route("/login").with(LoginLogoutController::loginPost);
//        router.GET().route("/logout").with(LoginLogoutController::logout);


        router.OPTIONS().route("/signup-asAdmin").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/loginAdmin").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/addStudent").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/deleteStudent/{rollNo}").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/updateStudent").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/updateAttendance").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/getStudentByRollNo/{rollNo}").with(CorsHeadersController.class, "routeForOptions");

        router.OPTIONS().route("/getAllStudents").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/getAllAttendances").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/getStudent").with(CorsHeadersController.class, "routeForOptions");


        router.OPTIONS().route("/signup").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/login").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/markAttendance").with(CorsHeadersController.class, "routeForOptions");

        router.OPTIONS().route("/logout").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/isLoggedIn").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/isAdminLoggedIn").with(CorsHeadersController.class, "routeForOptions");
        router.OPTIONS().route("/reset").with(CorsHeadersController.class, "routeForOptions");







//        signup- asAdmin
        router.POST().route("/signup-asAdmin").with(EmployeeController::signUpAsAdmin);
//        login
        router.POST().route("/loginAdmin").with(EmployeeController::loginAdmin);
//        addStudent
        router.POST().route("/addStudent").with(StudentController::addStudent);
//        deleteStudent
        router.DELETE().route("/deleteStudent/{rollNo}").with(StudentController::deleteStudent);
//        updateStudent
        router.PUT().route("/updateStudent").with(StudentController::updateStudentDetails);
//        updateAttendances
        router.PUT().route("/updateAttendance").with(StudentController::updateAttendance);


//        getStudentby rollNo for update
        router.GET().route("/getStudentByRollNo/{rollNo}").with(StudentController::getStudentByRoll);

//       getAllStudents
        router.GET().route("/getAllStudents").with(StudentController::getAllStudents);
//        get All Attendances
        router.GET().route("/getAllAttendances/{rollNo}").with(StudentController::getAllAttendances);
//        getStudent for user after login
        router.GET().route("/getStudent").with(StudentController::getStudent);


//        signin-Student
        router.POST().route("/signup").filters(CorsHandler.class).with(EmployeeController::signup);
//        login-Student
        router.POST().route("/login").filters(CorsHandler.class).with(EmployeeController::login);
//        markAttendance
        router.POST().route("/markAttendance").with(StudentController::markAttendance);


//        logout
        router.GET().route("/logout").with(EmployeeController::logout);
//        is User Logged in
        router.GET().route("/isLoggedIn").with(EmployeeController::isUserLoggedIn);
//        is Admin Logged in
        router.GET().route("/isAdminLoggedIn").with(EmployeeController::isAdminLoggedIn);
//        ResetPassword
        router.PUT().route("/reset").with(EmployeeController::resetPassword);



        ///////////////////////////////////////////////////////////////////////
        // Create new article
        ///////////////////////////////////////////////////////////////////////
//        router.GET().route("/article/new").with(ArticleController::articleNew);
//        router.POST().route("/article/new").with(ArticleController::articleNewPost);
//
        ///////////////////////////////////////////////////////////////////////
        // Create new article
        ///////////////////////////////////////////////////////////////////////
//        router.GET().route("/article/{id}").with(ArticleController::articleShow);

        ///////////////////////////////////////////////////////////////////////
        // Api for management of software
        ///////////////////////////////////////////////////////////////////////
//        router.GET().route("/api/{username}/articles.json").with(ApiController::getArticlesJson);
//        router.GET().route("/api/{username}/article/{id}.json").with(ApiController::getArticleJson);
//        router.GET().route("/api/{username}/articles.xml").with(ApiController::getArticlesXml);
//        router.POST().route("/api/{username}/article.json").with(ApiController::postArticleJson);
//        router.POST().route("/api/{username}/article.xml").with(ApiController::postArticleXml);
//
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);

        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////






//        signup
//        router.GET().route("/signIn/{email}/{password}/{isAdmin}").with(EmployeeController::createUSer);
//        login
//        router.POST().route("/validate/{email}/{password}").with(EmployeeController::validate);


//        router.GET().route("/.*").with(ApplicationController::index);
    }

}
