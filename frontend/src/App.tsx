import { Route, Routes } from "react-router-dom";
import "./App.css";
import HelloWorld from "./components/HelloWorld";
import Layout from "./components/common/layout/Index";
import LoginContainer from "./components/login/LoginContainer";
import RequireAuth from "./components/common/RequireAuth";

function App() {
  return (
    <Routes>
      <Route path='/' element={<Layout />}>
        <Route index element={<div>Home Page</div>} />

        <Route element={<RequireAuth roles={["ROLE_ADMIN", "ROLE_USER"]} />}>
          <Route path='/posts'>
            {/* Only allow access to ADMIN, USER  */}
            <Route index element={<div>Post page</div>} />

            {/* Only allow access to ADMIN  */}
            <Route element={<RequireAuth roles={["ROLE_ADMIN"]} />}>
              <Route path='new' element={<div>New Post</div>} />
            </Route>
          </Route>

          {/* Only allow access to ADMIN, USER  */}
          <Route path='/users' element={<div>Users Page</div>} />
          <Route path='/comments' element={<div>Comments Page</div>} />
          <Route path='/my-posts' element={<div>My posts Page</div>} />
        </Route>

        {/* public routes  */}
        <Route path='/login' element={<LoginContainer />} />
        <Route path='/register' element={<div>Register Page</div>} />
        <Route
          path='unauthorized'
          element={
            <div>You don't have the required permission to view this page</div>
          }
        />
      </Route>
    </Routes>
  );
}

export default App;
