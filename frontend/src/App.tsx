import { Route, Routes } from "react-router-dom";
import "./App.css";
import HelloWorld from "./components/HelloWorld";
import Layout from "./components/common/layout/Index";
import LoginContainer from "./components/login/LoginContainer";

function App() {
  return (
    <Routes>
      <Route path='/' element={<Layout />}>
        <Route index element={<div>Home Page</div>} />
        <Route path='/posts'>
          <Route index element={<div>Post page</div>} />
          <Route path='new' element={<div>New Post</div>} />
        </Route>
        <Route path='/users' element={<div>Users Page</div>} />
        <Route path='/comments' element={<div>Comments Page</div>} />
        <Route path='/my-posts' element={<div>My posts Page</div>} />
        <Route path='/login' element={<LoginContainer />} />
        <Route path='/register' element={<div>Register Page</div>} />
      </Route>
    </Routes>
  );
}

export default App;
