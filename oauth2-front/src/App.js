import React from 'react';
import { Routes, Route } from 'react-router-dom';
import AuthRoute from './components/auth/AuthRoute';
import Index from './pages/Index/Index';
import Login from './pages/Login/Login';
import OAuth2Login from './pages/Login/OAuth2Login';
import NotFound from './pages/NotFound/NotFound';
import OAuth2Merge from './pages/OAuth2Merge/OAuth2Merge';
import PostRegister from './pages/Posts/PostRegister';
import OAuth2Register from './pages/Register/OAuth2Register';

function App() {
  return (
    <>
      <Routes>
        <Route path='/' element={<AuthRoute path={"/"} element={<Index />} />}/>
        
        <Route path='/mypage' element={<AuthRoute path={"/mypage"} element={<Index />} />}/>
        <Route path='/post/register' element={<AuthRoute path={"/post/register"} element={<PostRegister />} />}/>


        <Route path='/auth/login' element={<AuthRoute path={"/auth/login"} element={<Login />} />} />
        <Route path='/auth/register' />
        <Route path='/auth/oauth2/login' 
          element={<AuthRoute path={"/auth/oauth2/login"} element={<OAuth2Login />} />}/>
        <Route path='/auth/oauth2/register' 
          element={<AuthRoute path={"/auth/oauth2/register"} element={<OAuth2Register />} />}/>
        <Route path='/auth/oauth2/merge' 
          element={<AuthRoute path={"/auth/oauth2/merge"} element={<OAuth2Merge />} />}/>
        <Route path='/*' element={<NotFound />}/>
      </Routes>
    </>
  );
}

export default App;
