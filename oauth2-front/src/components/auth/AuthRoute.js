import axios from 'axios';
import React from 'react';
import { useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from "recoil";
import { authenticationState } from '../../store/atoms/AuthAtoms';

const AuthRoute = ({ path, element }) => {
    const navigate = useNavigate();
    const [ authState, setAuthState ] = useRecoilState(authenticationState);
    const authenticated = useQuery(["authenticated"], async () => {
        const option = {
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("accessToken")}`
            }
        }
        return await axios.get("http://localhost:8080/auth/authenticated", option);
    }, {
        onSuccess: (response) => {
            if(response.status === 200) {
                if(response.data) {
                    setAuthState(true);
                }
            }
        }
    })

    const authenticatedPaths = ["/mypage", "/user", "/post"];
    const authPath = "/auth"

    if(authenticated.isLoading) {
        return <></>
    }

    if(authState && path.startsWith(authPath)) {
        navigate("/");
    }
    if(!authState && authenticatedPaths.filter(authenticatedPath => path.startsWith(authenticatedPath)).length > 0) {
        navigate("/auth/login");
    }
    return element;
};

export default AuthRoute;