import React from 'react';

import { FcGoogle } from 'react-icons/fc';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const navigate = useNavigate();

    const googleAuthLoginClickHandle = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    }

    const naverAuthLoginClickHandle = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/naver";
    }

    return (
        <div>
            <input type="text" placeholder='email'/>
            <input type="password" placeholder='password'/>
            <button>로그인</button>
            <button onClick={googleAuthLoginClickHandle}><FcGoogle /></button>
            <button onClick={naverAuthLoginClickHandle}>네이버</button>
        </div>
    );
};

export default Login;