import axios from 'axios';
import React, { useState } from 'react';
import { useMutation } from 'react-query';
import { useSearchParams } from 'react-router-dom';

const OAuth2Merge = () => {
    const providerMerge = useMutation(async (mergeData) => {
        try{
            const response = await axios.put("http://localhost:8080/auth/oauth2/merge", mergeData);
            return response;
        } catch(error) {
            setErrorMsg(error.response.data);
            return error;
        }
    }, {
        onSuccess: (response) => {
            if(response.status === 200) {
                alert("계정 통합 완료!");
                window.location.replace("/auth/login");
            }
        }
    });
    const [ password, setPassword ] = useState();
    const [ errorMsg, setErrorMsg ] = useState("");
    const [ searchParams, setSearchParams ] = useSearchParams();
    const email = searchParams.get("email");
    const provider = searchParams.get("provider");

    const passwordChangeHandle = (e) => {
        setPassword(e.target.value);
    }

    const providerMergeSubmitHandle = () => {
        providerMerge.mutate({
            email, 
            password,
            provider
        })
    }

    return (
        <div>
            <h1>{email}계정을 {provider} 계정과 통합하는 것에 동의 하십니까?</h1>
            <input type="password" onChange={passwordChangeHandle} placeholder='기존 계정의 비밀번호를 입력하세요'/>
            <p>{errorMsg}</p>
            <button onClick={providerMergeSubmitHandle}>동의</button>
            <button>취소</button>
        </div>
    );
};

export default OAuth2Merge;