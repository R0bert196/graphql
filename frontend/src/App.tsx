import { Route, Routes } from "react-router-dom";
import "./App.css";
import HelloWorld from "./components/HelloWorld";
import Layout from "./components/common/layout/Index";
import LoginContainer from "./components/login/LoginContainer";
import RequireAuth from "./components/common/RequireAuth";
import PostContainer from "./components/posts/PostContainer";
import AddPost from "./components/posts/AddPost";
import RegisterUser from "./components/users/RegisterUser";
import Unauthorized from "./components/common/Unauthorized";

function App() {
  return (
    <Routes>
      <Route path='/' element={<Layout />}>
        <Route index element={<div>Home Page</div>} />

        <Route element={<RequireAuth roles={["ROLE_ADMIN", "ROLE_USER"]} />}>
          <Route path='/posts'>
            {/* Only allow access to ADMIN, USER  */}
            <Route index element={<PostContainer />} />

            {/* Only allow access to ADMIN  */}
            <Route element={<RequireAuth roles={["ROLE_ADMIN"]} />}>
              <Route path='new' element={<AddPost />} />
            </Route>
          </Route>

          {/* Only allow access to ADMIN, USER  */}
          <Route path='/users' element={<div>Users Page</div>} />
          <Route path='/comments' element={<div>Comments Page</div>} />
          <Route path='/my-posts' element={<div>My posts Page</div>} />
        </Route>

        {/* public routes  */}
        <Route path='/login' element={<LoginContainer />} />
        <Route path='/register' element={<RegisterUser />} />
        <Route path='/unauthorized' element={<Unauthorized />} />
      </Route>
    </Routes>
  );
}

export default App;
